package app.isparks.service.security.jwt.impl;

import app.isparks.core.config.ISparksConstant;
import app.isparks.core.security.jwt.AJwt;
import app.isparks.core.security.jwt.exception.JWTAuthException;
import app.isparks.core.security.jwt.exception.JWTException;
import app.isparks.core.util.StringUtils;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * auth0的实现java jwt
 *
 * @author： chenghd
 * @date： 2021/2/6
 */
public class Jose4jImpl extends AJwt {

    private Logger log = LoggerFactory.getLogger(Jose4jImpl.class);

    public final static String DEFAULT_ALGORITHM = AlgorithmIdentifiers.HMAC_SHA256;

    public final static Key KEY = new HmacKey(ISparksConstant.DYNAMIC_PRIVATE_KEY.getBytes());

    @Override
    public String signJWT(String algorithm , String jti ,String issuer ,String audience ,String subject , Map<String,Object> claims , long nbf , long iat , long expire , TimeUnit timeUnit) throws JWTException {

        JwtClaims jwtClaims = generateClaim(jti , issuer , audience , subject , claims , nbf , iat ,expire , timeUnit);

        try {
            JsonWebSignature jws = new JsonWebSignature();
            jws.setAlgorithmHeaderValue(algorithm);

            jws.setKey(KEY);
            jws.setPayload(jwtClaims.toJson());

            //org.jose4j.jws.AlgorithmIdentifiers

            return jws.getCompactSerialization();
        }catch (JoseException e){
            log.error("fail to sign",e);
            throw new JWTException(e.getMessage());
        }

    }

    @Override
    public void verify(String algorithm ,String token, String issuer, String audience,String subject) throws JWTAuthException {

        JwtConsumer jwtConsumer = jwtConsumer(issuer,audience,subject,KEY,algorithm);

        try {

            jwtConsumer.process(token);

            log.info("JWT validation succeeded! ");

        }catch (InvalidJwtException e){
            log.error("Invalid JWT!"+handleException(e));

            throw new JWTAuthException().withReason("JWT expired.");
        }
    }

    @Override
    public Optional<String> verifyToken(String token, String issuer, String audience , String subject) {
        if(StringUtils.isEmpty(token)){
            return Optional.of("Token must not be null.");
        }

        JwtConsumer jwtConsumer = jwtConsumer(issuer,audience,subject,KEY,DEFAULT_ALGORITHM);

        try {

            jwtConsumer.process(token);

            log.info("JWT validation succeeded! ");

        }catch (InvalidJwtException e){
            //log.error("Invalid JWT!");
            log.error("Invalid JWT!",e);

            return Optional.ofNullable(handleException(e));

        }
        return Optional.empty();
    }

    @Override
    public Map<String, Object> getClaims(String algorithm ,String token, String issuer,String audience,String subject) throws JWTAuthException {
        JwtConsumer jwtConsumer = jwtConsumer(issuer,audience,subject,KEY,algorithm);
        try {
            JwtClaims claims = jwtConsumer.processToClaims(token);
            return claims.getClaimsMap();
        }catch (InvalidJwtException e){
            //throw new JWTAuthException().withReason(handleException(e));
            return new HashMap<>();
        }
    }

    @Override
    public String getId(String algorithm , String token, String issuer, String audience, String subject) throws JWTAuthException {

        JwtConsumer jwtConsumer = jwtConsumer(issuer,audience,subject,KEY,algorithm);

        try {

            JwtClaims claims = jwtConsumer.processToClaims(token);

            return claims.getJwtId();

        }catch (InvalidJwtException|MalformedClaimException e){

            if(e instanceof MalformedClaimException){
                throw new JWTAuthException().withReason("malformed claim");
            }else{
                throw new JWTAuthException().withReason(handleException((InvalidJwtException)e));
            }

        }

    }


    @Override
    public String algorithm() {
        return DEFAULT_ALGORITHM;
    }

