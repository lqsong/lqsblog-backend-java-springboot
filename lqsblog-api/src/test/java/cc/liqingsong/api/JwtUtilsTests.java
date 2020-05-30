package cc.liqingsong.api;

import cc.liqingsong.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class JwtUtilsTests {

    @Autowired
    private JwtUtils jwtUtils;

   // @Test
    public void testNativeJWT() {

        JwtBuilder jwtBuilder = Jwts.builder().setId("1000")
                .setSubject("王二")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "lqsblog")
                .claim("userId", "1")
                .claim("userName","小二");
        String token = jwtBuilder.compact();
        System.out.println(token);

    }

    //@Test
    public void testNativeParseJWT(){
        String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMDAwIiwic3ViIjoi546L5LqMIiwiaWF0IjoxNTg3NjM2ODQ2LCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiLlsI_kuowifQ.FVuGkM_Bhh5mBHrsYsFpjvYwDUpGLh5-e5_DxDvLSPs";
        Claims claims = Jwts.parser().setSigningKey("lqsblog").parseClaimsJws(token).getBody();
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getIssuedAt());


        String userId = (String)claims.get("userId");
        String userName = (String)claims.get("userName");
        System.out.println(userId);
        System.out.println(userName);


    }


    //@Test
    public void testJwt() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email","957698457@qq.com");
        map.put("phone", "13770779817");
        String token = jwtUtils.createJWT("1000","小二",map);
        System.out.println(token);
    }

    //@Test
    public void testParseJwt() {
        Claims claims = jwtUtils.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMDAwIiwic3ViIjoi5bCP5LqMIiwiaWF0IjoxNTg3NzI1ODM3LCJwaG9uZSI6IjEzNzcwNzc5ODE3IiwiZW1haWwiOiI5NTc2OTg0NTdAcXEuY29tIiwiZXhwIjoxNTg3NzI1OTU3fQ.Av2MnLSOTeiLgFXiBNmLO3eRj-bJvAhDPXx-xCV5KMk");

        String id = claims.getId();
        String name = claims.getSubject();
        String email = (String) claims.get("email");
        String phone = (String) claims.get("phone");
        System.out.println(id);
        System.out.println(name);
        System.out.println(email);
        System.out.println(phone);

    }



}
