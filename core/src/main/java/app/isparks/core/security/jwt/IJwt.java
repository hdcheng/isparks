package app.isparks.core.security.jwt;

import app.isparks.core.security.jwt.exception.JWTAuthException;
import app.isparks.core.security.jwt.exception.JWTException;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * jwt接口
 *
 * @author： chenghd
 * @date： 2021/2/6
 */
public interface IJwt {

    /**
     * 签发 jwt token
     *
     * @param algorithm 算法
     * @param jti 编号
     * @param claims 保存的参数
     * @param issuer 签发人，谁签发了这个令牌
     * @param subject 主题，令牌所针对的主题/主体
     * @param audience 受众，这个令牌将会发送给谁，一般是接收者的url
     * @param nbf 生效时间
     * @param iat 签发时间
     * @param expire 过期时间
     * @param timeUnit 过期时间单位
     * @return jwt token
     * @throws JWTException
     */
    String signJWT(String algorithm , String jti ,String issuer ,String audience ,String subject , Map<String,Object> claims , long nbf , long iat , long expire , TimeUnit timeUnit) throws JWTException;

    /**
     * 验证 jwt
     *
     * @param token 令牌字符
     * @param issuer 签发人，谁签发了这个令牌
     * @param audience 受众，这个令牌将会发送给谁
     * @param subject 主题
     * @throws JWTAuthException
     */
    void verify(String algorithm ,String token,String issuer,String audience,String subject) throws JWTAuthException;

    /**
     * 获取 claims
     *
     * @param token
     * @param issuer 签发者
     * @param audience 受众
     * @return 返回 Map ,返回值不能为 NULL
     */
    Map<String,Object> getClaims(String algorithm , String token, String issuer,String audience,String subject) throws JWTAuthException;

    /**
     * 获取id
     * @param token
     * @return
     * @throws JWTAuthException
     */
    String getId(String algorithm , String token,String issuer, String audience, String subject) throws JWTAuthException;

}