    /**
     * 设置payload中的值
     *
     * @param id
     * @param subject
     * @param audience
     * @param issuer
     * @param claims
     * @param nbf Not Before 生效时间
     * @param iat Issued At 签发时间
     * @param expire
     * @param timeUnit
     * @return
     */
    private JwtClaims generateClaim(String id ,String issuer , String audience , String subject , Map<String, Object> claims, long nbf , long iat , long expire, TimeUnit timeUnit){
        JwtClaims jwtClaims = new JwtClaims();

        jwtClaims.setIssuer(issuer);

        if(!StringUtils.isEmpty(audience)){
            jwtClaims.setAudience(audience);
        }

        if(!StringUtils.isEmpty(subject)){
            jwtClaims.setSubject(subject);
        }

        if(!StringUtils.isEmpty(id)){
            jwtClaims.setJwtId(id);
        }

        jwtClaims.setNotBefore(NumericDate.fromMilliseconds(nbf));

        jwtClaims.setIssuedAt(NumericDate.fromMilliseconds(iat));


        switch (timeUnit){
            case MILLISECONDS:
                jwtClaims.setExpirationTime(NumericDate.fromMilliseconds(System.currentTimeMillis() + expire));break;
            case SECONDS:
                jwtClaims.setExpirationTime(NumericDate.fromSeconds((System.currentTimeMillis()/1000) + expire));break;
            case MINUTES:
                jwtClaims.setExpirationTime(NumericDate.fromMilliseconds(System.currentTimeMillis() + expire * 60 * 1000));break;
            case HOURS:
                jwtClaims.setExpirationTime(NumericDate.fromMilliseconds(System.currentTimeMillis() + expire * 3600 * 1000));break;
            default:
                jwtClaims.setExpirationTime(NumericDate.fromMilliseconds(System.currentTimeMillis() + 30000));break; // 默认30秒
        }

        claims.entrySet().forEach((claim)-> jwtClaims.setClaim(claim.getKey(),claim.getValue()));

        return jwtClaims;
    }

    /**
     * jws
     *
     * @param key
     * @param kid
     * @param algorithm
     * @return
     */
    private JsonWebSignature jsonWebSignature(Key key, String kid, String algorithm) {
        JsonWebSignature jsonWebSignature = new JsonWebSignature();

        // JWT 使用 RSA 私钥签名
        jsonWebSignature.setKey(key);
        // 可选操作
        if (null != key) {
            jsonWebSignature.setKeyIdHeaderValue(kid);
        }

        // 在 JWT / JWS 上设置签名算法
        jsonWebSignature.setAlgorithmHeaderValue(algorithm);
        return jsonWebSignature;
    }

    /**
     * 创建jwt消费者，用于验证jwt和获取jwt中的数据
     *
     * @param issuer
     * @param audience
     * @param key
     * @param algorithm
     * @return
     */
    private JwtConsumer jwtConsumer(String issuer,String audience,String subject,Key key,String algorithm){
        JwtConsumerBuilder builder = new JwtConsumerBuilder()
                // 设置允许的预期签名算法
                .setJwsAlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, algorithm)
                // 在验证时间时留出一些余量以解决时钟偏差问题
                .setAllowedClockSkewInSeconds(3)
                // 必须设置过期时间
                .setRequireExpirationTime()
                // 必须设置 Token 签发者
                .setExpectedIssuer(issuer)
                // 设置用于验证签名的公钥
                .setVerificationKey(key)
                // 设置解密密钥
                // .setDecryptionKey()
                // 设置解密密钥解析器
                // .setDecryptionKeyResolver()
                // .setDisableRequireSignature()
                ;

        if(audience != null){
            builder.setExpectedAudience(audience);
        }

        if(subject != null){
            builder.setExpectedSubject(subject);
        }

