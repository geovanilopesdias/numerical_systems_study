package com.gheowinapps.sistemas_numericos;

import static com.gheowinapps.sistemas_numericos.BaseConversion.convertNonDecimalToDecimal;
import static com.gheowinapps.sistemas_numericos.BaseConversion.convertDecimalToNonDecimal;
import java.util.Scanner;

/**
 *
 * @author Geovani Lopes Dias
 */
public class SystemView {
    private static void openNonDecimalToDecimalScreen() throws RuntimeException{
        Scanner s = new Scanner(System.in);
        String base, value;
        System.out.println("=== Conversor de não-decimal em decimal ===");
        System.out.print("\tInsira a base do valor: ");
        base = s.nextLine();
        System.out.print("\tInsira o valor conforme a base informada: ");
        value = s.nextLine();
        int convertedValue = convertNonDecimalToDecimal(value, Integer.parseInt(base));
        System.out.print("\t\t>>> Seu valor convertido para a base decimal é: "+convertedValue);
    }
    
    private static void openDecimalToNonDecimalScreen() throws RuntimeException{
        Scanner s = new Scanner(System.in);
        String base, value, convertedValue, numericSystemName;
        System.out.println("=== Conversor de decimal em não-decimal ===");
        System.out.print("\tInsira a base desejada: ");
        base = s.nextLine();
        System.out.print("\tInsira o valor decimal a ser convertido: ");
        value = s.nextLine();
        convertedValue = convertDecimalToNonDecimal(value, Integer.parseInt(base));
        switch(base){
            case "2": numericSystemName = "BINÁRIA"; break;
            case "8": numericSystemName = "OCTAL"; break;
            default: numericSystemName = "HEXADECIMAL"; break;
        }
        System.out.print("\t\t>>> Seu valor convertido para a base " +
                numericSystemName+ " é: " +convertedValue);
    }
    
    private static void openBinaryArithmeticScreen() throws RuntimeException{
        Scanner s = new Scanner(System.in);
        String value1, value2; // Ampliar futuramente para ArrayList(String) para cálculos em looping
        System.out.println("=== Conversor de decimal em não-decimal ===");
        
    }
    
    public static void openSystemMenu(){
        Scanner s = new Scanner(System.in);
        
        // Fazer!!!
        System.out.println("==*== Exercício de Sistemas Numéricos ==*==");
        System.out.println("==*== Autor: Geovani Lopes Dias ==*==");
        System.out.println("Seleciona uma das opções abaixo:");
        System.out.println("\t >>> 0: Conversor de valor decimal em não decimal");
        System.out.println("\t >>> 1: Conversor de valor não-decimal em decimal");
        System.out.println("\t >>> 2: Operações aritméticas entre binários");
        System.out.print("\t\t >>> SUA OPÇÃO: ");
        String userOption = s.nextLine();
        int option = Integer.parseInt(userOption);
        System.out.println("=========================================");
        switch(option){
            case 0: openDecimalToNonDecimalScreen(); break;
            case 1: openNonDecimalToDecimalScreen(); break;
            case 2: System.out.println("Airtmética binária em construção..."); break;// Fazer openBinaryArithmeticScreen();
            default: System.out.println("Opção inváida.");
        }
    }
}
