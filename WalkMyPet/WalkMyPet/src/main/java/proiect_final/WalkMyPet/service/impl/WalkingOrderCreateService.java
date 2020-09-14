package proiect_final.WalkMyPet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proiect_final.WalkMyPet.domain.OrderStatus;
import proiect_final.WalkMyPet.domain.Payment;
import proiect_final.WalkMyPet.domain.WalkingOrder;
import proiect_final.WalkMyPet.repository.WalkingOrderCreateRepository;
import proiect_final.WalkMyPet.service.helper.Helper;
import proiect_final.WalkMyPet.service.mail.EmailService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WalkingOrderCreateService {

    @Autowired
    private WalkingOrderCreateRepository walkingOrderCreateRepository;

    @Autowired
    private EmailService emailService;


    public void addWalkingOrder(WalkingOrder walkingOrder) {
        Helper helper = new Helper();
        Payment payment = new Payment();
        helper.setOrderStatusOpen(walkingOrder);
        walkingOrder.setPeriod(helper.calculateWalkingPeriod(walkingOrder.getStartTime(), walkingOrder.getEndTime()));
        payment.setPaymentAmount(helper.calculatePaymentAmount(payment, walkingOrder));
        walkingOrder.setPayment(payment);
        walkingOrderCreateRepository.save(walkingOrder);
    }

    public List<WalkingOrder> viewPetOwnerWalkingOrders(int profileId) {
        List<WalkingOrder> walkingOrders = new ArrayList<>();
        walkingOrderCreateRepository.findByPetOwnerId(profileId)
                .forEach(walkingOrder -> walkingOrders.add(walkingOrder));
        return walkingOrders;
    }

    public List<WalkingOrder> viewProviderWalkingOrders(int profileId) {
        List<WalkingOrder> walkingOrders = new ArrayList<>();
        walkingOrderCreateRepository.findByProviderId(profileId)
                .forEach(walkingOrder -> walkingOrders.add(walkingOrder));
        return walkingOrders;
    }

    public Optional<WalkingOrder> findById(int id) {
        return walkingOrderCreateRepository.findById(id);
    }


    public void saveWalkingOrder(WalkingOrder walkingOrder, int id) {
        Helper helper = new Helper();
        Payment payment = new Payment();
        helper.setOrderStatusOpen(walkingOrder);
        walkingOrder.setPeriod(helper.calculateWalkingPeriod(walkingOrder.getStartTime(), walkingOrder.getEndTime()));
        payment.setPaymentAmount(helper.calculatePaymentAmount(payment, walkingOrder));
        walkingOrder.setPayment(payment);
        walkingOrderCreateRepository.save(walkingOrder);
    }

    public void deleteWalkingOrder(WalkingOrder walkingOrder) {
        walkingOrderCreateRepository.delete(walkingOrder);
    }

    public List<WalkingOrder> viewOpenOrders(OrderStatus orderStatus){
        String status = "OPEN";
        List<WalkingOrder> walkingOrders = new ArrayList<>();
        walkingOrderCreateRepository.findByOrderStatus(OrderStatus.valueOf(status))
                .forEach(walkingOrder -> walkingOrders.add(walkingOrder));
        return walkingOrders;
    }


    public void cancelWalkingOrder(WalkingOrder walkingOrder, OrderStatus status) {
        walkingOrderCreateRepository.cancelWalkingOrder(walkingOrder.getId(), status);
    }
}
