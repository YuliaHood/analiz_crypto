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

        while(!isValidPath) {
            if (isValidFilePath(path)) {
                isValidPath = true;
                readFile(path);
            }else {
                System.out.println("Путь до файлу не вірний( спробуйте будь ласка ще раз!");
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

        String userSign = scanner.nextLine();
        if(userSign.equals("+")){
            encrypt(path, key, scanner);
        } else if (userSign.equals("-")) {

        }else {
            System.out.println("Введіть вірний символ!");
        }
    }

    public static void encrypt(String path , int key , Scanner scanner){
        char[] elementsOfAlphabetic = new char[]{
                'А', 'Б', 'В', 'Г', 'Ґ', 'Д', 'Е', 'Є',
                'Ж', 'З', 'И', 'І', 'Ї', 'Й', 'К', 'Л',
                'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У',
                'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ь', 'Ю',
                'Я', ':', '!', '?', ',', '.', '\"', ' '};

        String FilePath = "encrypted.txt";
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FilePath))) {
            String linen = bufferedReader.readLine();
            char[] charArray = linen.toCharArray();
            for (int i = 0; i < linen.length(); i++){
                int index = search(elementsOfAlphabetic,charArray[i]);
                //String snns = String.valueOf(charArray[index]); //
                //System.out.println(snns);                       // проверка
                int newIndex = (index + key) % elementsOfAlphabetic.length;
                String newChar = String.valueOf(elementsOfAlphabetic[newIndex]);
                bufferedWriter.write(newChar);
                System.out.println("well done");
            }
            bufferedReader.close();
            bufferedWriter.close();
            readNAme(FilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    public static void readFile(String path){
        try {
            File file = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void readNAme(String newNAme){
        try {
            File file = new File(newNAme);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int search(char[] alpha, char target){
        for (int i = 0; i < alpha.length; i++) {
            if (alpha[i] == target) {
                return i;
            }
        }
        return -1;
    }
}