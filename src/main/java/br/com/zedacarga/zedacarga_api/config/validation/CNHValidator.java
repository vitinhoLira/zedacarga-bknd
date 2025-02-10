package br.com.zedacarga.zedacarga_api.config.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNHValidator implements ConstraintValidator<CNH, String> {

    @Override
    public boolean isValid(String cnh, ConstraintValidatorContext context) {
        if (cnh == null || !cnh.matches("\\d{11}")) {
            return false; // Deve ter 11 dígitos numéricos
        }
        return validarDigitoVerificador(cnh);
    }

    private boolean validarDigitoVerificador(String cnh) {
        int soma = 0;
        int multiplicador = 9;

        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cnh.charAt(i)) * multiplicador;
            multiplicador--;
        }

        int primeiroDigito = soma % 11;
        primeiroDigito = (primeiroDigito >= 10) ? 0 : primeiroDigito;

        soma = 0;
        multiplicador = 1;

        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cnh.charAt(i)) * multiplicador;
            multiplicador++;
        }

        int segundoDigito = soma % 11;
        segundoDigito = (segundoDigito >= 10) ? 0 : segundoDigito;

        return primeiroDigito == Character.getNumericValue(cnh.charAt(9)) &&
                segundoDigito == Character.getNumericValue(cnh.charAt(10));
    }
}
