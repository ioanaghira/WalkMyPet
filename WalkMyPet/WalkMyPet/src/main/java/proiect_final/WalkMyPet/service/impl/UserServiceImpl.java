package proiect_final.WalkMyPet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import proiect_final.WalkMyPet.domain.Profile;

import proiect_final.WalkMyPet.domain.ProfileType;
import proiect_final.WalkMyPet.repository.UserRepository;


import javax.management.relation.Role;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Profile> getAllProfiles() {
        List<Profile> profiles = new ArrayList<>();
        userRepository.findAll().forEach((p -> profiles.add(p)));
        //.forEach(locations::add)
        return profiles;
    }


    public Profile findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<Profile> findById(int id) {
        return userRepository.findById(id);
    }

    public void update(int id, Profile user) {

        userRepository.save(user);
    }

    public Profile editProfile (Profile user){
        return userRepository.save(user);
    }




    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Profile user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                getGrantedAuthorities(user));

    }


    private Collection<GrantedAuthority> getGrantedAuthorities(Profile user) {
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();

        if (user.getProfileType().toString().equals("PROVIDER")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_PROVIDER"));
        } else {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_PET_OWNER"));
        }
        return grantedAuthorities;
    }


}
