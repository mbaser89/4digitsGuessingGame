# 4digitsGuessingGame

# Kurulum 
Projeyi indirmek için bu sayfa içerisinde clone or download kısmını kullanınız. Proje klasörünün içerisinde console uygulaması çalıştırıp, compile ve run edilir.

#### compile
javac Game.java

#### run 
java Game

## Kod Senaryosu

Oyun Game, UserGuess ve MachineGuess class'larını içermektedir. "main" metodu Game class içerisinde bulunmaktadır. Game class'ı UserGuess ve MachineGuess class'larından birer obje yaratır. UserGuess class'ı makinenin aklında tuttuğu sayıyı, MachineGuess class'ı da makinenin kullanıcının aklında tuttuğu sayıyı tahmin etmek için implement edilmiştir. 

UserGuess oyunu ile yaratılan oyunda, kullanıcı bilgisayarın tuttuğu sayıyı tahmin edene kadar devam eder. Bu durum Game içerisinde yaratılan while döngüsü ile sağlanır. UserGuess oyununun bitip bitmediğinin kontrolü, bu class içerisinde tutulan gameStatus class değişkeni ile sağlanır. UserGuess oyunu bittikten sonra, aynı durum MachineGuess için de geçerlidir. 

MachineGuess oyunu için eklenmiş olan while döngüsü, bilgisayar kullanıcının tuttuğu sayıyı doğru tahmin edene kadar devam eder. Bilgisayarın doğru tahmin yapıp yapmadığı, MachineGuess class değişkeni olan correctGuessArray dizi değişkeni  kullanılarak kontrol edilir. Bu değişken içerisinde bilgisayarın doğru tahmin ettiği sayılar tutulur. Tahmin edilmesi gereken sayı 4 basamaklı bir sayı olduğu için, bu dizinin uzunluğu 4 olduğu zaman doğru tahmin etme işlemi kabul edilir ve oyun başarılı bir şekilde sonlandırılmış olur. 

Oyuna ait detaylı açıklamalar kaynak dosyaları içerisinde ayrıntılı bir şekilde açıklanmıştır.
