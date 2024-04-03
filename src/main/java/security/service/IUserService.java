package security.service;

import security.persistence.entity.UserEntity;

import java.util.List;

public interface IUserService {

    List<UserEntity> findAllUsers();
}
