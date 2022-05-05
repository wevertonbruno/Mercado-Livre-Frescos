package com.mercadolibre.grupo1.projetointegrador.anotations.validation.impl;

import com.mercadolibre.grupo1.projetointegrador.anotations.validation.CPF;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class CPFValidator implements ConstraintValidator<CPF, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return isCPF(s);
    }

    /**
     * @author: https://receitasdecodigo.com.br/java/classe-java-completa-para-gerar-e-validar-cpf-e-cnpj
     */
    public boolean isCPF(String RawCPF) {
        // Retorna false caso nn seja passado nenhum valor
        if(RawCPF == null) return false;

        List<String> invalidSequences = Arrays.asList("00000000000","11111111111","22222222222","33333333333","44444444444","55555555555","66666666666","77777777777","88888888888","99999999999");

        final String CPF = removeCaracteresEspeciais(RawCPF);

        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (invalidSequences.stream().anyMatch(sequence -> sequence.equals(CPF)) || (CPF.length() != 11))
            return false;

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return (true);
            else
                return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    private String removeCaracteresEspeciais(String doc) {
        if (doc.contains(".")) {
            doc = doc.replace(".", "");
        }
        if (doc.contains("-")) {
            doc = doc.replace("-", "");
        }
        if (doc.contains("/")) {
            doc = doc.replace("/", "");
        }
        return doc;
    }
}
