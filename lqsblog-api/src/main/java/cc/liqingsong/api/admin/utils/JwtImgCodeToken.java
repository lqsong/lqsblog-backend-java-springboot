package cc.liqingsong.api.admin.utils;

import cc.liqingsong.common.exception.CommonRuntimeException;
import cc.liqingsong.common.utils.ImgValidateCodeUtil;
import cc.liqingsong.common.utils.JwtUtils;
import cc.liqingsong.database.vo.admin.ImgCodeTokenVO;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片验证码token
 * @author liqingsong
 */
@Component
public class JwtImgCodeToken {

    private JwtUtils jwtUtils;

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * 生成 token 图片验证码
     */
    public ImgCodeTokenVO createImgCode(){
        Map<String, String> imgCode = ImgValidateCodeUtil.getImgCodeBaseCode(4);
        String code = imgCode.get("imgCode");
        String token = jwtUtils.createJWT(code,code,new HashMap<>());

        ImgCodeTokenVO imgCodeTokenVO = new ImgCodeTokenVO();
        imgCodeTokenVO.setBase64(imgCode.get("data"));
        imgCodeTokenVO.setTokenCode(token);

        return imgCodeTokenVO;
    }

    /**
     * 解析token图片验证码 返回 验证码字符串
     *     token: token加密的验证码字符串
     */
    public String parseImgCode(String token) {
        if (null == token || token.equals("")) {
            return null;
        }
        try {
            Claims claims = jwtUtils.parseJWT(token);
            return claims.getId();
        }catch (Exception e) {
            //e.printStackTrace();
            // throw new RuntimeException("图片验证码过期请刷新验证码");
            throw new CommonRuntimeException(500, "图片验证码过期请刷新验证码");
        }
    }


}
