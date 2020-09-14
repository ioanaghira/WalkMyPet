package proiect_final.WalkMyPet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import proiect_final.WalkMyPet.service.helper.customAnnotations.CompareTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

@CompareTime(message = "End time cannot be before Start time Or on another day!", start = "startTime", end = "endTime")
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "walking_order")
public class WalkingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Profile.class)
    private Profile petOwner;

    @ManyToOne(targetEntity = Profile.class)
    private Profile provider;

    @Enumerated(EnumType.STRING)
    @Column(name="order_status",length = 12)
    private OrderStatus orderStatus;

    @NotNull(message = "Please insert the order Start Time!")
    @Column(name="start_time", nullable = false, unique = true)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "Please insert the order End Time!")
    @Column(name = "end_time", nullable = false, unique = true)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @FutureOrPresent(message = "Selected date cannot be a past date!")
    @NotNull(message = "Please insert the order Date!")
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column(name ="observations")
    private String observations;

    @Column(name ="period")
    private double period;

    @OneToOne(targetEntity = Payment.class, cascade = {CascadeType.ALL})
    private Payment payment;

    @OneToMany(mappedBy = "walkingOrder", targetEntity = Feedback.class, cascade = CascadeType.ALL)
    private List<Feedback> feedBacks;


    public WalkingOrder() {
    }

    public WalkingOrder(String petOwnerFirstName, String petOwnerLastName) {
        this.petOwner = new Profile(petOwnerFirstName, petOwnerLastName);
    }

    public WalkingOrder(int id){
        this.id = id;
    }

//    public WalkingOrder(int petOwnerId) {
//        this.petOwner = new Profile(petOwnerId);
//    }

}