        return builder.build();
    }

    /**
     * 处理验证异常原因
     *
     * @param e
     */
    private String handleException(InvalidJwtException e) {
        log.warn("Invalid JWT",e);
        try {
            JwtClaims jwtClaims = e.getJwtContext().getJwtClaims();
            // 异常是否因 JWT 过期触发
            if (e.hasExpired()) {
                return "Expired at " + jwtClaims.getExpirationTime();
            }
            // 异常是否因 Audience 无效触发
            if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
               return "Invalid audience: " + jwtClaims.getAudience();
            }
            // 异常是否因缺少 Audience 触发
            if (e.hasErrorCode(ErrorCodes.AUDIENCE_MISSING)) {
                return "Audience missing！";
            }
            // 异常是否因缺少加密触发
            if (e.hasErrorCode(ErrorCodes.ENCRYPTION_MISSING)) {
                return "Encryption missing！";
            }
            // 异常是否因缺少过期时间触发
            if (e.hasErrorCode(ErrorCodes.EXPIRATION_MISSING)) {
                return "Expiration missing!";
            }
            // 异常是否因过期时间太长触发
            if (e.hasErrorCode(ErrorCodes.EXPIRATION_TOO_FAR_IN_FUTURE)) {
                return "Expiration too far in future: " + jwtClaims.getExpirationTime();
            }
            // 异常是否因缺乏完整性触发
            if (e.hasErrorCode(ErrorCodes.INTEGRITY_MISSING)) {
                return "Integrity missing!";
            }
            // 异常是否因发布时间无效触发
            if (e.hasErrorCode(ErrorCodes.ISSUED_AT_INVALID_FUTURE)) {
                return "Issued at invalid future: " + jwtClaims.getIssuedAt();
            }
            // 异常是否因发布时间无效触发
            if (e.hasErrorCode(ErrorCodes.ISSUED_AT_INVALID_PAST)) {
                return "Issued at invalid past: " + jwtClaims.getIssuedAt();
            }
            // 异常是否因缺少发布时间触发
            if (e.hasErrorCode(ErrorCodes.ISSUED_AT_MISSING)) {
                return "Issued at missing!";
            }
            // 异常是否因签发者无效触发
            if (e.hasErrorCode(ErrorCodes.ISSUER_INVALID)) {
                return "Issuer invalid: " + jwtClaims.getIssuer();
            }
            // 异常是否因缺少签发者触发
            if (e.hasErrorCode(ErrorCodes.ISSUER_MISSING)) {
                return "Issuer missing!";
            }
            // 异常是否因 JSON 无效触发
            if (e.hasErrorCode(ErrorCodes.JSON_INVALID)) {
                return "Json invalid: " + jwtClaims.toString();
            }
            // 异常是否因缺少 JWT ID 触发
            if (e.hasErrorCode(ErrorCodes.JWT_ID_MISSING)) {
                return "JWT ID missing!";
            }
            // 异常是否因 JwtClaims 格式错误触发
            if (e.hasErrorCode(ErrorCodes.MALFORMED_CLAIM)) {
                return "Malformed claim!";
            }
            // 异常是否因缺少生效时间触发
            if (e.hasErrorCode(ErrorCodes.NOT_BEFORE_MISSING)) {
                return "Not before missing!";
            }
            // 异常是否因 Token 尚未生效触发
            if (e.hasErrorCode(ErrorCodes.NOT_YET_VALID)) {
                return "Not yet valid: " + jwtClaims.getNotBefore();
            }
            // 异常是否因 Token 的 Signature 部分无效触发
            if (e.hasErrorCode(ErrorCodes.SIGNATURE_INVALID)) {
                return "Signature invalid: " + jwtClaims.toString();
            }
            // 异常是否因 Token 的 Signature 部分缺失触发
            if (e.hasErrorCode(ErrorCodes.SIGNATURE_MISSING)) {
                return "Signature missing!";
            }
            // 异常是否因 Subject 无效触发
            if (e.hasErrorCode(ErrorCodes.SUBJECT_INVALID)) {
                return "Subject invalid: " + jwtClaims.getSubject();
            }
            // 异常是否因 Subject 缺失触发
            if (e.hasErrorCode(ErrorCodes.SUBJECT_MISSING)) {
                return "Subject missing!";
            }
            // 异常是否因 Type 无效触发
            if (e.hasErrorCode(ErrorCodes.TYPE_INVALID)) {
                return "Type invalid: " + jwtClaims.getRawJson();
            }
            // 异常是否因 Type 缺失触发
            if (e.hasErrorCode(ErrorCodes.TYPE_MISSING)) {
                return "Type missing!";
            }
        } catch (MalformedClaimException e1) {
            return "Malformed claim: " + e;
        }
        return "Unknown exception";
    }

}
