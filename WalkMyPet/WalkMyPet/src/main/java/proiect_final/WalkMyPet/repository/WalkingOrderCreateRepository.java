package proiect_final.WalkMyPet.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import proiect_final.WalkMyPet.domain.OrderStatus;
import proiect_final.WalkMyPet.domain.Payment;
import proiect_final.WalkMyPet.domain.Profile;
import proiect_final.WalkMyPet.domain.WalkingOrder;

import java.util.List;

@Repository
public interface WalkingOrderCreateRepository extends CrudRepository<WalkingOrder, Integer> {

    public List<WalkingOrder> findByPetOwnerId(int petOwnerId);

    public List<WalkingOrder> findByProviderId(int providerId);

    public List<WalkingOrder> findByOrderStatus(OrderStatus status);

    @Transactional
    @Modifying
    @Query("update WalkingOrder w set w.orderStatus = :status where w.id = :id")
    void cancelWalkingOrder(@Param(value = "id") int id, @Param(value = "status") OrderStatus status);

    @Transactional
    @Modifying
    @Query("update WalkingOrder w set w.orderStatus = :status, w.provider = :profile where w.id = :id")
    void confirmWalkingOrder(@Param(value = "id") int id, @Param(value = "status") OrderStatus status,
                             @Param(value = "profile") Profile profile);

    @Transactional
    @Modifying
    @Query("update WalkingOrder w set w.orderStatus = :status where w.id = :id")
    void completeWalkingOrder(@Param(value = "id") int id, @Param(value = "status") OrderStatus status);

}
