package com.castruche.laboratory_api.myworld_api.service;

import com.castruche.laboratory_api.main_api.configuration.JwtUtil;
import com.castruche.laboratory_api.main_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.laboratory_api.main_api.dto.util.PasswordDto;
import com.castruche.laboratory_api.main_api.dto.util.UserMailDto;
import com.castruche.laboratory_api.main_api.service.GenericService;
import com.castruche.laboratory_api.main_api.service.util.TypeFormatService;
import com.castruche.laboratory_api.myworld_api.dao.UserRepository;
import com.castruche.laboratory_api.myworld_api.dto.login.LoginResponseDto;
import com.castruche.laboratory_api.myworld_api.dto.login.LoginUserDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserDto;
import com.castruche.laboratory_api.myworld_api.entity.User;
import com.castruche.laboratory_api.myworld_api.formatter.UserFormatter;
import com.castruche.laboratory_api.myworld_api.service.util.MWMailService;
import com.castruche.laboratory_api.myworld_api.service.util.MWSecurityService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MWLoginService extends GenericService<User, UserDto, UserDto> {

    private static final Logger logger = LogManager.getLogger(MWLoginService.class);
    private final UserRepository userRepository;
    private final UserFormatter userFormatter;
    private final JwtUtil jwtTokenUtil;
    private final MWMailService mailService;
    private final TypeFormatService typeFormatService;
    private final MWSecurityService securityService;

    public MWLoginService(UserRepository userRepository,
                          UserFormatter userFormatter,
                          JwtUtil jwtTokenUtil,
                          MWMailService mailService,
                          MWSecurityService securityService,
                          TypeFormatService typeFormatService) {
        super(userRepository, userFormatter);
        this.userRepository = userRepository;
        this.userFormatter = userFormatter;
        this.jwtTokenUtil = jwtTokenUtil;
        this.mailService = mailService;
        this.typeFormatService = typeFormatService;
        this.securityService = securityService;
    }

    public BooleanResponseDto checkUsernameAvailability(String username) {
        Boolean exists = userRepository.existsByUsername(username);
        BooleanResponseDto response = new BooleanResponseDto();
        response.setStatus(!Boolean.TRUE.equals(exists));
        if(response.isStatus()){
            response.setMessage("Nom d'utilisateur disponible.");
        } else {
            response.setMessage("Le nom d'utilisateur " + username + " est déjà utilisé. Veuillez en choisir un autre.");
        }
        return response;
    }

    public BooleanResponseDto checkMailAvailability(String mail) {
        Boolean exists = userRepository.existsByEmail(mail);
        BooleanResponseDto response = new BooleanResponseDto();
        response.setStatus(!Boolean.TRUE.equals(exists));
        if(response.isStatus()){
            response.setMessage("Adresse Email disponible.");
        } else {
            response.setMessage("L'adresse' " + mail + " est déjà utilisée.");
        }
        return response;
    }

    @Transactional
    public UserDto register(UserDto userDto) {
        this.checkUserRegistrationValidity(userDto);
        User user = userFormatter.dtoToEntity(userDto);
        String password = userDto.getPassword();
        user.setPassword(this.securityService.encodePassword(password));
        user.setMailVerificationToken(jwtTokenUtil.generateToken(user.getEmail()));
        this.userRepository.save(user);
        user.setLastVerificationMailDate(LocalDateTime.now());
        UserDto userDtoSaved = selectDtoById(user.getId());
        this.mailService.sendMailForRegistration(userDtoSaved);
        this.mailService.sendMailForMailVerification(userDtoSaved, user.getMailVerificationToken());
        this.userRepository.save(user);
        return userDtoSaved;
    }

    private void checkUserRegistrationValidity(UserDto userDto){
        if(userDto==null || userDto.getUsername() ==null || userDto.getEmail() ==null || userDto.getPassword() ==null){
            throw new IllegalArgumentException("Données manquantes.");
        }
        if(!typeFormatService.isMail(userDto.getEmail()) && userDto.getPassword().length()<8){
            throw new IllegalArgumentException("Données invalides.");
        }
    }

    @Transactional
    public LoginResponseDto login(LoginUserDto userDto) throws EntityNotFoundException {
        LoginResponseDto response = new LoginResponseDto();
        User user = userRepository.findByUsername(userDto.getIdentifier());
        if(user == null){
            user = userRepository.findByEmail(userDto.getIdentifier());
        }
        if(user == null){
            response.setSuccess(false);
            response.setMessage("Utilisateur non trouvé.");
            return response;
        }
        else if(user.isBlocked()){
            response.setSuccess(false);
            response.setMessage("Votre compte a été bloqué suite à un trop grand nombre de tentatives de connexion.\nUn mail vous a été envoyé pour réinitialiser votre mot de passe." +
                    "\nVous pouvez aussi cliquer sur \"Mot de passe oublié\" pour en recevoir un nouveau.");
            return response;
        }
        BooleanResponseDto checkPasswordResponse = securityService.checkPassword(userDto.getPassword(), user);
        if(checkPasswordResponse.isStatus()){
            response.setSuccess(true);
            response.setMessage("Connexion réussie.");
            final String token = jwtTokenUtil.generateToken(user.getUsername());
            response.setToken(token);
            response.setUser(userFormatter.entityToDto(user));
        }
        else{
            BooleanResponseDto checkFalseResponse = securityService.checkTentativesConnexions(user, checkPasswordResponse);
            response.setSuccess(false);
            response.setMessage(checkFalseResponse.getMessage());

        }
        return response;
    }


    @Transactional
    public BooleanResponseDto verifyMail(String token) {
        User user = userRepository.findByMailVerificationToken(token);
        BooleanResponseDto response = new BooleanResponseDto();
        if(user == null){
            response.setStatus(false);
            response.setMessage("Token invalide.");
        } else {
            user.setMailVerificationToken(null);
            user.setLastVerificationMailDate(null);
            user.setMailVerified(true);
            userRepository.save(user);
            response.setStatus(true);
            response.setMessage("Email vérifié.");
        }
        return response;
    }

    @Transactional
    public BooleanResponseDto sendResetPasswordMail(UserMailDto userMailDto) {
        String mail = userMailDto.getEmail();
        BooleanResponseDto response = new BooleanResponseDto();
        User user = userRepository.findByEmail(mail);
        response.setStatus(true);
        if(user == null){
            //On ne précise pas volontairement si l'email existe ou non pour des raisons de sécurité
            response.setMessage("Email non envoyé.");
        }
        else {
            user.setResetPasswordToken(jwtTokenUtil.generateToken(user.getEmail()));
            this.userRepository.save(user);
            UserDto userDtoSaved = selectDtoById(user.getId());
            this.mailService.sendMailForPasswordReset(userDtoSaved, user.getResetPasswordToken());
            response.setMessage("Email envoyé.");
        }
        return response;
    }

    @Transactional
    public BooleanResponseDto resetPassword(PasswordDto passwordDto) {
        BooleanResponseDto response = new BooleanResponseDto();

        if(passwordDto == null || passwordDto.getToken() ==null || passwordDto.getPassword() ==null){
            response.setStatus(false);
            response.setMessage("Une donnée est manquante.");
            return response;
        }
        if(passwordDto.getPassword().length()<8){
            response.setStatus(false);
            response.setMessage("Le mot de passe doit contenir au moins 8 caractères.");
            return response;
        }

        User user = userRepository.findByResetPasswordToken(passwordDto.getToken());

        if(user == null){
            response.setStatus(false);
            response.setMessage("Token invalide.");
        }
        else {
            BooleanResponseDto comparePasswordResponse = securityService.comparePassword(passwordDto.getPassword(), user.getPassword(), "Le nouveau mot de passe doit être différent de l'ancien.");
            if(!comparePasswordResponse.isStatus()){
                return comparePasswordResponse;
            }
            else{
                user.setPassword(this.securityService.encodePassword(passwordDto.getPassword()));
                user.setResetPasswordToken(null);
                user.setTentatives(0);
                user.setBlocked(false);
                userRepository.save(user);
                response.setStatus(true);
                response.setMessage("Mot de passe réinitialisé.");
            }
        }
        return response;
    }

}
