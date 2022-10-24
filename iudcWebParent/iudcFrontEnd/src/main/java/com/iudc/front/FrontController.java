package com.iudc.front;

import com.iudc.Utility;
import com.iudc.category.CategoryService;
import com.iudc.common.entity.Category;
import com.iudc.setting.EmailSettingBag;
import com.iudc.setting.SettingService;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Controller
public class FrontController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SettingService settingService;

    @GetMapping("")
    public String viewHomePage(Model model) {
        List<Category> listCategories = categoryService.listNoChildrenCategories();
        model.addAttribute("listCategories", listCategories);

        return "index";
    }

    @GetMapping("/front")
    public String viewInicio() {

        return "front/inicio";
    }

    @GetMapping("/bolsa")
    public String viewBolsa() {
        return "front/bolsaTrabajo";
    }

    @PostMapping("/solInfo")
    private String solicitarInformacion(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {

        EmailSettingBag emailSettings = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);
        String email = request.getParameter("email");
        String nombreContacto = request.getParameter("nombreContacto");
        String telefonoContacto = request.getParameter("telefonoContacto");
        String content = request.getParameter("mensajeContacto");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
        helper.setTo("el.sistemas@iudc.com.mx");
        String[] destinatarios = {"el.sistemas@iudc.com.mx", "eliasvillalpando01@gmail.com", "0201757@up.edu.mx"};
        helper.setTo(destinatarios);
        String mailSubject = "Alguien solicita información en la pagina \n\n";
        String mailContent = nombreContacto + " ha preguntado"
                + content + "\n\n ponte en contacto a su telefono: \n\n"
                + telefonoContacto + " o correo: " + email;
        helper.setSubject(mailSubject);
        helper.setText(mailContent, true);
        mailSender.send(message);

        return "front/envioExitoso";

    }
    
    @GetMapping("/nosotros")
    private String verPaginaNosotros(){

        return "front/nosotros";


    }
    
     @GetMapping("/test")
    private String paginaTest(){

        return "front/test";


    }
    
       @GetMapping("/politica-privacidad")
    private String paginaPolitica(){

        return "front/politicaPrivacidad";


    }

}
