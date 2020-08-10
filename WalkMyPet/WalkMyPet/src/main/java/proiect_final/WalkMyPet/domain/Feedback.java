package proiect_final.WalkMyPet.domain;

        import lombok.AllArgsConstructor;
        import lombok.Getter;
        import lombok.Setter;

        import javax.persistence.*;

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
    private String content;

    @ManyToOne(targetEntity = WalkingOrder.class)
    private WalkingOrder walkingOrder;

    //private Profile petOwner;
    //private Profile provider;
}
