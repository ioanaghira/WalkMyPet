package proiect_final.WalkMyPet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import proiect_final.WalkMyPet.domain.Profile;


public interface UserRepository extends JpaRepository<Profile, Integer> {

    Profile findByEmail(String email);


}
