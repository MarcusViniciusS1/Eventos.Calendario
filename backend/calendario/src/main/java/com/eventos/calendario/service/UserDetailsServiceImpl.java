package com.eventos.calendario.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=== CARREGANDO USUÁRIO ===");
        System.out.println("Usuário solicitado: " + username);


        switch (username) {
            case "ADM":

                String passwordADM = "$2a$10$N.zmdr9k7uOCQb97.AnUu.Zm8GjjHfW8t5pxHuMIUlbcbhfYo6T5e";
                System.out.println("✅ Usuário ADM encontrado");
                System.out.println("Senha esperada: ADM123");
                return new User("ADM", passwordADM, new ArrayList<>());

            case "admin":

                String passwordAdmin = "$2a$10$DowJonesIndex123456789uIjLVqovEWS2.2BhstYewjy.";
                System.out.println("✅ Usuário admin encontrado");
                System.out.println("Senha esperada: admin");
                return new User("admin", passwordAdmin, new ArrayList<>());

            default:
                System.out.println("❌ Usuário não encontrado: " + username);
                throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
    }


    public String encodePassword(String rawPassword) {
        String encoded = passwordEncoder.encode(rawPassword);
        System.out.println("Senha '" + rawPassword + "' criptografada: " + encoded);
        return encoded;
    }
}