package proiect_final.WalkMyPet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proiect_final.WalkMyPet.domain.Reply;
import proiect_final.WalkMyPet.repository.ReplyRepository;
import proiect_final.WalkMyPet.service.helper.Helper;

import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    Helper helper;

    public List<Reply> getAllReplies(int feedbackId) {
        return replyRepository.findByFeedbackId(feedbackId);
    }

    public void saveReply(Reply reply) {
        reply.setDateTime(helper.setCurrentDateTime());
        replyRepository.save(reply);
    }

    public Optional<Reply> findById(int id) {
        return replyRepository.findById(id);
    }

}
