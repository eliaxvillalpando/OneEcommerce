package com.iudc.cotizador;

import com.iudc.Utility;
import com.iudc.setting.EmailSettingBag;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import com.iudc.setting.SettingService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CotizadorController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SettingService settingService;

    @GetMapping("/cotizador")
    private String verCotizador() {

        return "front/cotizador";

    }

    @PostMapping("/datosCapturados")
    public String calcularPrecioDeVenta(
            @RequestParam(required = false, name = "tipoAplicacion") String tipoAplicacion,
            @RequestParam(required = false, name = "otrasAplicaciones") String otrasAplicaciones,
            @RequestParam(required = false, name = "tipoDiseno") String tipoDiseno,
            @RequestParam(required = false, name = "clienteDiseno") String clienteDiseno,
            @RequestParam(required = false, name = "tipoForma") String tipoForma,
            @RequestParam(required = false, name = "ancho") String ancho,
            @RequestParam(required = false, name = "largo") String largo,
            @RequestParam(required = false, name = "radio1") String radio1,
            @RequestParam(required = false, name = "radio2") String radio2,
            @RequestParam(required = false, name = "ladoCuadrado") String ladoCuadrado,
            @RequestParam(required = false, name = "anchoContinua") String anchoContinua,
            @RequestParam(required = false, name = "largoContinua") String largoContinua,
            @RequestParam(required = false, name = "diametroCirculo") String diametroCirculo,
            @RequestParam(required = false, name = "idPapel") String idPapel,
            @RequestParam(required = false, name = "coloresEtiqueta") String coloresEtiqueta,
            @RequestParam(required = false, name = "seleccionClienteUnColor") String seleccionClienteUnColor,
            @RequestParam(required = false, name = "tipoLaminado") String tipoLaminado,
            @RequestParam(required = false, name = "tipoSalidaEtiqueta") String tipoSalidaEtiqueta,
            @RequestParam(required = false, name = "presentacion") String presentacion,
            HttpServletRequest request,
            Model model) throws MessagingException, UnsupportedEncodingException {

        Double precioMillar = 245.43;
        Double minimoFabricacion = 25.0;

        if (tipoDiseno == "conDiseno") {
            coloresEtiqueta = null;
        }

        model.addAttribute("tipoAplicacion", tipoAplicacion);
        model.addAttribute("otrasAplicaciones", otrasAplicaciones);
        model.addAttribute("tipoDiseno", tipoDiseno);
        model.addAttribute("clienteDiseno", clienteDiseno);
        model.addAttribute("tipoForma", tipoForma);
        model.addAttribute("ancho", ancho);
        model.addAttribute("largo", largo);
        model.addAttribute("radio1", radio1);
        model.addAttribute("radio2", radio2);
        model.addAttribute("anchoContinua", anchoContinua);
        model.addAttribute("largoContinua", largoContinua);
        model.addAttribute("ladoCuadrado", ladoCuadrado);
        model.addAttribute("diametroCirculo", diametroCirculo);
        model.addAttribute("idPapel", idPapel);
        model.addAttribute("coloresEtiqueta", coloresEtiqueta);
        model.addAttribute("tipoLaminado", tipoLaminado);
        model.addAttribute("tipoSalidaEtiqueta", tipoSalidaEtiqueta);
        model.addAttribute("presentacion", presentacion);
        model.addAttribute("seleccionClienteUnColor", seleccionClienteUnColor);
        model.addAttribute("precioMillar", precioMillar);
        model.addAttribute("minimoFabricacion", minimoFabricacion);

        return "cotizador/etiquetaCotizada";

    }

    @PostMapping("/enviar-cotizacion")
    public String sendEmail(@RequestParam("recipient") String recipient, @RequestParam("thElements") String[] thElements, @RequestParam("tdElements") String[] tdElements) throws MessagingException, IOException {
        // Set up the email

        EmailSettingBag emailSettings = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);
        MimeMessage mail = mailSender.createMimeMessage();
        mail.setHeader("Content-Type", "text/html; charset=UTF-8");
        MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
        MimeBodyPart htmlPart = new MimeBodyPart();
        MimeMultipart multipart = new MimeMultipart("related");

        StringBuilder content = new StringBuilder();
        content.append("<table class='invoice-table'>");

        int tdIndex = 0;
        while (tdIndex < tdElements.length) {
            content.append("<tr>");
            content.append("<th>").append(thElements[tdIndex % thElements.length].replace("\"", " ")).append("</th>");
            content.append("<td>").append(tdElements[tdIndex].replace("\"", " ")).append("</td>");
            content.append("</tr>");
            tdIndex++;
        }

        content.append("</table>");

        MimeBodyPart imagePart = new MimeBodyPart();

        //htmlPart.setText(tableContent);
        String html = "<html><head> <meta charset='utf-8'>"
                + "<meta name='viewport' content='width=device-width, initial-scale=1, shrink-to-fit=no'>"
                + "<style> h2{color:#20B2E5}"
                + "th{font-weight: bold; color:#258DC2;}"
                + ".invoice-container {width: 80%;margin: auto;border: 1px solid black;padding: 20px;}"
                + ".invoice-footer {text-align: right;font-size: 16px;font-style: italic;} "
                + ".invoice-table th, .invoice-table td {border: 1px solid black;padding: 8px;text-align: left;}"
                + ".invoice-table {width: 100%;border-collapse: collapse;margin-bottom: 20px;}"
                + "img{text-align: center;margin-bottom: 20px; width: 200px; display: block; margin:0 auto;}"
                + ".invoice-title {text-align: center;font-size: 24px;font-weight: bold;margin-bottom: 20px;}</style>"
                + "</head><body><div class='invoice-container'>"
                + "<div class='invoice-title'> <h2 clas='text-center' style='text-align: center;'>Detalles de la cotización</h2></div> <div class='invoice-logo'><img src='cid:imageId' width='200px'></div></br>"
                + "<div>" + content + "</div>"
                + "<div class='invoice-footer'>Soluciones de etiquetado y codificado. <a href='https://www.iudc.com.mx'>Ir a la pagina</a></div>"
                + "</body></html>";

        htmlPart.setContent(html, "text/html; charset=UTF-8");

        File imageFile = new File("/Users/elias/Desktop/test3.png");
        imagePart.attachFile(imageFile);
        imagePart.setContentID("<imageId>");
        multipart.addBodyPart(htmlPart);
        multipart.addBodyPart(imagePart);

        helper.setSubject("Cotización Impresores Unidos del Centro");
        helper.setTo(recipient);

        mail.setContent(multipart);
        mailSender.send(mail);

        return "front/envioExitoso";

    }

    @RequestMapping("/cotizacion-enviada")
    public String getAjaxViewPage(HttpServletRequest request, Model model) {

        return "front/envioExitoso";

    }

}
