package com.vkopendoh.rssapp.service;

import com.vkopendoh.rssapp.model.User;
import com.vkopendoh.rssapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseGet(User::new);
    }

    public void addUser(User user){
        userRepository.save(user);
    }

}
