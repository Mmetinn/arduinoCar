### ARDUINO VE BLUETOOTH İLE ANDROID TELEFON ÜZERİNDEN KONTROL EDİLEN ROBOT
### ÖZET
#### Gerçekleştirilen projede mobil cihaz ile bluetooth modülü bağlantısı bulunan bir robot yapılmıştır. Projede kullanılmak üzere L298N Motor sürücü, Bluetooth modülü, DC Motor, ArduinoUNO ve bağlantı malzemeleri temin edilmiştir. Gereken bağlantıların yapılabilmesi için bir takım donanım bilgisinin yanısıra Arduino’nun programlanması için C dilinde bilgi sahibi olmak gerekmektedir. Gerekli olan programlama dili hakkında araştırma yapılmış ve devrelerin çalışabilir hale getirilmesi sağlanmıştır. Kablosuz haberleşme için yapılan işlemler sağlandıktan sonra elektronik cihazların kontrolü gerçekleştirilmiştir. Projenin konusu gereği istenen tüm koşullar sağlanmış ve proje sonlandırılmıştır.

### GİRİŞ
#### Projenin konusu, mobil cihazdan kablosuz olarak bluetooth ile araç kontrolü yapmaktır. DC motorların hareketi mobil cihaz ile sağlanacaktır. Bu yönlendirme işlemi için ise mobil cihazımızda bir uygulama tasarlanacak ve dizayn edilen uygulamadan gerekli bilgiler alınarak mekanik aksam üzerinde ki değişimler gözlenecektir. Teknolojinin ilerlemesiyle birlikte insanların yaşamlarını kolaylaştıran cihazlara yönelmesi ve bu cihazların tek elden kontrol edilmesi istendiği gözlemlenmektedir. Bu kontrol sırasında gereksiz kablo ve kullanımı zor olan aletlerden kaçınılmaktadır. Bu nedenle günümüzde yavaş yavaş bütün cihazların kablosuz cihazlar üzerinden kontrolüne geçiş yapılmaktadır. Mobil cihazlardan, gereken işlerin karşılanması ve gündelik yaşamı kolaylaştıran yeni buluşların insan yaşamı içerisine girmesi oldukça önemlidir. Tüm bu nedenlerden dolayı projeyi değerlendirdiğimizde aslında projenin ne kadar önemli olduğu ve göz önünde bulunduğu ortadadır. Projenin yapımında şu cihazlar kullanılmaktadır: Arduino Uno , HC05 Bluetooth modülü ,L298N motor sürücü, 4 adet DC motor, elektronik board, bağlantı kabloları.

