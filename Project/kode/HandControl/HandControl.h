#ifndef HANDCONTROL
#define HANDCONTROL
#include <string.h>
#include<Arduino.h>
#include <Servo.h>

class Hand{
 public:
    
    const int ServoIn1 =3; //Порты подключения сервоприводов
    const int ServoIn2 =8;
    const int ServoIn3 =5;
    const int ServoIn4 =6;
    const int ServoIn5 =7;
    static const int n=32;
    static const int m=6;
    
    void att();
 
    void SymbolTranslate(unsigned char a);
 
    void SentenceTranslate(char* s);
 
    char* getSymbol();
 
    void SetPosition(int a[5]);
 
   	bool check(int inf);
 
    int getPosition(int fingerNum);
 
   	Servo First;
    Servo Second;
    Servo Third;
    Servo Fourth;
    Servo Fifth;
 
 
};
 
#endif

