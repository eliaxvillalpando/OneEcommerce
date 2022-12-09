package com.iudc.front;

import com.iudc.Utility;
import com.iudc.category.CategoryService;
import com.iudc.common.entity.Category;
import com.iudc.setting.EmailSettingBag;
import com.iudc.setting.SettingService;
import java.io.*;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class FrontController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SettingService settingService;

    String ip;
    Date date;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    String strDate;
    File file = new File("requestClientes.txt");

    @GetMapping("")
    public String viewHomePage() throws IOException {

        getIPCliente();
        return "front/inicio";
    }

    @GetMapping("/productos")
    public String viewProductos(Model model) {
        List<Category> listCategories = categoryService.listNoChildrenCategories();
        model.addAttribute("listCategories", listCategories);

        return "index";
    }

    @GetMapping("/front")
    public String viewInicio() throws IOException {
        getIPCliente();
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
        String[] destinatarios = {"0201757@up.edu.mx"};
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
    private String verPaginaNosotros() {

        return "front/nosotros";

    }

    @GetMapping("/test")
    private String paginaTest() {

        return "front/test";

    }

    @GetMapping("/politica-privacidad")
    private String paginaPolitica() {

        return "front/politicaPrivacidad";

    }

    //Metodo controlador para obtener la Ip y escribirla en un txt
    public void getIPCliente() throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        FileWriter writer = new FileWriter(file, true);
        date = Calendar.getInstance().getTime();
        strDate = dateFormat.format(date);
        ip = request.getRemoteAddr();
        String contenido = "IP: " + ip + " Fecha: " + strDate + "\n";

        writer.write(contenido);
        writer.close();

    }

}
