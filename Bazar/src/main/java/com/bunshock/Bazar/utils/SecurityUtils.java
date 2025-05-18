package com.bunshock.Bazar.utils;

import com.bunshock.Bazar.exception.app.UserWithoutClientException;
import com.bunshock.Bazar.exception.security.NotAuthenticatedException;
import com.bunshock.Bazar.exception.security.UserNotFoundException;
import com.bunshock.Bazar.model.UserEntity;
import com.bunshock.Bazar.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class SecurityUtils {
    
    private final IUserRepository userRepository;

    @Autowired
    public SecurityUtils(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public String getLoggedUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new NotAuthenticatedException();
        }
        return auth.getName();
    }
    
    public UserEntity getUserFromContext() {
        String username = this.getLoggedUsername();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("obtener usuario del security context", username));
        
        if (user.getClient() == null) throw new UserWithoutClientException("obtener usuario del security context", username);
        
        return user;
    }
    
}
