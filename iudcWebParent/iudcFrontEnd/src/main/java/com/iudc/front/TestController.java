package com.iudc.front;

import com.iudc.setting.SettingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    }

}
