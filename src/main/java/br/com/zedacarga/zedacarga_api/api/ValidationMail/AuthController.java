package br.com.zedacarga.zedacarga_api.api.ValidationMail;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.zedacarga.zedacarga_api.modelo.validationMail.EmailService;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final EmailService emailService;
  private final Map<String, String> verificationCodes = new HashMap<>();

  public AuthController(EmailService emailService) {
    this.emailService = emailService;
  }

  @Operation(summary = "Envio de código de verificação", description = "Envia um código de verificação de 6 dígitos para o e-mail informado.")
  @PostMapping("/send-code")
  public ResponseEntity<Map<String, String>> sendVerificationCode(@RequestParam String email) {
    String code = String.valueOf(new Random().nextInt(900000) + 100000); // Código de 6 dígitos
    verificationCodes.put(email, code);
    emailService.sendVerificationEmail(email, code);

    Map<String, String> response = new HashMap<>();
    response.put("message", "Código de verificação enviado!");

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Verificação de código", description = "Verifica se o código informado corresponde ao código enviado para o e-mail.")
  @PostMapping("/verify-code")
  public ResponseEntity<Map<String, String>> verifyCode(@RequestParam String email, @RequestParam String code) {
    Map<String, String> response = new HashMap<>();

    if (verificationCodes.containsKey(email) && verificationCodes.get(email).equals(code)) {
      verificationCodes.remove(email);
      response.put("message", "E-mail verificado com sucesso!");
      return ResponseEntity.ok(response);
    }

    response.put("message", "Código inválido!");
    return ResponseEntity.badRequest().body(response);
  }
}
