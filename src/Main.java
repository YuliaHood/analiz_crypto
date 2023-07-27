import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isValidPath = false;

        System.out.println("Добрий день! Введіть будь ласка шлях до вашохо файлу");
        String path = scanner.nextLine();

        while (!isValidPath) {
            if (isValidFilePath(path)) {
                isValidPath = true;
                readFile(path);
            } else {
                System.out.println("Шлях до файлу не вірний( спробуйте будь ласка ще раз!");
                path = scanner.nextLine();
            }
        }

        System.out.println("Тепер введіть ключ " + "(Обовязково число!)");
        boolean useNotTrue = false;
        int key = 0;
        while (!useNotTrue) {
            String userInputNumber = scanner.nextLine();
            if (userInputNumber.matches("\\d+")) {
                key = Integer.parseInt(userInputNumber);
                break;
            } else {
                System.out.println("Введений символ не є числом! Спробуйте ще раз)");
            }
        }

        System.out.println("Оберіть що саме ви хочете зробити з данними файлу :\n" +
                "Якщо ви хочете зашифрувати данні шифром Цезаря , введіть " + "+\n" +
                "Якщо ви хочете розшифрувати данні шифром Цезаря , введіть " + "-");

        boolean cheak = false;
        while (!cheak) {
            String userSign = scanner.nextLine();
            if (userSign.equals("+")) {
                encrypt(path, key);
                cheak = true;
            } else if (userSign.equals("-")) {
                decrypt(path, key);
                cheak = true;
            } else {
                System.out.println("Введіть вірний символ!");
            }
        }
    }

    public static void encrypt(String path, int key) {
        char[] elementsOfAlphabetic = new char[]{
                'А', 'Б', 'В', 'Г', 'Ґ', 'Д', 'Е', 'Є',
                'Ж', 'З', 'И', 'І', 'Ї', 'Й', 'К', 'Л',
                'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У',
                'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ь', 'Ю',
                'Я', 'а', 'б', 'в', 'г', 'ґ', 'д', 'е', 'є',
                'ж', 'з', 'и', 'і', 'ї', 'й', 'к', 'л',
                'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у',
                'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ь', 'ю',
                'я', ':', '!', '?', ',', '.', '\"', ' ','-'};

        String FilePath = "encrypted.txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FilePath))) {
            String linen = bufferedReader.readLine();
            char[] charArray = linen.toCharArray();
            for (int i = 0; i < linen.length(); i++) {
                int index = search(elementsOfAlphabetic, charArray[i]);
                int newIndex = (index + key) % elementsOfAlphabetic.length;
                String newChar = String.valueOf(elementsOfAlphabetic[newIndex]);
                bufferedWriter.write(newChar);
            }
            bufferedReader.close();
            bufferedWriter.close();
            readNAme(FilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decrypt(String path, int key) {
        char[] elementsOfAlphabetic = new char[]{
                'А', 'Б', 'В', 'Г', 'Ґ', 'Д', 'Е', 'Є',
                'Ж', 'З', 'И', 'І', 'Ї', 'Й', 'К', 'Л',
                'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У',
                'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ь', 'Ю',
                'Я', 'а', 'б', 'в', 'г', 'ґ', 'д', 'е', 'є',
                'ж', 'з', 'и', 'і', 'ї', 'й', 'к', 'л',
                'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у',
                'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ь', 'ю',
                'я', ':', '!', '?', ',', '.', '\"', ' ', '-'};

        String FilePath = "decrypted.txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FilePath))) {
            String lines = bufferedReader.readLine();
            char[] charArray = lines.toCharArray();
            for (int i = 0; i < lines.length(); i++) {
                int index = search(elementsOfAlphabetic, charArray[i]);
                if(index == -1){
                    continue;
                }
                int newIndex = (index - key) % elementsOfAlphabetic.length;
                if (newIndex <= 0) {
                    newIndex = (index - key + elementsOfAlphabetic.length) % elementsOfAlphabetic.length;
                }
                String newChar = String.valueOf(elementsOfAlphabetic[newIndex]);
                bufferedWriter.write(newChar);
            }
            bufferedReader.close();
            bufferedWriter.close();
            readNAme(FilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidFilePath(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.exists(path) && Files.isRegularFile(path) && Files.isReadable(path);
        } catch (InvalidPathException e) {
            return false;
        }
    }

    public static void readFile(String path) {
        try {
            File file = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Зчитанні данні з вашого файлу: " + line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readNAme(String newNAme) {
        try {
            File file = new File(newNAme);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Данні успішно опрацьованні: " + line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int search(char[] alpha, char target) {
        for (int i = 0; i < alpha.length; i++) {
            if (alpha[i] == target) {
                return i;
            }
        }
        return -1;
    }
}