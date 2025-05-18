package com.bunshock.Bazar.utils;

import com.bunshock.Bazar.exception.security.NotAuthenticatedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtils {
    
    public static String getLoggedUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new NotAuthenticatedException();
        }
        return auth.getName();
    }
    
}
