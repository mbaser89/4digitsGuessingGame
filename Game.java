import java.util.Scanner;

public class Game {
    public static void main(String args[]) {
        // bilgisayarın tuttuğu sayıyı kullanıcının tahmin etmesi
        UserGuess userGuess = new UserGuess();

        //bilgisayar random 4 basamaklı bir sayı üretir.

        System.out.println("1- Bilgisayarın tuttuğu sayıyı tahmin ediniz");

        int number = userGuess.generateNumber();

        while (true) {
            System.out.println("lütfen 4 basamaklı bir sayı giriniz");
            userGuess.control(number);
            // doğru tahmin yapıldığında oyun sonlandırılır.
            if (userGuess.gameStatus == 1) {
                System.out.println("tebrikler");
                break;
            }
        }

        // kullanıcının tuttuğu sayıyı bilgisayarın tahmin etmesi
        MachineGuess machineGuess = new MachineGuess();

        System.out.println("2- Aklınızda bir sayı tutunuz ve devam etmek için enter'a basınız");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        while (true) {
            // bilgisayarın yerini ve değerini doğru tahmin ettiği değerlerin sayısı 4 ise
            // oyun sonlandırılır.
            if (machineGuess.correctGuessArray.size() == machineGuess.size) {
                System.out.println("tebrikler");
                break;
            }
            machineGuess.control();
        }


    }
}
