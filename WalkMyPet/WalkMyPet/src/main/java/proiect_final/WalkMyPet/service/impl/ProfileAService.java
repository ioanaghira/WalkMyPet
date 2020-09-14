package proiect_final.WalkMyPet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proiect_final.WalkMyPet.domain.Profile;
import proiect_final.WalkMyPet.repository.ProfileARepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileAService {


    @Autowired
    private ProfileARepository profileARepository;

    public void addProfile(Profile profile) {
        profileARepository.save(profile);
    }

    public List<Profile> getAllProfiles(){
        List<Profile> profiles = new ArrayList<>();
        profileARepository.findAll()
                .forEach(profiles :: add);
        return profiles;
    }

    public Optional<Profile> findById(int id) {
        return profileARepository.findById(id);
    }

    public Profile findByEmail(String email) {
        return profileARepository.findByEmail(email);
    }

//    public void editProfile(Profile profile, int id) {
//        testProfileRepository.save(profile);
//    }

//    public void deleteProfile(Profile profile) {
//        testProfileRepository.delete(profile);
//    }
}
