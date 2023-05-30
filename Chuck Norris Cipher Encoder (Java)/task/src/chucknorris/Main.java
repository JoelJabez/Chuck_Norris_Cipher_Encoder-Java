package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (!input.equals("exit")) {
            System.out.println("Please input operation (encode/decode/exit):");
            input = scanner.nextLine();

            switch (input) {
                case "encode" -> {
                    System.out.println("Input string:");
                    String encodeInput = scanner.nextLine();

                    System.out.println("Encoded string:");
                    encodeText(encodeInput);
                }

                case "decode" -> {
                    System.out.println("Input encoded string:");
                    String decodeInput = scanner.nextLine();

                    decodeText(decodeInput);
                }

                case "exit" -> System.out.println("Bye!");

                default -> System.out.printf("There is no '%s' operation\n", input);
            }
            System.out.println();
        }
    }

    public static void encodeText(String text) {
        String binaryText = "";
        String unaryText = "";

        for (int i = 0; i < text.length(); i++) {
            int ascii = Integer.parseInt(Integer.toBinaryString(text.charAt(i)));
            binaryText = String.format("%s%07d", binaryText, ascii);
        }

        for (int i = 0; i < binaryText.length(); i++) {
            if (i != 0 && binaryText.charAt(i) == binaryText.charAt(i - 1)) {
                unaryText = String.format("%s0", unaryText);
            } else {
                switch (binaryText.charAt(i)) {
                    case '0' -> unaryText = String.format("%s 00 0", unaryText);
                    case '1' -> unaryText = String.format("%s 0 0", unaryText);

                }
            }
        }

        System.out.println(unaryText.substring(1));
    }

    public static void decodeText(String text) {
        String[] unaryText = text.split(" ");
        int i, j, times;

        String errorMessage = "Encoded string is not valid";
        for (String t: unaryText) {
            for (i = 0; i < t.length(); i++) {
                if (t.charAt(i) != '0') {
                    System.out.println(errorMessage);
                    return;
                }
            }
        }

        if(unaryText.length % 2 != 0) {
            System.out.println(errorMessage);
            return;
        }

        int length = unaryText[0].length();
        if (!(length > 0 && length <= 2)) {
            System.out.println(errorMessage);
            return;
        }

        String binaryText = "";
        for (i = 0; i < unaryText.length; i+=2) {
            times = unaryText[i + 1].length();

            switch (unaryText[i]) {
                case "00" -> {
                    for (j = 0; j < times; j++) {
                        binaryText += "0";
                    }
                }

                case "0" -> {
                    for (j = 0; j < times; j++) {
                        binaryText += "1";
                    }
                }
            }
        }

        if (binaryText.length() % 7 == 0) {
            String[] asciiText = new String[binaryText.length() / 7];
            for (i = 0; i < asciiText.length; i++) {
                asciiText[i] = binaryText.substring(i * 7, (i * 7) + 7);
            }

            System.out.println("Decoded string:");
            for (String s: asciiText) {
                System.out.print((char) Integer.parseInt(s, 2));
            }
            System.out.println();
            return;
        }

        System.out.println(errorMessage);
    }
}