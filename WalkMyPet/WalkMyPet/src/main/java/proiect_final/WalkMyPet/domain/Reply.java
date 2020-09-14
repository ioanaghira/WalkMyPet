package proiect_final.WalkMyPet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "datetime")
    private String dateTime;

    //@NotNull(message = "Please add your reply before saving!")
    @Column(name = "reply_content")
    private String replyContent;

    @ManyToOne(targetEntity = Feedback.class)
    private Feedback feedback;

    @ManyToOne(targetEntity = Profile.class)
    private Profile profile;

    public Reply(){}

}
