package com.iudc.front;

import com.iudc.Utility;
import com.iudc.setting.EmailSettingBag;
import com.iudc.setting.SettingService;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.io.File;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.xslf.usermodel.*;

@Controller
public class TestController {

    @Autowired
    private SettingService settingService;

    @GetMapping("/test")
    private String testPage() {

        return "front/test";

    }

    @PostMapping("/send-email-test")
    public void sendEmail(@RequestParam("recipient") String recipient,
            @RequestParam("subject") String subject, @RequestParam("content") String emailData,
            @RequestParam("tableContent") String tableContent)
            throws MessagingException, FileNotFoundException, IOException {


        // Set up the email
        /*
        EmailSettingBag emailSettings = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);
        
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(recipient);
        helper.setText(emailData);
        helper.setText(tableContent);
        helper.setSubject("Cotización Impresores Unidos del Centro" + subject);
        mailSender.send(mail);*/
    }

}
