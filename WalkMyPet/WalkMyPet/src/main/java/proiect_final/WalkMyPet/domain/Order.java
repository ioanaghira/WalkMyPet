package proiect_final.WalkMyPet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Order {
    private int id;
    private Profile provider;
    private Profile petOwner;
    private LocalTime startTime;
    private LocalTime endTime;
    private OffsetDateTime dateTime;
    private OrderStatus orderStatus;
    private double period;
    private Payment payment;
}
