package proiect_final.WalkMyPet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proiect_final.WalkMyPet.domain.Feedback;

import java.util.List;

@Repository
public interface FeedBackRepository extends CrudRepository<Feedback, Integer> {
    public List<Feedback> findByWalkingOrderId(int walkingOrderId);
}
