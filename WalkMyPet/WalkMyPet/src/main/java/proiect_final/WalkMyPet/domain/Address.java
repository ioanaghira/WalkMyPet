package proiect_final.WalkMyPet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @NotBlank(message = "Please insert the Street Number!")
    @Column(name ="street_number")
    private String streetNumber;

    @NotBlank(message = "Please insert the Street Name!")
    @Column(name ="street")
    private String street;

    @NotBlank(message = "Please insert the Zone! If doesn't exist, please type 'NO ZONE'!")
    @Column(name ="zone")
    private String zone;

    @NotBlank(message = "Please insert the City Name!")
    @Column(name ="city")
    private String city;

    @NotBlank(message = "Please insert the Post Code!")
    @Column(name ="postcode")
    private String postcode;

    @OneToOne(mappedBy = "address", targetEntity = Profile.class)
    private Profile profile;

    public Address() {
    }

    public Address(String streetNumber, String street, String zone, String city, String postcode) {
        this.streetNumber = streetNumber;
        this.street = street;
        this.zone = zone;
        this.city = city;
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return "Street: " + street + "\n" +
                "StreetNumber: " + streetNumber + "\n" +
                "Zone: " + zone + "\n" +
                "City: " + city + "\n" +
                "Postcode: " + postcode;
    }
}
