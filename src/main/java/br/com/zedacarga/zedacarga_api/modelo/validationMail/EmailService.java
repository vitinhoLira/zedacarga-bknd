package br.com.zedacarga.zedacarga_api.modelo.validationMail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender emailSender;

  public void sendVerificationEmail(String toEmail, String code) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(" zedacargaltda@gmail.com");
    message.setTo(toEmail);
    message.setSubject("Código de Verificação");
    message.setText("Seu código de verificação é: " + code);

    emailSender.send(message);
  }
}
