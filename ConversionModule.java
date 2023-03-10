package com.gheowinapps.sistemas_numericos;

import java.util.Scanner;

/**
 *
 * @author Geovane
 */
public class ConversionModule {
    private static boolean analyzeInputForConversion(String userValue, int userBase){
        boolean userValueIsInteger = (int) Double.parseDouble(userValue) == Integer.parseInt(userValue);
        boolean userInputBaseIsSupported = (userBase == 2 || userBase == 8 || userBase == 16);
        if(!userValueIsInteger) {throw new RuntimeException("Apenas valores inteiros são permitidos.");}
        if(!userInputBaseIsSupported) {throw new RuntimeException("Apenas base 2, 8 e 16 são suportadas.");}
        return userValueIsInteger && userInputBaseIsSupported;
    }
    
    private static int countDigits(String value){
        Double numericalValue = Double.valueOf(value);
        return (int) Math.log10(numericalValue)+1;
    }
    
    private static int[] groupPureNumericalDigits(String value) throws RuntimeException{
        int[] digits = new int[countDigits(value)];
        for(int i = 0; i < countDigits(value); i++){
            digits[i] = Character.getNumericValue(value.toCharArray()[i]);
        }
        return digits;
    }
    
    private static int convertBinaryOrOctalToDecimal(String value, int base) throws RuntimeException{
        boolean inputIsOK = analyzeInputForConversion(value, base);
        int convertedValue = 0;
        if(inputIsOK){
            int[] digitosDoValor = groupPureNumericalDigits(value);
            for (int i = 0; i < digitosDoValor.length; i++){
                convertedValue += (int) digitosDoValor[i] * Math.pow(base, digitosDoValor.length-(1+i));
            }
        }
        if(convertedValue == 0){throw new RuntimeException();}
        return convertedValue;
    }
    
    private static int convertHexadecimalLetterToNumber(char letter) throws RuntimeException{
        switch(letter){
            case 'A', 'a': return 10;
            case 'B', 'b': return 11;
            case 'C', 'c': return 12;
            case 'D', 'd': return 13;
            case 'E', 'e': return 14;
            case 'F', 'f': return 15;
            default: throw new RuntimeException("Letra inválida de valor hexadecimal.");
        }
    }
    
    private static boolean isDigitAHexadecimalLetter(char letter){
        return letter == 'A' || letter == 'B' || letter == 'C' || letter == 'D' || letter == 'E' || letter == 'F' ||
               letter == 'a' || letter == 'b' || letter == 'c' || letter == 'd' || letter == 'e' || letter == 'f';
    }
    
    private static int convertHexadecimalToDecimal(String valorDoUsuario, int base) throws RuntimeException{
        int convertedValue = 0, auxiliaryDigit;
        char[] digits = valorDoUsuario.toCharArray();

        for (int i = 0; i < digits.length; i++){
            if(isDigitAHexadecimalLetter(digits[i])){
                auxiliaryDigit = convertHexadecimalLetterToNumber(digits[i]);}
            else {
                auxiliaryDigit = Character.getNumericValue(digits[i]);}
            convertedValue += (int) auxiliaryDigit * Math.pow(base, digits.length-(1+i));

        }
        if(convertedValue == 0){throw new RuntimeException();}
        return convertedValue;
    }
    
    private static int convertNonDecimalToDecimal(String value, int base) throws RuntimeException{
        int convertedValue = 0;
        switch(base){
            case 2, 8 -> convertedValue = convertBinaryOrOctalToDecimal(value, base);
            case 16 -> convertedValue = convertHexadecimalToDecimal(value, base);
        }
        return convertedValue;
    }
    
    public static void openConversionFromNonDecimalToDecimalScreen() throws RuntimeException{
        Scanner s = new Scanner(System.in);
        String base, value;
        System.out.println("=== Estudo de sistemas de base não-decimal ===");
            System.out.print("\tInsira a base do valor: ");
            base = s.nextLine();
            System.out.print("\tInsira o valor conforme a base informada: ");
            value = s.nextLine();
            int convertedValue = convertNonDecimalToDecimal(value, Integer.parseInt(base));
            System.out.print("Seu valor convertido para a base "+base+" é: "+convertedValue);
        
    }
}
