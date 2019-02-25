package com.justh5.experiment.filter;

import com.alibaba.druid.util.PatternMatcher;
import com.alibaba.druid.util.ServletPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: songxiaoyue
 * @Date: 2018/7/4 11:55
 */
public class BaseFilter {
    protected static PatternMatcher pathMatcher = new ServletPathMatcher();
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/html/*","/info","/view/*","/layui/*","/css/*","/zTree_v3/*","/js/*")));

    public static boolean allow(HttpServletRequest req){
        //静态资源，直接放行
        for (String pattern : ALLOWED_PATHS) {
            if (pathMatcher.matches(pattern, req.getServletPath())) {
                return true;
            }
        }
        return false;
    }
}
