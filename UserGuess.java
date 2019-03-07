import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class UserGuess {
    // bulunması istenen sayımızın 4 basamaklı bir sayı olduğunu belirtir
    public int size = 4;

    //oyunun bitip bitmediğini kontrol etmek için kullanılır. Oyunun başarılı bir şekilde
    //bitirilmesi durumunda, gameStatus 1 olacaktır.
    public int gameStatus = -1;

    //random bir şekilde birbirinden farklı 4 basamaklı bir sayı üretmek için kullanılır.
    //fonksiyon çıkışında üretilen sayı döndürülür.
    public int generateNumber() {
        ArrayList<Integer> digits = new ArrayList<>();
        //başlangıçta 1'den 9'a kadar olan sayılar bir diziye eklenir.
        // 0 sayısı 4 basamaklı bir sayının ilk elemanı olamayacağı için ilk aşamada
        // dizi içerisine eklenmemiştir.
        for (int i = 1; i < 10; i++) {
            digits.add(i);
        }
        // rasgele üretilen sayı ilk aşamada birleştirme işlemi rahat olabilmesi
        // için string türünde tutulur.
        String number = "";
        Random random = new Random();
        // 4 basamaklı bir sayı üretmek için, dizi içerisinden rastgele sayılar seçilir.
        // döngünün ilk turu tamamlandıktan digits dizimize 0 da eklenir. Bu sayede
        // 0 ilk basamak değeri dışında kullanılması sağlanmış olur
        for (int i = 0; i < this.size; i++) {
            if (i == 1) {
                digits.add(0);
            }
            // dizi içerisinden rasgele bir sayı seçebilmek için, rasgele bir index üretilir.
            int index = random.nextInt(digits.size());
            // üretilen random index, dizi içerisinde seçilir.
            int digit = digits.get(index);
            //rasgele seçilen dizi String türündeki number'a eklenir.
            number += digit;
            digits.remove(index);
        }

        // string olarak elde edilen rasgele sayımız integer'a dönüştürülür.
        return Integer.parseInt(number);
    }

    /**
     * tahmin olarak kullanıcının girdiği sayının içerisinde duplicate eleman olup olmadığı kontrol edilir.
     *
     * @param str
     * @return
     */
    public boolean findIfHasDuplicates(String str) {
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    return true;
                }
            }
        }
        return false;
    }

    // aranması istenen digit ismindeki elemanın number içerisinde olup olmadığını kontrol eder
    // eğer eleman number dizisi içerisinde ise ve number dizisi içerisindeki yeri, index olarak verilen sayıya eşit değil ise 0,
    // eğer eleman number dizisi içerisinde ise ve number dizisi içerisindeki yeri, index olarak verilen sayıya eşit ise 1,
    // eğer eleman number dizisi içerisinde değilse, -1 dönderilir.
    public int controlDigitInNumber(char digit, int index, String number) {
        if (number.indexOf(digit) != -1 && number.indexOf(digit) != index) {
            return 0;
        } else if (number.indexOf(digit) == index) {
            return 1;
        } else {
            return -1;
        }
    }

    // kullanıcının tahmini kontrol edilir.
    // gerekli geri bildirim sağlanır.
    public void control(int realNumber) {
        //kullanıcıdan input alınır
        int guess = getInputFromUser();

        //kullanıcının girdiği sayı ve bilgisayarın rasgele ürettiği sayılar string türünde de tutulur.
        //bu sayade string işlemlerinin kolaylıklarından faydalanılır.
        String guessAsStr = guess + "";
        String realNumberAsStr = realNumber + "";

        //doğru girilen basamakların sayısı tutulur.
        int successfullGuessCount = 0;
        int hasNumberPlaceIncorrectCount = 0;
        int inCorrectGuessCount = 0;
        if (guessAsStr.length() != this.size) {
            System.out.println("girdiğiniz sayı 4 basamaklı bir sayı olmalıdır");
        } else if (guessAsStr.equals(realNumberAsStr)) {
            //kullanıcının girdiği değer ile makinenin tuttuğu değer birebir eşleşirse
            // oyunun başarılı bir şekilde bitirildiği set edilir.
            this.gameStatus = 1;
        } else if (findIfHasDuplicates(guessAsStr)) { // kullanıcın girdiği sayı değerinde, tekrarlayan sayı olup olmadığı kontrol edilir.
            System.out.println("Lütfen tekrar etmeyen bir sayı giriniz");
        } else {// kullanıcının girdiği değer kontrol edilir. gerekli geri bildirim verilir.

            //basamak değeri ve yeri doğru olan sayılar eklenir
            String correctGuesses = "";
            //basamak değeri doğrı ve yeri yanlış doğru olan sayılar eklenir
            String placeIncorrectGuessNumbers = "";

            for (int i = 0; i < guessAsStr.length(); i++) {
                char numberAtDigit = guessAsStr.charAt(i);
                //kontrol yapılan fonksiyon çağrılır.
                //controlDigitInNumber fonksiyonu -1, 0, 1 değerlerinden birini döner
                int searchResult = controlDigitInNumber(numberAtDigit, i, realNumberAsStr);

                if (searchResult == 0) {// basamak değerinin doğru olduğu ama yerinin yanlış olduğu basamak sayısı hesaplanır
                    hasNumberPlaceIncorrectCount += 1;
                    placeIncorrectGuessNumbers += " " + numberAtDigit;
                } else if (searchResult == 1) {// basamak değerinin ve yerinin doğru olduğu basamak sayısı hesaplanır.
                    successfullGuessCount += 1;
                    correctGuesses += " " + numberAtDigit;
                } else if (searchResult == -1) {// basamak değerinin yanlış olduğu basamak sayısı hesaplanır
                    inCorrectGuessCount += 1;
                }
            }

            if (successfullGuessCount > 0) {// basamak değerinin ve yerinin doğru olduğu basamak sayısı ekranda gösterilir.
                System.out.println("+" + successfullGuessCount + ": " + correctGuesses + " doğru girilmiştir.");
            }

            if (hasNumberPlaceIncorrectCount > 0) {// basamak değerinin doğru olduğu ama yerinin yanlış olduğu basamak sayısı ekranda gösterilir.
                System.out.println("-" + hasNumberPlaceIncorrectCount + ": " + placeIncorrectGuessNumbers + " farklı bir yerde olmalıdır.");
            }
        }
    }

    // kullanıcının input girmesi sağlanır
    public int getInputFromUser() {
        Scanner scanner = new Scanner(System.in);
        int guess = scanner.nextInt();

        return guess;
    }

}
