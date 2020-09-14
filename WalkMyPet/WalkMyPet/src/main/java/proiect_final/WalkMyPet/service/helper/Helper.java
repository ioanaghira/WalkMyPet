package proiect_final.WalkMyPet.service.helper;

import org.springframework.stereotype.Component;
import proiect_final.WalkMyPet.domain.*;
import proiect_final.WalkMyPet.repository.ReplyRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Component
public class Helper {

    public Helper() {
    }

    private int testId;

    private ReplyRepository replyRepository;

    public void setOrderStatusOpen(WalkingOrder walkingOrder) {
        walkingOrder.setOrderStatus(OrderStatus.OPEN);
    }

    public double calculateWalkingPeriod(LocalTime startTime, LocalTime endTime) {
        double period = java.time.Duration.between(startTime, endTime).toMinutes();
        return period;
    }

    public double calculatePaymentAmount(Payment payment, WalkingOrder walkingOrder) {
        return (walkingOrder.getPeriod() / 10) * payment.getFee();
    }

    public String setCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }


    public String setFbEmailReceiverName(WalkingOrder wo, Feedback fb) {
        String name;
        if (wo.getProvider() != null) {
            name = wo.getProvider().getFirstName() + " " + wo.getProvider().getLastName();
        } else {
            name = "WalkMyPet";
        }
        return name;
    }

    public String getFbReceiverEmailAddress(WalkingOrder wo, Feedback fb) {
        String email;
            if (wo.getProvider() != null) {
                email = wo.getProvider().getEmail();
            } else {
                email = "walkmypet2020@gmail.com";
            }
        return email;
    }

    public String setRpEmailReceiverName(WalkingOrder wo, Reply rp) {
        String name;
        if (wo.getPetOwner().getFirstName().equals(rp.getProfile().getFirstName())
                && wo.getPetOwner().getLastName().equals(rp.getProfile().getLastName())) {
            if (wo.getProvider() != null) {
                name = wo.getProvider().getFirstName() + " " + wo.getProvider().getLastName();
            } else {
                name = "WalkMyPet";
            }
        } else {
            name = wo.getPetOwner().getFirstName() + " " + wo.getPetOwner().getLastName();
        }
        return name;
    }

    public String getRpReceiverEmailAddress(WalkingOrder wo, Reply rp) {
        String email;
        if (wo.getPetOwner().getFirstName().equals(rp.getProfile().getFirstName())
                && wo.getPetOwner().getLastName().equals(rp.getProfile().getLastName())) {
            if (wo.getProvider() != null) {
                email = wo.getProvider().getEmail();
            } else {
                email = "walkmypet2020@gmail.com";
            }
        } else {
            email = wo.getPetOwner().getEmail();
        }
        return email;
    }

    public double calcRatingAverage(List<Feedback> feedbacks) {
        double average = 0;
        for (int i = 0; i <= feedbacks.size(); i++) {
            average = (Double) (average + feedbacks.get(i).getRating()) / feedbacks.size();
        }
        return average;
    }
}
