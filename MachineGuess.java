import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MachineGuess {
    public int size = 4;
    public int gameStatus = -1;
    //basamak değeri ve yeri doğru tahmin edilen sayılar eklenir.
    public ArrayList<Integer> correctGuessArray = new ArrayList();

    //basamak değeri ve yeri doğru tahmin edilen sayıların indeksi. correctGuessArray ile senkrondur.
    public ArrayList<Integer> correctGuessIndex = new ArrayList();

    //basamak değeri doğru, yeri yanlış olan sayılar eklenir.
    public ArrayList<Integer> placeIncorrectGuessNumbers = new ArrayList<>();

    // bilgisayarın tahmin yürütmesi sağlanır
    public int generateNumber() {
        // basamak değeri doğru ve yerinin yanlış olduğu değerlerin tutulduğu placeIncorrectGuessNumbers dizisi içerisindeki
        // değerler, basamak değeri ve yeri doğru olan correctGuessArray dizisi içerisinde de bulunursa
        // bu değerler placeIncorrectGuessNumbers dizisi içerisinden kaldırılır.
        ArrayList<Integer> tempPlaceIncorrectGuessNumbers = new ArrayList<>();
        for (int i = 0; i < placeIncorrectGuessNumbers.size(); i++) {
            if(correctGuessArray.indexOf(placeIncorrectGuessNumbers.get(i)) == -1){
                tempPlaceIncorrectGuessNumbers.add(placeIncorrectGuessNumbers.get(i));
            }
        }
        this.placeIncorrectGuessNumbers = tempPlaceIncorrectGuessNumbers;
//        System.out.println(this.placeIncorrectGuessNumbers);

        //random bir şekilde birbirinden farklı 4 basamaklı bir sayı üretmek için kullanılır.
        //fonksiyon çıkışında üretilen sayı döndürülür. bu fonksiyon içerisinde, kullanıcının doğru bildiği değerler
        // random sayı üretmek için kullanılan digits içerisine eklenmez. bu sayede bilgisayar sayı tahmini yaparken
        // önceden tahmin ettiği sayıları random bir sayı üretirken kullanmamış olur
        ArrayList<Integer> digits = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            if (this.correctGuessArray.indexOf(i) == -1 && this.placeIncorrectGuessNumbers.indexOf(i) == -1) {
                digits.add(i);
            }
        }
        String number = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            if (i == 1) {
                if (this.correctGuessArray.indexOf(0) == -1  && this.placeIncorrectGuessNumbers.indexOf(i) == 0) {
                    digits.add(0);
                }
            }
            int index = random.nextInt(digits.size());
            int digit = digits.get(index);
            number += digit;
            digits.remove(index);
        }

        //StringBuilder class'ı string değişimini sağladığı için kullanılmıştır.
        StringBuilder combineWithKnowns = new StringBuilder(number);
        // random olarak üretilen sayı içerisindeki değerler, kullanıcının yerini ve değerini
        // doğru olarak tahmin ettiği değerler ile yer değiştirilir.
        for (int i = 0; i < this.correctGuessArray.size(); i++) {
            String temp = this.correctGuessArray.get(i) + "";
            combineWithKnowns.setCharAt(this.correctGuessIndex.get(i), temp.charAt(0));
        }
        // kullanıcının yerinin ve değerinin doğru olduğu yerler dışındaki index'ler hesaplanır.
        // bu sayede kullanıcının doğru tahmin ettiği, yerinin yanlış olduğu değerlerin rasgele deneneceği yerler
        // belirtilmiş olur
        ArrayList<Integer> freePlaces = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if(this.correctGuessIndex.indexOf(i) == -1){
                freePlaces.add(i);
            }
        }
        // kullanıcın değerlerinin doğru, yerlerinin yanlış olduğu değerlerin, freePlaces olarak belirtilen yerlere rasgele
        // eklenmesi sağlanır.
        this.placeIncorrectGuessNumbers.stream().forEach((placeIncorrectGuessNumber) -> {
            int randomForPlace = random.nextInt(freePlaces.size());
            int randomPlaceIndex = freePlaces.get(randomForPlace);
            String temp = placeIncorrectGuessNumber + "";
            combineWithKnowns.setCharAt(randomPlaceIndex, temp.charAt(0));
            freePlaces.remove(randomForPlace);
        });

        // bilgisayarın tahmin ettiği sayı integer olarak döndürülür.
        return Integer.parseInt(combineWithKnowns.toString());
    }

    // kullanıcıdan bilgisayar geri bildirim verilmesi sağlanır.
    // bilgisayarın tahmin ettiği sayıda herhangi bir değer doğru değil ise, ipucu verilmez.
    // ipucu verilmeden enter'a basarak turun geçilmesi için nextLine kullanılmıştır.
    public int getInputFromUser() {
        Scanner scanner = new Scanner(System.in);
        try {
            int guess = Integer.parseInt(scanner.nextLine());
            return guess;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void control(){
        // bilgisayar tahmini bir sayı üretir.
        int computerGuessedNumber = this.generateNumber();
        // bilgisayarın ürettiği sayı string olarak tutulur işlem kolaylığı sağlanır.
        String computerGuessedNumberStr = computerGuessedNumber + "";
        System.out.println("computerGuessedNumber: " + computerGuessedNumber);

        // kullanıcının ipucu girmesi sağlanır.
        System.out.println("Doğru yerde bulunan rakamları giriniz: ");
        int correctGuessedDigits = this.getInputFromUser();

        if (correctGuessedDigits != -1) {// ipucu girilip girilmediği kontrol edilir
            String correctGuessedDigitAsStr = correctGuessedDigits + "";
            for (int i = 0; i < correctGuessedDigitAsStr.length(); i++) {// girilen ipuçlarındaki sayılar gezilir.
                //girilen ipucunun bilgisayarın tahmin ettiği sayıda gerçekten var olup olmadığı kontrol edilir.
                // Bu ek bir kontroldür. Kullanıcı hatalı bir ipucu girmesi kısmi olarak engellenmiş olur.
                int foundDigitIndex = computerGuessedNumberStr.indexOf(correctGuessedDigitAsStr.charAt(i));
                // girilen ipucu bilgisayarın tahmin ettiği sayı içerisinde var ise ve girilen bu ipucu
                // önceki girilen ipuçları içerisinde birebir match etmiyor ise, yeni bir ipucu olarak
                // correctGuessArray ve correctGuessIndex dizileri içerisine atılır.
                if (foundDigitIndex != -1 && this.correctGuessIndex.indexOf(foundDigitIndex) == -1) {
                    this.correctGuessArray.add(Integer.parseInt(correctGuessedDigitAsStr.charAt(i) + ""));
                    this.correctGuessIndex.add(foundDigitIndex);
                }
            }
        }

        // kullanıcıdan değeri doğru, yeri yanlış olan değerler girilmesi sağlanır.
        System.out.println("Yanlış yerde bulunan rakamları giriniz: ");
        int inCorrectPlaceGuessDigits = this.getInputFromUser();
        if (inCorrectPlaceGuessDigits != -1) {// herhangi bir değer girilip girilmediği kontrol edilir.
            String inCorrectPlaceGuessDigitsAsStr = inCorrectPlaceGuessDigits + "";//değer işlem kolaylığı için string'e çevrilir.
            // girilen değerler placeIncorrectGuessNumbers dizisi içerisine eklenir. ekleme yapılırken
            // dizi içerisinde aynı eleman bulunup bulunmadığı kontrol edilir.
            for (int i = 0; i < inCorrectPlaceGuessDigitsAsStr.length(); i++) {
                int inCorrectPlaceDigit = Integer.parseInt(inCorrectPlaceGuessDigitsAsStr.charAt(i) + "");
                if(this.placeIncorrectGuessNumbers.indexOf(inCorrectPlaceDigit) == -1){
                    this.placeIncorrectGuessNumbers.add(inCorrectPlaceDigit);
                }
            }
        }

//            System.out.println(mng.placeIncorrectGuessNumbers);


    }

}