### KULLANILAN TEKNOLOJİLER VE YÖNTEMLER
#### 1)ARDUİNO
##### Arduino bir G/Ç kartı ve Processing/Wiring dilinin bir uygulamasını içeren geliştirme ortamından oluşan bir fiziksel programlama platformudur.Arduino kartlarının donanımında bir adet Atmel AVR mikrodenetleyici (ATmega328, ATmega2560, ATmega32u4 gibi) ve programlama ve diğer devrelere bağlantı için gerekli yan elemanlar bulunur. Her Arduino kartında en azından bir 5 voltluk regüle entegresi ve bir 16MHz kristal osilator (bazılarında seramik rezonatör) vardır. Arduino kartlarında programlama için harici bir programlayıcıya ihtiyaç duyulmaz, çünkü karttaki mikrodenetleyiciye önceden bir bootloader programı yazılıdır
#### 2)DC MOTORLAR
##### Doğru akım elektrik enerjisini, mekanik enerjiye çeviren elektrik makinesine DC motor denir. Doğru akım motorlarına DA veya DC motor denilmektedir. Doğru akım zamanla yönü ve şiddeti değişmeyen akıma denir. İngilizce “Direct Current” kelimelerinin kısaltılması “DC” ile gösterilir.Manyetik alan içinde kalmış bir iletken tel üzerinden akım geçerse iletken tel üzerinde bir hareket gözlenir. DC motorların elde ettiği hareket enerjisi bu temel prensibe bağlıdır. İçerisinden akım geçen iletken, manyetik alana sokulursa iletkene bir kuvvet etkir.
#### 3)PWM ile DC Motor Hız Kontrol Yöntemi
##### DC motor hız kontrol yöntemleri ‘nden ilki pwm ile hız kontrol yöntemi’dir.Bu yöntem en yaygın hız kontrol yöntemlerinden birisidir. “Pulse Width Modulation” (Darbe genlik modülasyonu) kelimelerinin kısaltılmış halidir. Bir D.C. motorun hızını kontrol edebilmek için ayarlanabilir bir D.C. gerilime ihtiyaç vardır. Eğer 12 v.bir D.C.  motor alır ve enerji verirsek motor hızlanmaya başlayacaktır. Motorun maksimum hıza ulaşması için belirli bir süre geçmesi gerekecektir. Eğer motor maksimum hıza ulaşmadan motorun enerjisini kesersek motor bu defa yavaşlamaya başlayacaktır. Eğer enerjiyi yeterli çabuklukta sürekli kapatıp açarsak motor sıfır ile maksimum arasında bir yerdeki hız değerinde çalışacaktır. İşte pwm tam olarak bu anlama gelir. PWM yöntemi ile motor belirli aralıklarda, darbe işaretleri gönderilerek enerji verilir ve motor belirli bir hızda çalıştırılır. Bu darbe işaretlerinin genliği ayarlanarak motorun enerjili olma süresi artırılıp azaltılabilir. Bu ise motorun çalışma hızının artırılıp azaltılması anlamında gelir.
#### 4)L 298N Motor Sürücü Kartı
##### L298N H Bridge Çift Motor Sürücü Kartı genellikle motorların hız ve yönlerini kontrol etmek amacıyla kullanılan bir çift H köprülü motor sürücü kartıdır. Motor kontrolü dışında ışıklandırma projelerinde LED gruplarının parlaklarının ayarlanması amacıyla da kullanılır.H-köprüleri düşük akımlar ile büyük akımların iletilmesini kontrol edebilmek amacıyla kullanılan devrelerdir.
#### L298N H Bridge Çift Motor Sürücü Kartı pin açıklamaları:
1.	Out 1: Motor A çıkış 1
2.	Out 2: Motor A çıkış 2
3.	Out 3: Motor B çıkış 1
4.	Out 4: Motor B çıkış 2
5.	GND: Toprak
6.	5V: 5V Lojik giriş
7.	EnA: Motor A için PWM sinyalini devreye alır
8.	In1: Motor A çıkış 1 için giriş sinyali
9.	In2: Input for Motor A çıkış 2 için giriş sinyali
10.	In3: Input for Motor B çıkış 1 için giriş sinyali
11.	In4: Input for Motor B çıkış 2 için giriş sinyali
12.	EnB: Motor B için PWM sinyalini devreye alır

#### L298N H Bridge Çift Motor Sürücü Kartı - Genel Özellikler
1.	Çift H-köprülü motor sürücü
2.	L298N motor sürücü entegresi
3.	2 DC motor çift yönlü kontrol edilebilir
4.	Dahili 5V voltaj regülatörü
5.	5V-35V sürüş voltajı
6.	2A maksimum akım
7.	L298N H Bridge Çift Motor Sürücü Kartı - Teknik Özellikler
8.	Çift H-Köprülü Sürüş Çipi: L298N
9.	Lojik Voltaj: 5V
10.	Sürüş Voltajı: 5V-35V
11.	Lojik Akım: 0-36mA
12.	Sürüş Akımı: 2A (MAX single bridge)
13.	Maksimum Güç: 25W
#### 5)HC-05 Bluetooth-Serial Modül
#### HC06 Bluetooth-Serial Modül Kartı, Bluetooth SSP(Serial Port Standart)  kullanımı ve  kablosuz seri haberleşme uygulamaları için tasarlanmıştır. Hızlı prototiplemeye imkan sağlaması, breadboard, arduino ve çeşitli devrelerde rahatça kullanılabilmesi için gerekli pinler devre kartı sayesinde dışarıya alınmıştır.Standart pin yapısı sayesinde istenilen ortamlarda rahatça kontrol edilebilir. Bununla beraber ürün beraberinde gönderilen jumper kablolar ile bağlantılar rahatlıkla yapılabilir.Bluetooth 2.0'ı destekleyen bu kart, 2.4GHz frekansında haberleşme yapılmasına imkan sağlayıp açık alanda yaklaşık 10 metrelik bir haberleşme mesafesine sahiptir. 
#### Özellikleri:
1.	Bluetooth Protokolü: Bluetooth 2.0+EDR(Gelişmiş Veri Hızı)
2.	2.4GHz haberleşme frekansı
3.	Hassasiyet: ≤-80 dBm
4.	Çıkış Gücü: ≤+4 dBm
5.	Asenkron Hız: 2.1 MBps/160 KBps
6.	Senkron Hız: 1 MBps/1 MBps
7.	Güvenlik: Kimlik Doğrulama ve Şifreleme
8.	Çalışma Gerilimi: 1.8-5V(Önerilen 3.3V)
9.	Akım: 50 mA
#### 6)Hc-rs04 Ultrasonik Mesafe Sensörü
##### Hc-sr04 ultrasonik sensör sonar(Sound Navigation and Ranging ) kullanarak karşısındaki nesneye olan mesafesini hesaplayan bir input  kaynağıdır.Sonar dediğimiz sistem ses dalgalarını kullanarak cismin uzaklığını boyutunu elde etmemizi sağlar. Bu tür sensörlerin esin kaynağı yunuslar ve yarasalardır. Onlarda sonar ile iletişim kurar ve hareket eder.2cm ile 400cm  arası mesafe en sağlıklı okuma yaptığı aralıktır.Üzerinde bir alıcı ve bir verici modül bulunur.Çalışma mantığını Hc-rs04 Ultrasonik sensör nasıl çalışır kısmında ayrıntılı anlatacağım.
#### Özellikler:
1.	Güç Kaynağı _______________: +5V DC
2.	Minimum akım _____________: <2mA
3.	Çalışma akımı ______________: 15mA
4.	Çalışma frekansı ____________: 40 kHZ
7/16
5.	Efektif Açı   ________________: <15 derece
6.	Mesafe ölçüm arası  __________: 2cm – 400cm
7.	Hassasiyet  ________________: 0.3cm
8.	Tetikleme girişi darbe genişliği ___: 10uS
9.	Boyut ____________________:45mm x 20mm x 15mm
10.	Hc-sr04 Ultrasonik Mesafe Sensörünün Layoutu

