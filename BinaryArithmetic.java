package com.gheowinapps.sistemas_numericos;

import static com.gheowinapps.sistemas_numericos.BaseConversion.convertBinaryOrOctalToDecimal;

/**
 * @author Geovani Lopes Dias
 */
public class BinaryArithmetic {
    
    // ----- Test functions:
    private static boolean isUnderByteRange(String textBinaryValue){
        return textBinaryValue.length() <= 8;
    }
    
    
    private static boolean hasBinaryFormat(String textBinaryValue){
        for(char digit: textBinaryValue.toCharArray()){
            if (digit != '0' && digit != '1'){return false;}
        }
        return true;
    }
    
    
    private static void testBinaryInput(String textA, String textB){
        if(!isUnderByteRange(textA) && !hasBinaryFormat(textA))
            {throw new RuntimeException("The first value isn't on binary form.");}
        if(!isUnderByteRange(textB) && !hasBinaryFormat(textB))
            {throw new RuntimeException("The second value isn't on binary form.");}
    }
    
    // ----- Configuration functions:
    private static char[][] setLowAndHighBinaryValues(String textA, String textB) throws RuntimeException{
        try{testBinaryInput(textA, textB);} catch(RuntimeException e){e.printStackTrace();}
        
        char[][] values = new char[2][];
        int valueA = convertBinaryOrOctalToDecimal(textA, 2), 
                valueB = convertBinaryOrOctalToDecimal(textB, 2);
        
        if (valueA > valueB){
            values[1] = textA.toCharArray();
            values[0] = textB.toCharArray();}
        else{
            values[1] = textB.toCharArray();
            values[0] = textA.toCharArray();}
        return values;
    }
    
    
    // ----- Operational functions:
    public static String sumPairOfValues(String valueA, String valueB) throws RuntimeException{
        if(valueA.equals(valueB)){return "0";}
        char[][] values = setLowAndHighBinaryValues(valueA, valueB);
        
        char[] lowValue = values[0], highValue = values[1];
        StringBuilder sum = new StringBuilder();
        boolean carryOne = false;
        
        for (int n = 1; n <= lowValue.length; n++){
            char lowValueDigit = lowValue[lowValue.length-n];
            if(lowValueDigit != highValue[highValue.length-n]){
                if(carryOne){sum.append('0');}
                else{sum.append('1');}
            }
            
            else{
                if(carryOne && lowValueDigit == '0'){sum.append('1'); carryOne = false;}
                else if(carryOne && lowValueDigit == '1'){sum.append('1');}
                else if(!carryOne && lowValueDigit == '0'){sum.append('0');}
                else {sum.append('0'); carryOne = true;}
            }
        }
        
        // Inserção dos demais dígitos do valor maior, se faltam:
        if(lowValue.length != highValue.length){
            for (int m = lowValue.length+1; m <= highValue.length; m++){
                System.out.println("Before m = "+m+" --> sum = "+sum.reverse().toString());
                char highValueDigit = highValue[highValue.length-m];
                
                // ====> Está construindo errado. 11011-1011 está retornando 10110, deveria retornar 100110.
                // Está retornando "1" quando carryOne é true & highValueDigit é 1, apesar de não fazer sentido na linhas abaixo!
                System.out.println("Mth high value digit: "+highValueDigit+"Carry One: "+carryOne);
                if (carryOne && highValueDigit == 0) {sum.append('1'); carryOne = false;}
                else if (carryOne && highValueDigit == 1) {sum.append('0');}
                else {sum.append(highValueDigit);}
                System.out.println("After m = "+m+" --> sum = "+sum.reverse().toString());
            }
        } else {if(carryOne) {sum.append('1');}}
        
        // Exceções de instância "sum" vazia ou de comprimento menor que highValue:
        if(sum.isEmpty()) {throw new RuntimeException("Instância \"soma\" de StringBuilder está vazia.");}
        if(sum.length() < highValue.length) {throw new RuntimeException("Instância \"soma\" de StringBuilder representa um valor equivocado.");}
        
        return sum.reverse().toString();
    }
        
    public static String subtractPairOfValues(String valueA, String valueB){return "";}
    public static String multiplyPairOfValues(String valueA, String valueB){return "";}
    public static String dividePairOfValues(String valueA, String valueB){return "";}
    
}
