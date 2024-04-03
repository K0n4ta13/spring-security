package security.service;

import security.persistence.entity.UserEntity;
import security.service.model.dto.LoginDTO;
import security.service.model.dto.ResponseDTO;

import java.util.HashMap;

public interface IAuthService {

    HashMap<String, String> login(LoginDTO login) throws Exception;
    ResponseDTO register(UserEntity user) throws Exception;
}
