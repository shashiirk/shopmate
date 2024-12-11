package dev.shashiirk.shopmate.service.impl;

import dev.shashiirk.shopmate.domain.User;
import dev.shashiirk.shopmate.dto.UserDTO;
import dev.shashiirk.shopmate.exception.EmailAlreadyUsedException;
import dev.shashiirk.shopmate.mapper.UserMapper;
import dev.shashiirk.shopmate.repository.UserRepository;
import dev.shashiirk.shopmate.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link User}.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        userRepository.findByEmail(userDTO.getEmail()).ifPresent(user -> {
            boolean removed = removeNonActiveExistingUser(user);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        return save(userDTO);
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDTO);
    }

    private boolean removeNonActiveExistingUser(User user) {
        if (user.isActive()) {
            return false;
        }
        userRepository.deleteById(user.getId());
        userRepository.flush();
        return true;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public Optional<UserDTO> partialUpdate(UserDTO userDTO) {
        return userRepository.findById(userDTO.getId()).map(user -> {
            userMapper.partialUpdate(userDTO, user);

            return user;
        }).map(userMapper::toDTO);
    }

    @Override
    public Optional<UserDTO> findOne(Long id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
