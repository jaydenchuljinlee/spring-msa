package com.bbrick.auth.comn.auth.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ApiInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        logger.debug("handler : {}", handler);
        logger.debug("modelAndView : {}", modelAndView);
    }
}
