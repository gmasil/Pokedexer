package de.gmasil.collection.setup;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import de.gmasil.collection.security.UserService;

@Service
public class InitialSetupInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        if (!userService.hasUsers() //
                && !request.getRequestURI().startsWith("/setup") //
                && !request.getRequestURI().startsWith("/error") //
                && !request.getRequestURI().startsWith("/public/")) {//
            response.sendRedirect("/setup");
            return false;
        }
        return true;
    }
}
