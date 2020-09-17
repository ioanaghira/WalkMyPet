package proiect_final.WalkMyPet.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "order_status")
public enum OrderStatus {
    OPEN, BOOKED, FINISHED, CANCELLED;
}
