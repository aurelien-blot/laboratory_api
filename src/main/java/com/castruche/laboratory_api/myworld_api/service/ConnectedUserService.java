package com.castruche.laboratory_api.myworld_api.service;


import com.castruche.laboratory_api.main_api.dto.util.ConnectedUserDto;
import com.castruche.laboratory_api.myworld_api.dao.UserRepository;
import com.castruche.laboratory_api.myworld_api.entity.User;
import com.castruche.laboratory_api.myworld_api.formatter.UserFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ConnectedUserService {

    private static final Logger logger = LogManager.getLogger(ConnectedUserService.class);

    private UserRepository userRepository;
    private UserFormatter userFormatter;

    public ConnectedUserService(UserRepository userRepository, UserFormatter userFormatter) {
        this.userRepository = userRepository;
        this.userFormatter = userFormatter;
    }

    public ConnectedUserDto getCurrentUser() {
        User user = this.getCurrentUserEntity();
        return this.userFormatter.entityToConnectedUserDto(user);
    }

    public User getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        String username=authentication.getName();
        return this.userRepository.findByUsername(username);
    }

}
