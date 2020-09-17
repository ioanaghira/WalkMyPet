package proiect_final.WalkMyPet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proiect_final.WalkMyPet.domain.Feedback;
import proiect_final.WalkMyPet.repository.FeedBackRepository;
import proiect_final.WalkMyPet.service.helper.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedBackService {

    @Autowired
    private FeedBackRepository feedBackRepository;
    @Autowired
    private Helper helper;

    public List<Feedback> getAllOrderFeedbacks(int walkingOrderId) {
        List<Feedback> feedbacks = new ArrayList<>();
        feedBackRepository.findByWalkingOrderId(walkingOrderId).forEach(feedback -> feedbacks.add(feedback));
        return feedbacks;
    }

    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbacks = new ArrayList<>();
        feedBackRepository.findAll().forEach(feedback -> feedbacks.add(feedback));
        return feedbacks;
    }

    public void saveFeedBack(Feedback feedback) {
        feedback.setDateTime(helper.setCurrentDateTime());
        feedBackRepository.save(feedback);
    }

    public Optional<Feedback> findById(int id) {
        return feedBackRepository.findById(id);
    }

}
