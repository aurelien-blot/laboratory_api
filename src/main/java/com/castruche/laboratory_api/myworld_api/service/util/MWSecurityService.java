package com.castruche.laboratory_api.myworld_api.service.util;

import com.castruche.laboratory_api.main_api.configuration.JwtUtil;
import com.castruche.laboratory_api.main_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.laboratory_api.main_api.enums.ResponseCodeEnum;
import com.castruche.laboratory_api.main_api.service.util.SecurityService;
import com.castruche.laboratory_api.myworld_api.dao.UserRepository;
import com.castruche.laboratory_api.myworld_api.dto.user.UserDto;
import com.castruche.laboratory_api.myworld_api.entity.User;
import com.castruche.laboratory_api.myworld_api.formatter.UserFormatter;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MWSecurityService extends SecurityService {

    private static final Logger logger = LogManager.getLogger(SecurityService.class);
    private final MWSettingService settingService;
    private final MWMailService mailService;
    private UserRepository userRepository;

    private final UserFormatter userFormatter;

    private final JwtUtil jwtTokenUtil;

    public MWSecurityService(MWSettingService settingService,
                             JwtUtil jwtTokenUtil,
                             UserRepository userRepository, MWMailService mailService,
                             UserFormatter userFormatter) {
        super(userRepository);
        this.settingService = settingService;
        this.mailService = mailService;
        this.userFormatter = userFormatter;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Transactional
    public BooleanResponseDto checkTentativesConnexions(User user, BooleanResponseDto result) {
        Integer tentativesMax = settingService.getTentativesBeforeBlocking();
        if(null==tentativesMax){
            throw new IllegalArgumentException("Le nombre de tentatives avant blocage n'est pas défini.");
        }
        if(user.getTentatives()>=tentativesMax){
            user.setBlocked(true);
            UserDto userDto = userFormatter.entityToDto(user);
            if(user.getTentatives()==tentativesMax){
                user.setResetPasswordToken(jwtTokenUtil.generateToken(user.getUsername()));
                this.mailService.sendMailForPasswordReset(userDto, user.getResetPasswordToken());
            }
            this.userRepository.save(user);
            result.setStatus(false);
            result.setCode(ResponseCodeEnum.FORBIDDEN.getCode());
            result.setMessage("Votre compte a été bloqué suite à un trop grand nombre de tentatives de connexion.\nUn mail vous a été envoyé pour réinitialiser votre mot de passe." +
                    "\nVous pouvez aussi cliquer sur \"Mot de passe oublié\" pour en recevoir un nouveau.");
        }
        else{
            result.setStatus(false);
            result.setMessage("Les identifiants de connexion sont incorrects.");
        }
        return result;
    }


}
