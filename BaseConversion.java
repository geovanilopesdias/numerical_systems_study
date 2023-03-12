package com.gheowinapps.sistemas_numericos;

/**
 *
 * @author Geovani Lopes Dias
 */
public class BaseConversion {
    private static boolean isPurelyNumerical(String value){
        boolean result = value.matches("\\d+");
        if(result){return true;}
        else{throw new RuntimeException("Input isn't purely numerical.");}
    }
    
    private static boolean analyzeBinaryOrOctalInput(String value, int userBase){
        boolean userInputBaseIsSupported = (userBase == 2 || userBase == 8 || userBase == 16);
        boolean userValueIsInteger = (int) Double.parseDouble(value) == Integer.parseInt(value);
        
        if(!userInputBaseIsSupported) {throw new RuntimeException("Apenas base 2, 8 e 16 são suportadas.");}
        if(!userValueIsInteger) {throw new RuntimeException("Apenas valores inteiros são permitidos.");}
        return userValueIsInteger && userInputBaseIsSupported;
    }
    
    private static boolean isDecimalInputInByteRange(String textualValue) throws RuntimeException{
        if(isPurelyNumerical(textualValue)){
            int value = Integer.parseInt(textualValue);
            return value >= 0 && value <= 127;
        }
        return false;
    }
    
    private static int countDigits(String value){
        Double numericalValue = Double.valueOf(value);
        return (int) Math.log10(numericalValue)+1;
    }
    
    
    private static int[] toIntArray(String value){
        if(!isPurelyNumerical(value)){
            throw new RuntimeException("Input isn't purely numerical.");
        }
        
        int[] digits = new int[countDigits(value)];
        for(int i = 0; i < countDigits(value); i++){
            digits[i] = Character.getNumericValue(value.toCharArray()[i]);
        }
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
    private static int convertBinaryOrOctalToDecimal(String value, int base) throws RuntimeException{
        int convertedValue = 0;
        boolean inputIsOK = analyzeBinaryOrOctalInput(value, base);
        if(inputIsOK){
            int[] digitosDoValor = toIntArray(value);
            for (int i = 0; i < digitosDoValor.length; i++){
                convertedValue += (int) digitosDoValor[i] * Math.pow(base, digitosDoValor.length-(1+i));
            }
        }
        if(convertedValue == 0){throw new RuntimeException();}
        return convertedValue;
    }
    
    
    private static String convertDecimalToBinaryOrOctal(String userValue, int neededBase)throws RuntimeException{
        if(!isPurelyNumerical(userValue)){
            throw new RuntimeException("O valor inserido não é puramente numérico.");
        }
        
        if(neededBase == 2 && !isDecimalInputInByteRange(userValue)){
            throw new RuntimeException("Apenas valores entre 0 e 127 são suportados bara conversão.");
        }
        
        else {
            int currentQuotient = Integer.parseInt(userValue);
            StringBuilder valueConvertedButInverted = new StringBuilder(); 
            do{
                valueConvertedButInverted.append(currentQuotient % neededBase);
                currentQuotient /= neededBase;
            } while (currentQuotient >= neededBase);
            valueConvertedButInverted.append(currentQuotient % neededBase); // Último valor
            return valueConvertedButInverted
                    .reverse()
                    .toString();
        }
        
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
        if(!isPurelyNumerical(userValue)){
            throw new RuntimeException("O valor inserido não é puramente numérico.");
        }
        
        else {
            int currentQuotient = Integer.parseInt(userValue), currentRemainder;
            StringBuilder valueConvertedButInverted = new StringBuilder();
            char nextDigit;
            do{
                currentRemainder = currentQuotient % 16;
                nextDigit = convertNumberToHexadecimalLetter(currentRemainder);
                valueConvertedButInverted.append(nextDigit);
                currentQuotient /= 16;
            } while (currentQuotient >= 16);
            
            // Último dígito:
            currentRemainder = currentQuotient % 16;
            nextDigit = convertNumberToHexadecimalLetter(currentRemainder);
            valueConvertedButInverted.append(nextDigit);

            return valueConvertedButInverted.reverse().toString();
        }
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
    
    
    /* ----- Screen-I/O functions -----*/
    
}
