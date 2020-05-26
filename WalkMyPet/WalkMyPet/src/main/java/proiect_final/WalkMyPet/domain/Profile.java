package proiect_final.WalkMyPet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Profile {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Address address;
    private Email email;
    private String password;
    private ProfileType profileType;
    private List<Pet> petList;
}
