package proiect_final.WalkMyPet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import proiect_final.WalkMyPet.domain.Reply;

import java.util.List;

@Repository
public interface ReplyRepository extends CrudRepository<Reply, Integer> {
    public List<Reply> findByFeedbackId(int feedbackId);

}
