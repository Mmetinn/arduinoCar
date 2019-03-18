#ifndef ArabaKontrol_h
#define ArabaKontrol_h

#include "Arduino.h"

class Araba {

  public:
    Araba(int, int, int, int, int, int);
    void ileri(int);
    void geri(int);
    void sol(int);
    void sag(int);
    void dur();

  private:
    int sol1, sol2, sag1, sag2, hiz1, hiz2;

};

#endif

