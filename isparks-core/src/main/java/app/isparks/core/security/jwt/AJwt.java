package app.isparks.core.security.jwt;

import app.isparks.core.security.jwt.exception.JWTAuthException;
import app.isparks.core.security.jwt.exception.JWTException;
import app.isparks.core.util.IdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 抽象类
 *
 * @author： chenghd
 * @date： 2021/2/6
 */
public abstract class AJwt implements IJwt{

    private Logger log = LoggerFactory.getLogger(getClass());

    private static final String ISSUER = "ISparks";

    /**
     * 默认签发者
     */
    protected String issuer(){
        return ISSUER;
    }

    /**
     *
     * @param subject 主题
     * @param audience 接收者地址
     * @param claims 内容
     * @param expire 过期时间
     * @param timeUnit 过期单位
     * @return token string
     * @throws JWTException
     */
    public String signJWT(String issuer,String audience ,String subject,Map<String,Object> claims , long expire , TimeUnit timeUnit) throws JWTException{
        long current = System.currentTimeMillis();
        return signJWT(algorithm(),IdUtils.id(),issuer,audience,subject,claims,current,current,expire,timeUnit);
    }
    /**
     *
     * @param subject 主题
     * @param audience 接收者地址
     * @param claims 内容
     * @param expire 过期时间
     * @param timeUnit 过期单位
     * @return token string
     * @throws JWTException
     */
    public String signJWT(String audience ,String subject , Map<String,Object> claims , long expire , TimeUnit timeUnit) throws JWTException{
        return signJWT(issuer(),audience,subject,claims,expire,timeUnit);
    }

    /**
     *
     * @param audience 接收者地址
     * @param claims 内容
     * @param expire 过期时间
     * @param timeUnit 过期单位
     * @return token string
     * @throws JWTException
     */
    public String signJWT(String audience,Map<String,Object> claims , long expire , TimeUnit timeUnit) throws JWTException{
        return signJWT(audience,null,claims,expire,timeUnit);
    }

    /**
     * @param claims 内容
     * @param expire 过期时间
     * @param timeUnit 过期单位
     * @return token string
     * @throws JWTException
     */
    public String signJWT(Map<String,Object> claims, long expire, TimeUnit timeUnit) throws JWTException{
        return signJWT(null,null,claims,expire,timeUnit);
    }

    /**
     * 验证 jwt
     *
     * @param token
     * @param audience 受众，这个令牌将会发送给谁
     * @throws JWTAuthException
     */
    public void verify(String token,String issuer,String audience,String subject) throws JWTAuthException{
        verify(algorithm(),token,issuer(),audience,null);
    }

    /**
     * 验证 jwt
     *
     * @param token
     * @param audience 受众，这个令牌将会发送给谁
     * @throws JWTAuthException
     */
    public void verify(String token,String audience) throws JWTAuthException{
        verify(token,issuer(),audience,null);
    }

    /**
     * 验证 jwt
     *
     * @param token
     * @throws JWTAuthException
     */
    public void verify(String token) throws JWTAuthException{
        verify(token,issuer(),null,null);
    }

    /**
     * 验证 jwt
     * @param token
     * @return
     */
    public boolean tryVerify(String token){
        try {
            verify(token);
        }catch (Exception e){
            log.warn("jwt验证失败",e);
            return false;
        }
        return true;
    }

    /**
     * 验证令牌，返回 Optional 中存放异常原因
     *
     * @param token 令牌
     * @param issuer 签发者
     * @param audience 受众
     * @param subject 受众
     * @return 异常原因，如果为空，则验证通过。
     */
    public abstract Optional<String> verifyToken(String token, String issuer, String audience , String subject);

    /**
     * 验证 jwt
     *
     * @param token
     * @param audience 受众，这个令牌将会发送给谁
     * @return 异常原因，如果为空，则验证通过。
     */
    public Optional<String> verifyToken(String token, String audience){
        return verifyToken(token,issuer(),audience,null);
    }


    /**
     * 验证 jwt
     *
     * @param token
     * @return 异常原因，如果为空，则验证通过。
     */
    public Optional<String> verifyToken(String token){
        return verifyToken(token,issuer(),null,null);
    }

    /**
     * 默认加密算法，如 HMAC SHA256
     *
     * @return algorithm
     */
    public abstract String algorithm();

    /**
     * 获取内容
     *
     * @param token
     * @param audience 受众，这个令牌将会发送给谁
     */
    public Map<String, Object> getClaims(String token, String issuer,String audience,String subject) throws JWTAuthException {
        return getClaims(algorithm(),token,issuer(),audience,null);
    }

    /**
     * 获取内容
     *
     * @param token
     * @param audience 受众，这个令牌将会发送给谁
     */
    public Map<String, Object> getClaims(String token, String audience) throws JWTAuthException {
        return getClaims(token,issuer(),audience,null);
    }

    /**
     * 获取内容
     *
     * @param token
     */
    public Map<String, Object> getClaims(String token) throws JWTAuthException {
        return getClaims(token,issuer(),null,null);
    }

    /**
     * 获取token id
     * @param token
     * @return id
     */
    public String tryGetId(String token){
        try {
            return getId(algorithm(),token,issuer(),null,null);
        }catch (Exception e){
            log.error("获取ID异常",e);
            return "";
        }
    }
}
