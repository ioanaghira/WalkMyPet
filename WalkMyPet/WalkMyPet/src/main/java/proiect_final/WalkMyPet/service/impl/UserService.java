package proiect_final.WalkMyPet.service.impl;

import org.springframework.security.core.userdetails.UserDetailsService;
import proiect_final.WalkMyPet.domain.Profile;

import java.util.Optional;


public interface UserService extends UserDetailsService {

    Profile findByEmail(String email);

    public Optional<Profile> findById(int id);


}

