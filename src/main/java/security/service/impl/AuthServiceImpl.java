package security.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import security.persistence.entity.UserEntity;
import security.persistence.repository.IUserRepository;
import security.service.IAuthService;
import security.service.IJWTUtilityService;
import security.service.model.dto.LoginDTO;
import security.service.model.dto.ResponseDTO;
import security.service.model.validation.UserValidation;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    private final IUserRepository userRepository;
    private final IJWTUtilityService jwtUtilityService;
    private final UserValidation userValidation;

    public AuthServiceImpl(IUserRepository userRepository, IJWTUtilityService jwtUtilityService, UserValidation userValidation) {
        this.userRepository = userRepository;
        this.jwtUtilityService = jwtUtilityService;
        this.userValidation = userValidation;
    }

    @Override
    public HashMap<String, String> login(LoginDTO login) throws Exception {
        try {
            HashMap<String, String> jwt = new HashMap<>();
            Optional<UserEntity> user = this.userRepository.findByEmail(login.getEmail());

            if (user.isEmpty()) {
                jwt.put("Error", "User not registered!");
                return jwt;
            }

            if (verifyPassword(login.getPassword(), user.get().getPassword())) {
                jwt.put("jwt", this.jwtUtilityService.generateJWT(user.get().getId()));
            } else {
                jwt.put("Error", "Authentication failed");
            }

            return jwt;
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    public ResponseDTO register(UserEntity user) throws Exception {
        try {
            ResponseDTO response = this.userValidation.validate(user);

            if (response.getNumOfErrors() > 0) {
                return response;
            }

            List<UserEntity> getAllUsers = this.userRepository.findAll();

            if (getAllUsers.stream().anyMatch(existingUser -> existingUser.getEmail().equals(user.getEmail()))) {
                response.setNumOfErrors(1);
                response.setMessage("User already exists!");
                return response;
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            user.setPassword(encoder.encode(user.getPassword()));
            this.userRepository.save(user);
            response.setMessage("User created successfully!");

            return response;
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    private boolean verifyPassword(String enteredPassword, String storedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, storedPassword);
    }
}
