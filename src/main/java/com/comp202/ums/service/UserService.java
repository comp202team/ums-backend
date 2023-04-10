package com.comp202.ums.service;

import com.comp202.ums.Dto.user.UserDto;
import com.comp202.ums.Dto.user.UserRegisterRequestDto;
import com.comp202.ums.Dto.user.UserRequestDto;
import com.comp202.ums.Entity.Role;
import com.comp202.ums.Entity.User;
import com.comp202.ums.Map.UserMapper;
import com.comp202.ums.Repository.UserRepository;
import com.comp202.ums.exception.AuthorizationException;
import com.comp202.ums.exception.ForbiddenException;
import com.comp202.ums.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null){
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user.getRole()));
        }
        throw new UsernameNotFoundException("Invalid username or password");
    }

    private Collection<SimpleGrantedAuthority> getAuthorities(Role role) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toString()));
        return authorities;
    }
    public UserDto register(UserRegisterRequestDto userRegisterRequestDto){
        User user = new User();
        user.setUsername(userRegisterRequestDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterRequestDto.getPassword()));
        user.setEmail(userRegisterRequestDto.getEmail());
        user.setFirstName(userRegisterRequestDto.getFirstName());
        user.setLastName(userRegisterRequestDto.getLastName());
        user.setRole(Role.STUDENT);
        user = userRepository.save(user);
        return UserMapper.INSTANCE.userToUserDto(user);
    }
    public List<UserDto> getAll() {
        return UserMapper.INSTANCE.userListToUserDtoList(userRepository.findAll());
    }
    public UserDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User", "No user found with this id"));
        currentUserOrAdminControl(user);
        return UserMapper.INSTANCE.userToUserDto(user);
    }
    public UserDto update(Long id, UserRequestDto userRequestDto){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User", "No user found with this id"));
        currentUserOrAdminControl(user);

        if(userRequestDto.getUsername() != null){
            user.setUsername(userRequestDto.getUsername());
        }
        if(userRequestDto.getEmail() != null){
            user.setEmail(userRequestDto.getEmail());
        }
        if(userRequestDto.getFirstName() != null){
            user.setFirstName(userRequestDto.getFirstName());
        }
        if (userRequestDto.getLastName() != null){
            user.setLastName(userRequestDto.getLastName());
        }
        return UserMapper.INSTANCE.userToUserDto(userRepository.save(user));
    }
    public Boolean delete(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User", "No user found with this id"));
        currentUserOrAdminControl(user);
        userRepository.delete(user);
        return Boolean.TRUE;
    }
    public UserDto getByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user != null)
            return UserMapper.INSTANCE.userToUserDto(user);

        return null;
    }
    public User getUserByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new NotFoundException("User", "No user found with this username");
        return user;
    }
    protected User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            return getUserByUsername(currentUsername);
        }
        else {
            throw new AuthorizationException();
        }
    }
    public UserDto getCurrentUserDto(){
        return UserMapper.INSTANCE.userToUserDto(getCurrentUser());
    }
    protected boolean isAdmin(){
        return hasRole("ROLE_INSTRUCTOR");
    }
    protected boolean hasRole(String role){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(role));
    }
    private void currentUserOrAdminControl(User user){
        if(getCurrentUser() != user && !isAdmin()){
            throw new ForbiddenException();
        }
    }


}
