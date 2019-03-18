#include "Arduino.h"
#include "ArabaKontrol.h"

Araba::Araba(int _sol1, int _sol2, int _sag1, int _sag2, int _hiz1, int _hiz2) {
  
  pinMode(_sol1, OUTPUT);
  pinMode(_sol2, OUTPUT);
  pinMode(_sag1, OUTPUT);
  pinMode(_sag2, OUTPUT);

  pinMode(_hiz1, OUTPUT);
  pinMode(_hiz2, OUTPUT);

  sol1 = _sol1;
  sol2 = _sol2;
  sag1 = _sag1;
  sag2 = _sag2;
  hiz1 = _hiz1;
  hiz2 = _hiz2;

  Serial.begin(9600);
  Serial.println("Aracin Ayarlari Yapildi.");

}

void Araba::ileri(int hizDeger) {

  Serial.println("Ileri Gidiyor");

  digitalWrite(sol1, HIGH);
  digitalWrite(sol2, LOW);
  digitalWrite(sag1, HIGH);
  digitalWrite(sag2, LOW);

  analogWrite(hiz1, hizDeger);
  analogWrite(hiz2, hizDeger);

}

void Araba::geri(int hizDeger) {
  
  Serial.println("Geri Gidiyor");

  digitalWrite(sol1, LOW);
  digitalWrite(sol2, HIGH);
  digitalWrite(sag1, LOW);
  digitalWrite(sag2, HIGH);

  analogWrite(hiz1, hizDeger);
  analogWrite(hiz2, hizDeger);

}

void Araba::sol(int hizDeger) {
  
  Serial.println("Sola Donuyor");

  digitalWrite(sol1, LOW);
  digitalWrite(sol2, LOW);
  digitalWrite(sag1, HIGH);
  digitalWrite(sag2, LOW);

  analogWrite(hiz1, 0);
  analogWrite(hiz2, hizDeger);

}

void Araba::sag(int hizDeger) {
  
  Serial.println("Sagaa Donuyor");

  digitalWrite(sol1, HIGH);
  digitalWrite(sol2, LOW);
  digitalWrite(sag1, LOW);
  digitalWrite(sag2, LOW);

  analogWrite(hiz1, hizDeger);
  analogWrite(hiz2, 0);

}

void Araba::dur() {
  
  Serial.println("Arac Durdu..");

  digitalWrite(sol1, LOW);
  digitalWrite(sol2, LOW);
  digitalWrite(sag1, LOW);
  digitalWrite(sag2, LOW);

  analogWrite(hiz1, 0);
  analogWrite(hiz2, 0);

}

