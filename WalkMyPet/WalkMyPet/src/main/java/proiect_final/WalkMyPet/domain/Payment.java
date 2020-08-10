package proiect_final.WalkMyPet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name ="fee")
    private double fee;

    @Column(name="payment_amount")
    private double paymentAmount;

    @OneToOne(mappedBy = "payment", targetEntity = WalkingOrder.class)
    private WalkingOrder walkingOrder;

    public Payment(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Payment() {
        fee = 5;
    }


    @Override
    public String toString() {
        return String.valueOf(paymentAmount);
    }
}
