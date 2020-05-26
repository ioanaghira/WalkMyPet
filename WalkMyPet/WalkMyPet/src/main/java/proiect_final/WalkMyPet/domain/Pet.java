package proiect_final.WalkMyPet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Pet {
    private int id;
    private String name;
    private String breed;
    private String description;
    private double weightInKg;
}
