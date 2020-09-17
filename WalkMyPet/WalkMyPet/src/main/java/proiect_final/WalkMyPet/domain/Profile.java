package proiect_final.WalkMyPet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import proiect_final.WalkMyPet.service.helper.customAnnotations.EmailExists;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@EmailExists(message = "Email address exists! Register with another!", email = "email")
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "first_name")
    @NotBlank(message = "Please insert your First Name!")
    private String firstName;

    @NotBlank(message = "Please insert your Last Name!")
    @Column(name = "last_name")
    private String lastName;


    @Email
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Enter a valid email address!")
    @EmailExists(message = "Email address exists! Register with another!", email = "email")
    @NotBlank
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Password must be at least 8 char long, to have one Uppercase, one symbol, one number! ")
    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @NotBlank(message = "Please insert your Phone Number!")
    @Column(name = "phone_number")
    private String phoneNumber;


    @Valid
    @OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "petOwner", targetEntity = WalkingOrder.class, cascade = CascadeType.ALL)
    private List<WalkingOrder> walkingOrders;

    @NotNull(message = "Please select your Profile type!")
    @Enumerated(EnumType.STRING)
    @Column(name = "profile_type", length = 10)
    private ProfileType profileType;

    @OneToMany(mappedBy = "profile", targetEntity = Feedback.class, cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, targetEntity = Reply.class)
    private List<Reply> reply;

    public Profile(int id) {
        this.id = id;
    }

    public Profile(Address address) {
        this.address = address;
    }

    public Profile(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Profile() {
    }

    public Profile(String email) {
        this.email = email;
    }


}
