import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HangmanGame {

    // قائمة الكلمات التي يمكن اختيارها للعبة
    private static final String[] WORDS = {
            "تفاحة", "برتقال", "موز", "مانجو", "جافا", "مبرمج", "حاسوب", "شاطئ", "جبل", "سفر"
    };
    private static final int MAX_INCORRECT_GUESSES = 6; // عدد المحاولات الخاطئة المسموح بها

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // اختيار كلمة عشوائية من القائمة
        String wordToGuess = WORDS[random.nextInt(WORDS.length)];
        // تحويل الكلمة إلى مصفوفة من الحروف لتسهيل التعامل معها
        char[] wordToGuessChars = wordToGuess.toCharArray();
        // مصفوفة لتخزين الحروف التي خمنها المستخدم بشكل صحيح (تبدأ بفراغات أو شرطات سفلية)
        char[] guessedWord = new char[wordToGuess.length()];
        Arrays.fill(guessedWord, '_');

        List<Character> incorrectGuesses = new ArrayList<>(); // قائمة لتخزين التخمينات الخاطئة
        int incorrectGuessCount = 0; // عداد التخمينات الخاطئة
        boolean wordGuessed = false;

        System.out.println("أهلاً بك في لعبة الرجل المشنوق!");
        System.out.println("حاول تخمين الكلمة التالية:");

        // حلقة اللعب الرئيسية
        while (incorrectGuessCount < MAX_INCORRECT_GUESSES && !wordGuessed) {
            System.out.println("\nالكلمة: " + String.valueOf(guessedWord));
            System.out.println("التخمينات الخاطئة (" + incorrectGuessCount + "/" + MAX_INCORRECT_GUESSES + "): " + incorrectGuesses);
            System.out.print("أدخل حرفاً: ");

            String input = scanner.nextLine().trim(); // قراءة إدخال المستخدم وإزالة المسافات الزائدة

            // التحقق من أن المدخل هو حرف واحد فقط
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("الرجاء إدخال حرف واحد فقط.");
                continue; // العودة إلى بداية الحلقة
            }

            char guessedChar = Character.toLowerCase(input.charAt(0)); // تحويل الحرف إلى صغير لتسهيل المقارنة

            // التحقق مما إذا كان الحرف قد تم تخمينه سابقاً (سواء صحيح أو خاطئ)
            if (String.valueOf(guessedWord).indexOf(guessedChar) != -1 || incorrectGuesses.contains(guessedChar)) {
                System.out.println("لقد خمنت هذا الحرف من قبل. حاول حرفاً آخر.");
                continue;
            }

            boolean correctGuess = false;
            // التحقق مما إذا كان الحرف موجوداً في الكلمة
            for (int i = 0; i < wordToGuessChars.length; i++) {
                if (Character.toLowerCase(wordToGuessChars[i]) == guessedChar) {
                    guessedWord[i] = wordToGuessChars[i]; // كشف الحرف في الكلمة المخمنة
                    correctGuess = true;
                }
            }

            if (correctGuess) {
                System.out.println("تخمين صحيح!");
                // التحقق مما إذا كانت الكلمة قد تم تخمينها بالكامل
                if (String.valueOf(guessedWord).equals(wordToGuess)) {
                    wordGuessed = true;
                }
            } else {
                System.out.println("تخمين خاطئ.");
                incorrectGuesses.add(guessedChar);
                incorrectGuessCount++;
                drawHangman(incorrectGuessCount); // رسم جزء من الرجل المشنوق (اختياري)
            }
        }

        // نهاية اللعبة
        System.out.println("\n--- نهاية اللعبة ---");
        if (wordGuessed) {
            System.out.println("تهانينا! لقد فزت! الكلمة كانت: " + wordToGuess);
        } else {
            System.out.println("للأسف، لقد خسرت. الكلمة كانت: " + wordToGuess);
            drawHangman(MAX_INCORRECT_GUESSES); // عرض الرجل المشنوق كاملاً عند الخسارة
        }

        scanner.close();
    }

    // دالة اختيارية لرسم الرجل المشنوق بناءً على عدد التخمينات الخاطئة
    public static void drawHangman(int incorrectCount) {
        System.out.println("  _______");
        System.out.println(" |/      |");
        if (incorrectCount >= 1) {
            System.out.println(" |      (_)");
        } else {
            System.out.println(" |");
        }
        if (incorrectCount >= 2) {
            System.out.print(" |      ");
            if (incorrectCount == 2) {
                System.out.println("/");
            } else if (incorrectCount == 3) {
                System.out.println("/|");
            } else if (incorrectCount >= 4) {
                System.out.println("/|\\");
            } else {
                System.out.println();
            }
        } else {
            System.out.println(" |");
        }
        if (incorrectCount >= 5) {
            System.out.print(" |      ");
            if (incorrectCount == 5) {
                System.out.println("/");
            } else if (incorrectCount >= 6) {
                System.out.println("/ \\");
            } else {
                System.out.println();
            }
        } else {
            System.out.println(" |");
        }
        System.out.println(" |");
        System.out.println("_|___");
        System.out.println();
    }
}
