package proiect_final.WalkMyPet.domain;

        import lombok.AllArgsConstructor;
        import lombok.Getter;
        import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Feedback {
    private int id;
    private String content;
    private Profile petOwner;
    private Profile provider;
}