### YAPILAN ÇALIŞMA
#### Akıllı telefon uygulaması yapılırken donanım elemanlarıyla telefonun haberleşmesi zorunludur. Bunun için kablosuz iletişim çok daha önemlidir. Projede bu iletişimi sağlamak için HC-05 bluetooth modülü kullanıldı.
![alt text](https://github.com/Mmetinn/images/blob/master/diyagram.png)
#### Telefon uygulamamız bluetooth vasıtasıyla robotu kontrol edecek bilgileri üzerinde mikrodenetleyicisi bulunan arduinoya HC-05 üzerinden göndermektedir. Kullanılan robotumuz 2 adet DC motora sahip olup ileri,geri,sağ ve sol yönlerde hareket edebilmektedir. Kumandadan yapılan mekanik kontrol yerine arduino tarafından kontrol edilmesi gerekmektedir. Ana kumanda tarafından sağlanan analog sinyallerin bizim tarafımızdan ayarlanması sağlanmıştır.

### Fritzing Çizimi
![alt text](https://github.com/Mmetinn/images/blob/master/fritzing.png)

### Android Uygulama
#### Android uygulama iki sayfadan oluşmakta uygulamanın ilk sayfasında daha önceden eşleştirilmiş bluetooth cihazlarını listeleyen bir listviev ve altında listview’i yenileyen bir buton bulunmakta.Liste içerisinde bağlanılmak istenilen bluetooth cihazı seçildiğinde cihaz bağlantı kapsamında ise bağlantı sağlanılıyor ve ikinci sayfaya yönlendirme yapılıyor.Ikinci sayfada ise dört buton ve bir tane seekbar bulunmakta seekbar ile motor hızları ayarlanmakta.Butonlar ile  de aracın şleri geri sağ ve sola gitmesi sağlanmaktadır.Aynı zamanda ileri geri ve sağ sol yapıldığı zaman basılan butona göre ok işareti çıkaran bir tane de imagevew mevcut.Hiç bir butona basılmadığında ise image viewde dur image görünmektedir.Uygulamanın birkaç fotoğrafı alt sayfada
<img src="https://github.com/Mmetinn/images/blob/master/ardAnd_1.png" width="300" height="400" /> <img src="https://github.com/Mmetinn/images/blob/master/ardAnd_2.png" width="300" height="400" />
<img src="https://github.com/Mmetinn/images/blob/master/ardAnd_3.png" width="300" height="400" /> <img src="https://github.com/Mmetinn/images/blob/master/ardAnd_4.png" width="300" height="400" />

