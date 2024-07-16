package com.witcher.e_commerce.application.witcher.service;

import com.witcher.e_commerce.application.witcher.dao.VerificationTokenRepository;
import com.witcher.e_commerce.application.witcher.entity.User;
import com.witcher.e_commerce.application.witcher.entity.VerificationToken;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
public class EmailService {

    public final VerificationTokenService verificationTokenService;

    private final VerificationTokenRepository verificationTokenRepository;

    private final TemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;

    private final VerificationToken verificationToken;

    private final UserService userService;

    public Object sendHtmlMail;

    @Autowired
    public EmailService(VerificationTokenRepository verificationTokenRepository, TemplateEngine templateEngine, JavaMailSender javaMailSender, VerificationToken verificationToken, VerificationTokenService verificationTokenService, VerificationToken verificationToken1, UserService userService, Object sendHtmlMail) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.verificationTokenService = verificationTokenService;

        this.verificationToken = verificationToken1;
        this.userService = userService;
        this.sendHtmlMail = sendHtmlMail;
    }


    public void sendHtmlMail(User user) throws MessagingException{
        VerificationToken verificationToken=verificationTokenRepository.findByUser(user);
        //check if the user has a token
        if(verificationToken !=null){
            log.info("user verification token {}",verificationToken.getToken());
            String token= verificationToken.getToken();
            Context context= new Context();
            context.setVariable("title","Verify your email address");
            context.setVariable("link","http://localhost:8080/activation?token=" +token);

            //create an html template and pass the variables to it
            String body= templateEngine.process("verification",context);

            //snd verification email
            MimeMessage message= javaMailSender.createMimeMessage();
            MimeMessageHelper helper= new MimeMessageHelper(message,true);
            helper.setTo(user.getEmail());
            helper.setSubject("email verification");
            helper.setText(body,true);
            javaMailSender.send(message);

        }
    }

}
