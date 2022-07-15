package com.bbrick.auth.comn.auth.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                    @Nullable ModelAndView modelAndView) throws Exception {
        logger.debug("handler : {}", handler);
        logger.debug("modelAndView : {}", modelAndView);
    }
}
