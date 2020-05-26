package proiect_final.WalkMyPet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Address {
    private int id;
    private String streetNumber;
    private String street;
    private String zone;
    private String city;
    private String postcode;
}
