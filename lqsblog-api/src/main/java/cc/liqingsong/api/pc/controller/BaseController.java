package cc.liqingsong.api.pc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * API-PC - BaseController
 * @author liqingsong
 */
public class BaseController {

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    @ModelAttribute
    public void setReqAnRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public Page getPage() {
        long var1 = 1L;
        long var3 = 10L;
        String var5 = this.request.getParameter("page");
        if (!StringUtils.isEmpty(var5)) {
            Long var6 = Long.valueOf(var5);
            if (var6 > 0L) {
                var1 = var6;
            }
        }

        String var8 = this.request.getParameter("per");
        if (!StringUtils.isEmpty(var8)) {
            Long var7 = Long.valueOf(var8);
            if (var7 > 0L) {
                var3 = var7;
            }
        }

        return new Page(var1, var3);
    }



}
