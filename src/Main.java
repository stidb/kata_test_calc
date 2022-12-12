import java.io.IOException;
import java.util.Scanner;
import java.util.regex.*;

public class Main {
    static String[] strSplitted = new String[4];
    //strSplitted[0] = [+-*\]
    // strSplitted[1] operand 1
    // strSplitted[2] operand 2
    // strSplitted[3] result
    public static void main(String[] args) throws IOException {
        System.out.println("Hi. I am a calculator. \nJust enter what I have to calculate and I will do it. \nI can work with Arabic (1,3,5, etc) and Roman numerals (I,III,V, etc).");
        System.out.println("Entered numbers cannot be greater than 10 (or X)");
        System.out.println("Example:  a + b; a - b; a * b; a / b;");
        String strInputString ="";
        if (args.length > 0) {
            for(int i=0;i<args.length;i++)
                strInputString = strInputString + args[i];
            System.out.printf("It looks like you already entered \"%s\"\n", strInputString);
        } else {
            System.out.printf("Your math phrase: ");
            Scanner in = new Scanner(System.in);
            strInputString = in.nextLine();
            in.close();
            System.out.printf("It looks like you entered \"%s\"\n", strInputString);
        }
        strInputString = strInputString.toUpperCase().replace(" ","");
        System.out.println("Result:" + calc(strInputString));
    }
    static boolean isRoman(String strInput) {
        String sRomanRegex = "^(I{1,3}|IV|VI{0,3}|I{0,1}X)([\\+\\-\\*/])(I{1,3}|IV|VI{0,3}|I{0,1}X)";
        Pattern regexp = Pattern.compile(sRomanRegex);
        Matcher matcher = regexp.matcher(strInput);
        if (matcher.matches()) {
            strSplitted[0] = matcher.group(2);
            strSplitted[1] = toArabic(matcher.group(1));
            strSplitted[2] = toArabic(matcher.group(3));
        }
        return matcher.matches();
    }

    static boolean isArabic(String strInput) {
//        i{1,3}|iv|vi{0,3}|i{0,1}x
        String sArabicRegex = "^([0-9]{1}|10)([\\+\\-\\*/])([0-9]{1}|10)";
        Pattern regexp = Pattern.compile(sArabicRegex);
        Matcher matcher = regexp.matcher(strInput);
        if (matcher.matches()) {
            strSplitted[0] = matcher.group(2);
            strSplitted[1] = matcher.group(1);
            strSplitted[2] = matcher.group(3);
        }
        return matcher.matches();
    }
    static String toArabic(String strInput) {
        return strInput
                .replace("VIII","8")
                .replace("VII","7")
                .replace("IX","9")
                .replace("X","10")
                .replace("VI","6")
                .replace("IV","4")
                .replace("V","5")
                .replace("III","3")
                .replace("II","2")
                .replace("I","1");
    }
    static String toRoman(String strInput) {
        Integer intInput = Integer.parseInt(strInput);
        return "I".repeat(intInput)
                .replace("IIIII", "V")
                .replace("IIII", "IV")
                .replace("VV", "X")
                .replace("VIV", "IX")
                .replace("XXXXX", "L")
                .replace("XXXX", "XL")
                .replace("LL", "C")
                ;
    }
    public static String calc(String strInput) throws IOException {
        boolean bArabic = false;
        if (isRoman(strInput)) {
            bArabic = false;
        }
        else if (isArabic(strInput)) {
            bArabic = true;
        } else {
            throw new IOException("Sorry, you entered something wrong");
        }
        if (strSplitted[0].equals("+")) {
            strSplitted[3] = "" + (Integer.parseInt(strSplitted[1]) + Integer.parseInt(strSplitted[2]));
        }
        if (strSplitted[0].equals("-")) {
            strSplitted[3] = "" + (Integer.parseInt(strSplitted[1]) - Integer.parseInt(strSplitted[2]));
        }
        if (strSplitted[0].equals("*")) {
            strSplitted[3] = "" + (Integer.parseInt(strSplitted[1]) * Integer.parseInt(strSplitted[2]));
        }
        if (strSplitted[0].equals("/")) {
            strSplitted[3] = "" + (Integer.parseInt(strSplitted[1]) / Integer.parseInt(strSplitted[2]));
        }
        if (!bArabic && Integer.parseInt(strSplitted[3]) == 0) {
            throw new IOException("Sorry, the result is \"0\", but the Romans do not want to count it in the calculation.");
        }
        if (!bArabic && Integer.parseInt(strSplitted[3]) < 0) {
            throw new IOException("Sorry, the Romans didn't want to count negative numbers");
        }
        if (!bArabic && Integer.parseInt(strSplitted[3]) > 100) {
            System.out.println(strSplitted[3]);
            throw new IOException("o_O how did you do that?");
        }
        if (!bArabic) {
            return toRoman(strSplitted[3]);
        }

        return strSplitted[3];
    }
}

