package com.hardwarehub.user_service.service;

import com.hardwarehub.user_service.mapper.UserMapper;
import com.hardwarehub.user_service.repository.UserRepository;
import com.hardwarehub.user_service.dto.PasswordRequestDTO;
import com.hardwarehub.user_service.dto.UserResponseDTO;
import com.hardwarehub.user_service.entity.UserEntity;
import com.hardwarehub.user_service.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO getMe()
    {
        return userMapper.entityToDTO(findCurrentUserOrThrow());
    }

    public Page<UserResponseDTO> getAll(Pageable pageable)
    {
        Page<UserEntity> users = userRepository.findAll(pageable);

        return users.map(userMapper::entityToDTO);

    }

    public Page<UserResponseDTO> getAllByUsernameLike(String username, Pageable pageable)
    {
        Page<UserEntity> users = userRepository.findByUsernameContainingIgnoreCase(username, pageable);

        return users.map(userMapper::entityToDTO);
    }

    public Page<UserResponseDTO> getAllByRole(Role role, Pageable pageable)
    {
        Page<UserEntity> users = userRepository.findAllByRole(role, pageable);

        return users.map(userMapper::entityToDTO);
    }

    public UserResponseDTO getById(int id)
    {
        return userMapper.entityToDTO(findByIdOrThrow(id));
    }

    public UserResponseDTO getByEmail(String email)
    {
        return userMapper.entityToDTO(findByEmailOrThrow(email));
    }

    public void deleteMe(PasswordRequestDTO requestDTO)
    {
        UserEntity user = findCurrentUserOrThrow();

        if (!passwordEncoder.matches(requestDTO.password(), user.getPassword()))
        {
            throw new RuntimeException("Invalid password.");
        }

        userRepository.delete(user);
    }

    public void deleteById(int id)
    {
        UserEntity user = findByIdOrThrow(id);

        validateIsNotAdmin(user);

        deleteUser(user);
    }

    public void deleteByEmail(String email)
    {
        UserEntity user = findByEmailOrThrow(email);

        validateIsNotAdmin(user);

        deleteUser(user);
    }

    private UserEntity findCurrentUserOrThrow()
    {
        // Throws an exception if the user doesn't exist or is an admin.
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    private UserEntity findByIdOrThrow(int id)
    {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    private UserEntity findByEmailOrThrow(String email)
    {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    private void deleteUser(UserEntity user)
    {
        // TODO: Add logs and more
        userRepository.delete(user);
    }

    private void validateIsNotAdmin(UserEntity user)
    {
        if (user.getRole() == Role.ADMIN)
        {
            throw new AccessDeniedException("You are not allowed to check or modify this user.");
        }
    }
}
