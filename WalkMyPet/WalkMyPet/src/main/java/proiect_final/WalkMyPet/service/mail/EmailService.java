package proiect_final.WalkMyPet.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("walkmypet@email.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.getMessage();
            exception.printStackTrace();
        }
    }

    public void sendMailCreateOrder(String subject, String to, String name, int oId,
                                     String oDate, String start, String end,
                                     String oStatus, Double oCost){

        // Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("name", name);
        ctx.setVariable("orderNr", oId);
        ctx.setVariable("orderDate", oDate);
        ctx.setVariable("orderStart", start );
        ctx.setVariable("orderEnd", end);
        ctx.setVariable("orderStatus", oStatus);
        ctx.setVariable("orderCost", oCost);
        ctx.setVariable("location", "Cluj-Napoca");

        try {
        // Prepare message using a Spring helper
        MimeMessage mimeMessage = this.emailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject(subject);
        message.setFrom("walkmypet2020@gmail.com");
        message.setTo(to);

        String htmlContent = this.templateEngine.process("createOrderEmail", ctx);
        message.setText(htmlContent, true);

        // Send email
        this.emailSender.send(mimeMessage);

        } catch (MailException | MessagingException exception) {
            exception.getMessage();
            exception.printStackTrace();
        }
    }

    public void sendMailCancelledOrder(String subject, String to, int oId){

        // Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("orderNr", oId);
        ctx.setVariable("location", "Cluj-Napoca");

        try {
            // Prepare message using a Spring helper
            MimeMessage mimeMessage = this.emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject(subject);
            message.setFrom("walkmypet2020@gmail.com");
            message.setTo(to);

            String htmlContent = this.templateEngine.process("cancelledOrderEmail", ctx);
            message.setText(htmlContent, true);

            // Send email
            this.emailSender.send(mimeMessage);

        } catch (MailException | MessagingException exception) {
            exception.getMessage();
            exception.printStackTrace();
        }
    }



    public void sendMailConfirmOrder(String subject, String to, int oId){

        final Context ctx = new Context();
        ctx.setVariable("orderNr", oId);
        ctx.setVariable("location", "Cluj-Napoca");

        try {
            MimeMessage mimeMessage = this.emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject(subject);
            message.setFrom("walkmypet2020@gmail.com");
            message.setTo(to);

            String htmlContent = this.templateEngine.process("confirmOrderEmail", ctx);
            message.setText(htmlContent, true);

            // Send email
            this.emailSender.send(mimeMessage);

        }  catch (MailException | MessagingException exception) {
            exception.getMessage();
            exception.printStackTrace();
        }

    }

    public void sendMailCompleteOrder(String subject, String to, String name, int oId,
                                      String oDate, String start, String end,
                                      String oStatus, Double oCost){

        final Context ctx = new Context();
        ctx.setVariable("name", name);
        ctx.setVariable("orderNr", oId);
        ctx.setVariable("orderDate", oDate);
        ctx.setVariable("orderStart", start );
        ctx.setVariable("orderEnd", end);
        ctx.setVariable("orderStatus", oStatus);
        ctx.setVariable("orderCost", oCost);
        ctx.setVariable("location", "Cluj-Napoca");

        try {
            MimeMessage mimeMessage = this.emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject(subject);
            message.setFrom("walkmypet2020@gmail.com");
            message.setTo(to);

            String htmlContent = this.templateEngine.process("completeWalkingOrder", ctx);
            message.setText(htmlContent, true);

            // Send email
            this.emailSender.send(mimeMessage);

        }  catch (MailException | MessagingException exception) {
            exception.getMessage();
            exception.printStackTrace();
        }

    }






    public void sendMailOnFeedBack(String subject, String to, String titleName, String fbName, int woId,
                                   String content, String dateTime){

        // Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("titleName", titleName);
        ctx.setVariable("fbName", fbName);
        ctx.setVariable("woId", woId);
        ctx.setVariable("to", to);
        ctx.setVariable("subject", subject );
        ctx.setVariable("content", content);
        ctx.setVariable("dateTime", dateTime);
        ctx.setVariable("location", "Cluj-Napoca");

        try {
        // Prepare message using a Spring helper
        MimeMessage mimeMessage = this.emailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject(subject);
        message.setFrom("walkmypet2020@gmail.com");
        message.setTo(to);

        String htmlContent = this.templateEngine.process("createFeedbackEmail", ctx);
        message.setText(htmlContent, true);

        // Send email
        this.emailSender.send(mimeMessage);

        } catch (MailException | MessagingException exception) {
            exception.printStackTrace();
            exception.getMessage();
        }
    }

    public void sendMailOnReply(String subject, String to, String titleName, String fbName, String rpName, int woId,
                                   String content, String rpContent, String rpDateTime, String dateTime){

        // Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("titleName", titleName);
        ctx.setVariable("fbName", fbName);
        ctx.setVariable("rpName", rpName);
        ctx.setVariable("woId", woId);
        ctx.setVariable("to", to);
        ctx.setVariable("subject", subject );
        ctx.setVariable("content", content);
        ctx.setVariable("rpContent", rpContent);
        ctx.setVariable("rpDateTime", rpDateTime);
        ctx.setVariable("dateTime", dateTime);
        ctx.setVariable("location", "Cluj-Napoca");

        try {
            // Prepare message using a Spring helper
            MimeMessage mimeMessage = this.emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject(subject);
            message.setFrom("walkmypet2020@gmail.com");
            message.setTo(to);

            String htmlContent = this.templateEngine.process("createReplyEmail", ctx);
            message.setText(htmlContent, true);

            // Send email
            this.emailSender.send(mimeMessage);

        } catch (MailException | MessagingException exception) {
            exception.printStackTrace();
        }
    }

    public void sendMailOnRegister(String subject, String to, String name){

        // Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("name", name);
        ctx.setVariable("to", to);
        ctx.setVariable("subject", subject );
        ctx.setVariable("location", "Cluj-Napoca");

        try {
            // Prepare message using a Spring helper
            MimeMessage mimeMessage = this.emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject(subject);
            message.setFrom("walkmypet2020@gmail.com");
            message.setTo(to);

            String htmlContent = this.templateEngine.process("createRegisterEmail", ctx);
            message.setText(htmlContent, true);

            // Send email
            this.emailSender.send(mimeMessage);

        } catch (MailException | MessagingException exception) {
            exception.printStackTrace();
        }
    }
}
