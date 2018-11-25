#include <EEPROM.h>
#include "ArabaKontrol.h"

  Araba calistir(7, 10, 4, 9, 11, 3);    
  const int motorA2  = 7;  // L298N'in IN1 Girişi
  const int motorB1  = 10; // L298N'in IN2 Girişi
  const int motorA1  = 4;  // L298N'in IN3 Girişi
  const int motorB2  = 9;  // L298N'in IN4 Girişi
  int trigPin = 6; //Uzaklık sensöründen deger okuma pini
  int echoPin = 5; //Uzaklık sensöründen deger okuma pini
  long sure; //Sendorun calisma suresidir buna göre uzaklık hesaplanır
  long uzaklik; 
  int kesme=13; //Dış kesmeyi bağladığım pin 
  char dur_pin=2; //Kesme pini
  int i=0;
  int j=0;
  int state; //Androidden gelen veriler
  int vSpeed=255; //Motor hızı
  int buzzer=12; //Buzzerın bağlandığı pin

void setup() {    
  //pinler output ve input olarak ayarlandı
    pinMode(trigPin, OUTPUT);
    pinMode(echoPin,INPUT);
    pinMode(motorA1, OUTPUT);
    pinMode(motorA2, OUTPUT);
    pinMode(motorB1, OUTPUT);
    pinMode(motorB2, OUTPUT);    
    pinMode(kesme,OUTPUT);
    pinMode(buzzer, OUTPUT);
    //Kesme ayarı yapılıyor 2. pin 5v olduğunda dur fonksiyonu çalışacak.
    attachInterrupt(0, dur, RISING );
    //dur_pin degerini eeprom'a kaydettim
    EEPROM.write(1,dur_pin);    
    //boud hızını 9600 olarak ayarladım
    Serial.begin(9600);
}
 
void loop() {
  //eğer seri portta deger varsa
  if(Serial.available() > 0){     
    state = Serial.read(); 
    Serial.print("state");
    Serial.println(state);    
  } 
  //Uzaklık sensöründeki veriyi okuyup 
  //cm cinsinden uzaklık değişkenine attım   
  digitalWrite(trigPin, LOW);
  delayMicroseconds(5);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW); 
  sure = pulseIn(echoPin, HIGH);
  uzaklik= sure /29.1/2;
  Serial.print("Uzaklik ");  
  Serial.print(uzaklik);   
  //Androidden gelen veriye göre hızı 5 aşamaya böldüm   
  if (state == '1'){
    vSpeed=0;}
  else if (state == '2'){
    vSpeed=70;
  }
  else if (state == '3'){
    vSpeed=120;
  }
  else if (state == '4'){
    vSpeed=180;
  }
  else if (state == '5'){
    vSpeed=255;
  }    
  Serial.print("hiz::::");
  Serial.println(vSpeed);   
  //Gelen veri 'F' ise ileri git
  if (state == 'F') {      
    //eğer uzaklık 30 cm üstü ve buzzer çalışıyorsa buzzerı sustur
    if(digitalRead(buzzer)==HIGH && uzaklik>30){
      digitalWrite(buzzer,LOW);      
      noTone(buzzer);
    }
    calistir.ileri(vSpeed);         
  }   
  //Gelen veri 'L' ise sola git
  else if (state == 'L') {
    if(digitalRead(buzzer)==HIGH && uzaklik>30){
      digitalWrite(buzzer,LOW);      
      noTone(buzzer);
    }
    calistir.sol(vSpeed);
  }
  //Gelen veri 'B' ise geri git
  else if (state == 'B') {
   //eğer uzaklık 30 cm altı ise buzzerı çalıştır ve kesmeyi aktif et
    if(uzaklik<30){
      digitalWrite(buzzer,HIGH);
      tone(buzzer, 1000);
      digitalWrite(kesme,HIGH);    
    }else{//30 cmden buyukse buzzerı sustur ve geri git
      digitalWrite(kesme,LOW);    
      digitalWrite(buzzer,LOW);
      noTone(buzzer);
      calistir.geri(vSpeed);
   }  
 }   
 //eğer gelen veri 'R' ise saga git
 else if (state == 'R') {         
  if(digitalRead(buzzer)==HIGH && uzaklik>30){
    digitalWrite(buzzer,LOW);      
  }
  calistir.sag(vSpeed);
  }            
  //eğer gelen veri 'S' ise  dur
  else if (state == 'S'){
    if(digitalRead(buzzer)==HIGH && uzaklik>30){
      digitalWrite(buzzer,LOW);      
      noTone(buzzer);
    }
      calistir.dur();
  }  
}
//kesmede çalışacak olan dur fonksiyonu
void dur(){
  if(digitalRead(EEPROM.read(1))==1){  
    calistir.dur();   
  }
}

