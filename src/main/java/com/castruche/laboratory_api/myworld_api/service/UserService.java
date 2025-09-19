package com.castruche.laboratory_api.myworld_api.service;

import com.castruche.laboratory_api.main_api.service.GenericService;
import com.castruche.laboratory_api.myworld_api.dao.UserRepository;
import com.castruche.laboratory_api.myworld_api.dto.user.UserContactDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserLightDto;
import com.castruche.laboratory_api.myworld_api.entity.User;
import com.castruche.laboratory_api.myworld_api.formatter.UserFormatter;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User, UserDto, UserLightDto> {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final UserFormatter userFormatter;

    public UserService(UserRepository userRepository, UserFormatter userFormatter) {
        super(userRepository, userFormatter);
        this.userRepository = userRepository;
        this.userFormatter = userFormatter;
    }

    @Transactional
    public User selectByUsername(String userName){
        return userRepository.findByUsername(userName);
    }


}
