package com.gheowinapps.sistemas_numericos;

/**
 *
 * @author Geovani Lopes Dias
 */
public class BaseConversion {
    
    // Numerical tests:
    private static boolean isPurelyNumerical(String value){
        return value.matches("\\d+");
    }
    
    
    private static boolean isDecimalInputInByteRange(String textualValue) throws RuntimeException{
        testNumericalInput(textualValue);
        int value = Integer.parseInt(textualValue);
        return value >= 0 && value <= 127;
    }
    
    
    private static void testNumericalInput(String value){
        if(!isPurelyNumerical(value)){throw new RuntimeException("The input value isn't numerical.");}
    }
    
    private static void testByteRange(String userValue, int neededBase){
        if(neededBase == 2 && !isDecimalInputInByteRange(userValue)){
            throw new RuntimeException("Apenas valores entre 0 e 127 são suportados bara conversão.");
        }
    }
    
    private static boolean analyzeBinaryOrOctalInput(String textualValue, int userBase){
        testNumericalInput(textualValue);
        boolean userInputBaseIsSupported = (userBase == 2 || userBase == 8 || userBase == 16);
        boolean userValueIsInteger = (int) Double.parseDouble(textualValue) == Integer.parseInt(textualValue);
        
        if(!userInputBaseIsSupported) {throw new RuntimeException("Apenas base 2, 8 e 16 são suportadas.");}
        if(!userValueIsInteger) {throw new RuntimeException("Apenas valores inteiros são permitidos.");}
        return userValueIsInteger && userInputBaseIsSupported;
    }
    
    // 
    private static int countDigits(String textualValue){
        testNumericalInput(textualValue);
        Double numericalValue = Double.valueOf(textualValue);
        return (int) Math.log10(numericalValue)+1;
    }
    
    
    private static int[] toIntArray(String textualValue){
        testNumericalInput(textualValue);
        int[] digits = new int[countDigits(textualValue)];
        for(int i = 0; i < countDigits(textualValue); i++)
            digits[i] = Character.getNumericValue(textualValue.toCharArray()[i]);
        return digits;
    }
    
    // ---------- Hexadecimal digits converters:
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
    
    
    private static char convertNumberToHexadecimalLetter(int value){
        if(value < 0 || value > 15){
            throw new RuntimeException("O valor inserido precisa estar entre 0 e 15.");
        }
        
        else{
            for (char hexLetter = '0'; hexLetter <= '9'; hexLetter++){
                if(value == Character.getNumericValue(hexLetter)){return hexLetter;}}
            switch(value){
                case 10: return 'A';
                case 11: return 'B';
                case 12: return 'C';
                case 13: return 'D';
                case 14: return 'E';
                default: return 'F';
            }
        }
    }

    
    private static boolean isDigitHexadecimalLetter(char letter){
        for (char hexLetter = 'A'; hexLetter <= 'F'; hexLetter++)
            if(letter == hexLetter){return true;}
        for (char hexLetter = 'a'; hexLetter <= 'b'; hexLetter++)
            if(letter == hexLetter){return true;}
        return false;
    }
    
    
    // ---------- Specific conversion methods:
    private static int convertBinaryOrOctalToDecimal(String userValue, int base) throws RuntimeException{
        int convertedValue = 0;
        boolean inputIsOK = analyzeBinaryOrOctalInput(userValue, base);
        if(inputIsOK){
            int[] digitosDoValor = toIntArray(userValue);
            for (int i = 0; i < digitosDoValor.length; i++){
                convertedValue += (int) digitosDoValor[i] * Math.pow(base, digitosDoValor.length-(1+i));
            }
        }
        if(convertedValue == 0){throw new RuntimeException();}
        return convertedValue;
    }
    
    
    private static String convertDecimalToBinaryOrOctal(String userValue, int neededBase)throws RuntimeException{
        testNumericalInput(userValue);
        testByteRange(userValue, neededBase);
        
        int currentQuotient = Integer.parseInt(userValue);
        StringBuilder valueConvertedButInverted = new StringBuilder(); 
        do{
            valueConvertedButInverted.append(currentQuotient % neededBase);
            currentQuotient /= neededBase;
        } while (currentQuotient >= neededBase);
        valueConvertedButInverted.append(currentQuotient % neededBase); // Last value
        return valueConvertedButInverted
                .reverse()
                .toString();
    }
    
    
    private static int convertHexadecimalToDecimal(String userValue, int base) throws RuntimeException{
        int convertedValue = 0, auxiliaryDigit;
        char[] digits = userValue.toCharArray();

        for (int i = 0; i < digits.length; i++){
            if(!isDigitHexadecimalLetter(digits[i])) {auxiliaryDigit = Character.getNumericValue(digits[i]);}
            else {auxiliaryDigit = convertHexadecimalLetterToNumber(digits[i]);}
            convertedValue += (int) auxiliaryDigit * Math.pow(base, digits.length-(1+i));
        }
        if(convertedValue == 0){throw new RuntimeException();}
        return convertedValue;
    }
    
    
    private static String convertDecimalToHexadecimal(String userValue) throws RuntimeException{
        testNumericalInput(userValue);
        
        int currentQuotient = Integer.parseInt(userValue), currentRemainder;
        StringBuilder valueConvertedButInverted = new StringBuilder();
        char nextDigit;
        do{
            currentRemainder = currentQuotient % 16;
            nextDigit = convertNumberToHexadecimalLetter(currentRemainder);
            valueConvertedButInverted.append(nextDigit);
            currentQuotient /= 16;
        } while (currentQuotient >= 16);

        // Last digit insertion:
        currentRemainder = currentQuotient % 16;
        nextDigit = convertNumberToHexadecimalLetter(currentRemainder);
        valueConvertedButInverted.append(nextDigit);
        return valueConvertedButInverted.reverse().toString();
    }
    
    // ----------- General conversion functions:
    public static int convertNonDecimalToDecimal(String value, int base) throws RuntimeException{
        try{
            int convertedValue = 0;
            switch(base){
                case 2, 8 -> convertedValue = convertBinaryOrOctalToDecimal(value, base);
                case 16 -> convertedValue = convertHexadecimalToDecimal(value, base);
            }
            return convertedValue;
        }
        catch(RuntimeException e) {e.printStackTrace();}
        return -1;
    }
    
    
    public static String convertDecimalToNonDecimal(String value, int base) throws RuntimeException{
        try{
            String convertedValue;
            switch(base){
                case 2, 8 -> convertedValue = convertDecimalToBinaryOrOctal(value, base);
                case 16 -> convertedValue = convertDecimalToHexadecimal(value);
                default -> convertedValue = null;
            }
            return convertedValue;
        }
        catch(RuntimeException e) {e.printStackTrace();}
        return null;
    }
    
    
}
