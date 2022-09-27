package com.iudc.customer;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.iudc.Utility;
import com.iudc.common.entity.Customer;
import com.iudc.common.exception.CustomerNotFoundException;
import com.iudc.setting.EmailSettingBag;
import com.iudc.setting.SettingService;

@Controller
public class ForgotPasswordController {
	@Autowired private CustomerService customerService;
	@Autowired private SettingService settingService;
	
	@GetMapping("/forgot_password")
	public String showRequestForm() {
		return "customer/forgot_password_form";
	}
	
	@PostMapping("/forgot_password")
	public String processRequestForm(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
		try {
			String token = customerService.updateResetPasswordToken(email);
			String link = Utility.getSiteURL(request) + "/reset_password?token=" + token;
			sendEmail(link, email);
			
			model.addAttribute("message", "Se ha enviado un link para restablecer tu contraseña."
					+ " Por favor, revisa tu correo.");
		} catch (CustomerNotFoundException e) {
			model.addAttribute("error", e.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "No fue posible enviar el correo.");
		}
		
		return "customer/forgot_password_form";
	}
	
	private void sendEmail(String link, String email) 
			throws UnsupportedEncodingException, MessagingException {
		EmailSettingBag emailSettings = settingService.getEmailSettings();
		JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);
		
		String toAddress = email;
		String subject = "Hola... aqui encontrara el link para restablecer tu contraseña ";
		
		String content = "<p>Hola,</p>"
				+ "<p>Has solicitado restablecer tu contraseña. </p>"
				+ "Haz click en el link para cambiar tu contraseña:</p>"
				+ "<p><a href=\"" + link + "\">Cambiar mi contraseña</a></p>"
				+ "<br>"
				+ "<p>Ignora este email si recuerdas tu contraseña."
				+ "o no haz hecho la solicitud.</p>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
		helper.setTo(toAddress);
		helper.setSubject(subject);		
		
		helper.setText(content, true);
		mailSender.send(message);
	}
	
	@GetMapping("/reset_password")
	public String showResetForm(@Param("token") String token, Model model) {
		Customer customer = customerService.getByResetPasswordToken(token);
		if (customer != null) {
			model.addAttribute("token", token);
		} else {
			model.addAttribute("pageTitle", "Token Invalido");
			model.addAttribute("message", "Token Invalido");
			return "message";
		}
		
		return "customer/reset_password_form";
	}
	
	@PostMapping("/reset_password")
	public String processResetForm(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");
		
		try {
			customerService.updatePassword(token, password);
			
			model.addAttribute("pageTitle", "Restablecer contraseña");
			model.addAttribute("title", "Restablece tu contraseña.");
			model.addAttribute("message", "Has cambiado tu contraseña.");
			
		} catch (CustomerNotFoundException e) {
			model.addAttribute("pageTitle", "Token Invalido.");
			model.addAttribute("message", e.getMessage());
		}	

		return "message";		
	}
}
