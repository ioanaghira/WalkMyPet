package proiect_final.WalkMyPet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proiect_final.WalkMyPet.domain.Profile;

import java.util.List;

@Repository
public interface ProfileARepository extends CrudRepository<Profile, Integer> {
    public Profile findByEmail(String email);
}

