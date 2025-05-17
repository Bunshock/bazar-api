package com.bunshock.Bazar.service.interfaces;

import com.bunshock.Bazar.dto.auth.RegisterUserDTO;


public interface IUserService {
    
    void registrarUser(RegisterUserDTO registroDTO);
    
}
