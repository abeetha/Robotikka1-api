package com.bootcamp.robotikka.robotikkaapi.service.impl;

import com.bootcamp.robotikka.robotikkaapi.dto.request.RequestUserDTO;
import com.bootcamp.robotikka.robotikkaapi.dto.response.CommonResponseDTO;
import com.bootcamp.robotikka.robotikkaapi.entity.User;
import com.bootcamp.robotikka.robotikkaapi.entity.UserRole;
import com.bootcamp.robotikka.robotikkaapi.entity.share.UserNameResource;
import com.bootcamp.robotikka.robotikkaapi.repo.UserRepo;
import com.bootcamp.robotikka.robotikkaapi.repo.UserRoleRepo;
import com.bootcamp.robotikka.robotikkaapi.service.UserService;
import com.bootcamp.robotikka.robotikkaapi.service.process.EmailService;
import com.bootcamp.robotikka.robotikkaapi.util.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Generator generator;
    private final UserRepo userRepo;
    private final UserRoleRepo userRoleRepo;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(Generator generator, UserRepo userRepo, UserRoleRepo userRoleRepo, EmailService emailService) {
        this.generator = generator;
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
        this.emailService = emailService;
    }

    @Override
    public CommonResponseDTO createUser(RequestUserDTO dto) {
        String generatedPrefix = generator.generatedPrefix(5, 16);
        if (userRepo.findByPrefix(generatedPrefix).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        Optional<UserRole> selectedUseRole = userRoleRepo.findByRoleName("USER");
        if(selectedUseRole.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        String primaryKey = generator.generatePrimaryKey(generatedPrefix, "U");
        String verificationCode = generator.createVerificationCode();

        if (emailService.createEmail
                (dto.getEmail(),
                        "Regarding Login",
                        "<h1>Verification Code = " + verificationCode + "</h1>"
                )) {

        User user = new User(primaryKey,
                dto.getContactNumber(), new UserNameResource(
                dto.getFirstName(),dto.getLastName()), dto.getEmail(),
                dto.getPassword(), true,true,true,
                false,generatedPrefix,verificationCode,null,null,null,selectedUseRole.get());
            userRepo.save(user);
            return new CommonResponseDTO(200,dto.getEmail()+" Send Email",primaryKey);
    }else{
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
    @Override
    public CommonResponseDTO verifyAccount(String email, String otp) {
        Optional<User> selectedUser = userRepo.findByEmail(email);
        if (selectedUser.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (selectedUser.get().isEnabled()) throw new ResponseStatusException(HttpStatus.OK);
        if (selectedUser.get().getOtp().equals(otp)) {
            selectedUser.get().setEnabled(true);
            userRepo.save(selectedUser.get());
            return new CommonResponseDTO(201,"Activated",null);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
