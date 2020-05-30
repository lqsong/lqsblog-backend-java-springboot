package cc.liqingsong.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Date;
import java.util.Map;

/**
 * JWT工具
 * @author liqingsong
 */
/**
 * // @Component
 * // 不能作为组件设置，因为这样 springboot 会自动扫描进入，如果其他模块不需要就会有冲突，因为设置了 @ConfigurationProperties("jwt.config")
 * // 需要自己手动配置
 */
@Getter
@Setter
@ConfigurationProperties("lqsblog.jwt.config")
@EnableConfigurationProperties(JwtUtils.class)
public class JwtUtils {

    // 签名私钥
    private String key;

    // 签名失效时间 - 毫秒 3600000（1小时）
    private Long ttl;

    // 距离签名失效时间多少内可以重置签名- 毫秒 1800000（0.5小时）
    private Long restttl;

    /**
     * 设置认证token
     *     id: 登录用户id
     *     name: 登录用户name
     */
    public String createJWT(String id, String name, Map<String, Object> map) {
        // 1、设置失效时间
        long now = System.currentTimeMillis(); // 当前毫秒
        long exp = now + ttl;
        // 2、创建jwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder().setId(id).setSubject(name)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key);
        // 3、根据map设置claims
        // jwtBuilder.setClaims(map); // 此方法会覆盖 setId,setSubject
        for (Map.Entry<String,Object> entry : map.entrySet()) {
            jwtBuilder.claim(entry.getKey(), entry.getValue());
        }
        // 4、设置失效时间
        jwtBuilder.setExpiration(new Date(exp));
        // 5、创建 token
        String token = jwtBuilder.compact();
        return token;
    }

    /**
     * 解析token字符串, 获取clamis
     *     token: token字符串
     */
    public Claims parseJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * 解析token字符串, 判断失效时间，返回是否生成新的token
     *     token: token字符串
     */
    public Boolean expirationNewJWT(String token) {
        Claims claims = parseJWT(token);

        Date expiration = claims.getExpiration();
        long expTime = expiration.getTime();

        Date date = new Date();
        long dateTime = date.getTime();

        // 说明token已过期
        if(dateTime >= expTime) {
            return false;
        }

        // 说明token 在 restttl 时间内将会过期
        if(expTime-dateTime <= restttl) {
            return true;
        }


        return false;
    }





}
