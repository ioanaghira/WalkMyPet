package proiect_final.WalkMyPet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import proiect_final.WalkMyPet.repository.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class WalkMyPetApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WalkMyPetApplication.class, args);
    }

}
