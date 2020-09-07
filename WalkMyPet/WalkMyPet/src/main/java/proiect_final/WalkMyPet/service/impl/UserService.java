package proiect_final.WalkMyPet.service.impl;

import org.springframework.security.core.userdetails.UserDetailsService;
import proiect_final.WalkMyPet.domain.Profile;

public interface UserService extends UserDetailsService {
    Profile findByEmail(String email);


}

