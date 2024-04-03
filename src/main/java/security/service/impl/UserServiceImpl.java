package security.service.impl;

import org.springframework.stereotype.Service;
import security.persistence.entity.UserEntity;
import security.persistence.repository.IUserRepository;
import security.service.IUserService;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return this.userRepository.findAll();
    }
}
