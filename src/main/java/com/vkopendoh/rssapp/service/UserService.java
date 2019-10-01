package com.vkopendoh.rssapp.service;

import com.vkopendoh.rssapp.model.User;
import com.vkopendoh.rssapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User getUserFromRepo(User user){
        return userRepository.findById(user.getId()).orElseGet(() -> user);
    }

    public void addUser(User user){
        userRepository.save(user);
    }

}
