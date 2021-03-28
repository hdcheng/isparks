package app.isparks.core.security.jwt;

import app.isparks.core.security.jwt.exception.JWTAuthException;
import app.isparks.core.security.jwt.exception.JWTException;
import app.isparks.core.security.jwt.impl.Jose4jImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author： chenghd
 * @date： 2021/2/6
 */
public class JwtHandler extends AJwt{

    private Logger log = LoggerFactory.getLogger(getClass());

    private static AJwt jwt = new Jose4jImpl();

    private static volatile JwtHandler handler = null;

    private JwtHandler(){}

    public static JwtHandler build(){
        if(handler == null){
            synchronized (JwtHandler.class){
                if(handler == null){
                    handler = new JwtHandler();
                }
            }
        }
        return handler;
    }


    @Override
    public String algorithm() {
        return jwt.algorithm();
    }

    @Override
    public String signJWT(String algorithm, String jti, String issuer, String audience, String subject, Map<String, Object> claims, long nbf, long iat, long expire, TimeUnit timeUnit) throws JWTException {
        return jwt.signJWT( algorithm,  jti,  issuer,  audience,  subject,  claims, nbf, iat, expire, timeUnit);
    }

    @Override
    public void verify(String token, String issuer, String audience, String subject) throws JWTAuthException {
        jwt.verify(token, issuer, audience, subject);
    }

    @Override
    public void verify(String algorithm, String token, String issuer, String audience, String subject) throws JWTAuthException {
        jwt.verify( algorithm,  token,  issuer,  audience,  subject);
    }

    @Override
    public Optional<String> verifyToken(String token, String issuer, String audience, String subject) {
        return jwt.verifyToken( token, issuer, audience, subject);
    }

    @Override
    public Map<String, Object> getClaims(String token, String issuer, String audience, String subject) throws JWTAuthException {
        return jwt.getClaims( token, issuer, audience, subject);
    }

    @Override
    public Map<String, Object> getClaims(String algorithm, String token, String issuer, String audience, String subject) throws JWTAuthException {
        return jwt.getClaims( algorithm, token, issuer, audience, subject);
    }

    @Override
    public String getId(String algorithm , String token,String issuer, String audience, String subject) throws JWTAuthException {
        return jwt.getId(algorithm , token , issuer , audience , subject);
    }

    public static void main(String[] args)throws Exception {
        Map<String,Object> payload = new HashMap<>();
        payload.put("name","zhangsan");

        String token = JwtHandler.build().signJWT(payload,8,TimeUnit.HOURS);

        boolean result = JwtHandler.build().tryVerify(token);
        System.out.println(result);

    }

}
