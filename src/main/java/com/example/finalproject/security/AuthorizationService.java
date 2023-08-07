package com.example.finalproject.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collection;

@Service
public class AuthorizationService {
    public boolean isAdmin(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
    }

    public void addUsernameToModel(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
    }
}
