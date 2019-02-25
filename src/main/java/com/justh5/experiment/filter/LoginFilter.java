package com.justh5.experiment.filter;

import com.justh5.experiment.domain.Cache;
import com.justh5.experiment.model.UserModel;
import com.justh5.experiment.util.CacheManager;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements HandlerInterceptor {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpServletRequest req = httpServletRequest;
        HttpServletResponse rep = httpServletResponse;
        //静态资源，直接放行
        if(BaseFilter.allow(httpServletRequest)){
            return true;
        }
        //权限验证，有token或者存在session信息，token为app提供
        String authToken = req.getHeader("token");
        if(!StringUtils.isEmpty(authToken)){
            Cache cache=CacheManager.getCacheInfo(authToken);
            if(cache!=null) {
                UserModel userModel = (UserModel)cache.getValue();
                if(userModel!=null&&userModel.getId()>0){
                    return true;
                }
            }
        }else{
            UserModel userModel=(UserModel) req.getSession().getAttribute("user");
            if(userModel!=null&&userModel.getId()>0){
                return true;
            }
        }
        rep.sendRedirect("/admin/index");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        System.out.println("post");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        System.out.println("after");
    }
}
