package com.bbrick.auth.comn.web.session;

import com.bbrick.auth.comn.exceptions.AuthenticationException;
import com.bbrick.auth.comn.web.WebConstants;
import com.bbrick.auth.config.security.authentication.AuthenticatedAuthentication;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpSession;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionUtils {
    public static void setAuthenticatedAuthentication(HttpSession session, AuthenticatedAuthentication authentication) {
        session.setAttribute(WebConstants.Session.ATTRIBUTE_AUTH, authentication);
    }

    public static AuthenticatedAuthentication getAuthenticatedAuthentication(HttpSession session) {
        Object authentication = session.getAttribute(WebConstants.Session.ATTRIBUTE_AUTH);

        if (authentication ==null) {
            throw new AuthenticationException("session not contains authentication");
        }

        return (AuthenticatedAuthentication) authentication;
    }
}
