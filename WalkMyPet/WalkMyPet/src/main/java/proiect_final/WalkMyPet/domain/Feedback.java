package proiect_final.WalkMyPet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import proiect_final.WalkMyPet.service.helper.Helper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "content")
    @NotNull(message = "Please insert your feedback!")
    private String content;

    @Column(name = "datetime")
    private String dateTime;

    @OneToMany(mappedBy = "feedback", targetEntity = Reply.class, cascade = CascadeType.ALL)
    private List<Reply> reply;

    @ManyToOne(targetEntity = WalkingOrder.class)
    private WalkingOrder walkingOrder;

    @ManyToOne(targetEntity = Profile.class)
    private Profile profile;

    @Column(name="rating")
    private int rating;

    public Feedback(){}

    public Feedback(int id){
        this.id = id;
    }

}
