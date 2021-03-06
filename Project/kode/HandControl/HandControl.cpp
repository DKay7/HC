#include"HandControl.h"
#include<Arduino.h>
#include<Servo.h>

 /*
 Using CP1251
 ������������ ������� CP1251
 */
const int navigate[Hand::n][Hand::m]= { //������������� �������
{48, 90, 45, 90, 90, 90},//�
{49, 45, 0, 90, 90, 90},//�
{50, 0, 0, 0, 0, 0},//�
{51, 0, 45, 90, 90, 90},//�
{52, 90, 0, 0,90, 90},//�
{53, 45, 45, 90, 45, 45},//�
{54, 45, 45, 90, 45, 45},//�
{55, 90, 0, 90, 90, 90},//�
{56, 90, 45, 90, 0, 0},//�
{57, 90, 45, 90, 0, 0},//�
{58, 90, 0, 0,90, 90},//�
{59, 90, 45, 45, 90, 90},//�
{60, 90, 45, 45, 45, 90},//�
{61, 90, 0, 0, 90, 0},//�
{62, 45, 90, 0, 0, 0},//�
{63, 90, 45, 45, 90, 90},//�
{64, 90, 0, 90, 0, 0},//�
{65, 45, 45, 45, 45, 45},//�
{66, 90, 45, 45, 45, 90},//�
{67, 0, 45, 90, 90, 0},//�
{68, 0, 45, 45, 45, 45},//�
{69, 0, 0, 90, 90, 90},//�
{70, 90, 0, 0, 90, 90},//�
{71, 0, 45, 90, 90, 90},//�
{72, 90, 0, 0, 0, 90},//�
{73, 90, 0, 0, 0, 90},//�
{74, 0, 0, 90, 90, 90},//�
{75, 0, 45, 90, 90, 45},//�
{76, 0, 0, 90, 90, 90},//�
{77, 45, 45, 90, 90, 90},//�
{78, 0, 45, 45, 45, 0},//�
{79, 45, 45, 45, 90, 90},//�
{97, 45, 90, 90, 90, 90},//a
{98, 0, 0, 0, 0, 90},//b
{99, 0, 45, 45, 45, 45},//c
{100, 45, 0, 40, 40, 40},//d
{101, 90, 45, 45, 45, 45},//e
{102, 0, 90, 45, 0, 0},//f
{103, 90, 30, 90, 90, 90},//g
{104, 90, 0, 90, 90, 0},//h
{105, 0, 90, 90, 90, 90},//i
{106, 0, 90, 90, 90, 0},//j
{107, 0, 0, 45, 90, 90},//k
{108, 0, 0, 90, 90, 90},//l
{109, 90, 45, 45, 45, 45}, //m
{110, 45, 45, 45,  90, 90},//n
{111, 45, 45, 45, 45, 45},//o
{112, 90, 0, 45, 90, 90},//p
{113, 90, 0, 90, 90, 90},//q
{114, 90, 30, 0, 90, 90},//r
{115, 90, 90, 90, 90, 90},//s
{116, 0, 90, 0, 0, 0},//t
{117, 90, 20, 0, 90, 90},//u
{118, 90, 10, 20, 90, 90},//v
{119, 90, 0, 0, 0, 90},//w
{120, 90, 30, 30, 90, 90},//x
{121, 30, 90, 90, 90, 0},//y
{122, 90, 10, 90, 90, 90}//z
};
 
 
	void Hand :: att(){ //��������� ��������� �������������

    First.attach(ServoIn1);
    Second.attach(ServoIn2);
    Third.attach(ServoIn3);
    Fourth.attach(ServoIn4);
    Fifth.attach(ServoIn5);
}
 
 
	 bool Hand:: check(int inf){//���������� true ���� �������� ����� (�� ����� �����)
  	
  	switch(inf){ 
  		case 0:
  			return true;
  			break;
  			
  		case 1:
  			return false;
  			break;
  }
	return 0;
}
 

 
 
void Hand :: SymbolTranslate(unsigned char a){ //������� �������� � �����
    
   	int str=-1;
    int i;
    for(i=0; i<n; i++){
        if(navigate[i][0]==(int)(a)){
            str=i;
            break;  
        }
    }
    
    if(str==-1){
    	return;
	}
	else{

    First.write(navigate[str][1]); //��������� ������������ � ������ ���������
    Second.write(navigate[str][2]);
    Third.write(navigate[str][3]);
    Fourth.write(navigate[str][4]);
    Fifth.write(navigate[str][5]);
    delay(200);

   	Serial.println(str);
    Serial.println("Succsessfull");
	}
}
 
void Hand :: SentenceTranslate(char* s){ //������� ����������� � �����
    unsigned char a;
    for(size_t i=0; i< strlen(s); i++){
        a=s[i];
        SymbolTranslate(a);
        delay(2000);
    }
}
    
void  Hand :: SetPosition(int a[5]){ //����� ������� ����������. 
    
	First.write(a[0]);
    Second.write(a[1]);
   Third.write(a[2]);
   Fourth.write(a[3]);
   Fifth.write(a[4]);

    Serial.println(First.read());
   Serial.println(Second.read());
   Serial.println(Third.read());
   Serial.println(Fourth.read());
   Serial.println(Fifth.read());
   
    Serial.println("Succsessfull 2");
}
 
int  Hand :: getPosition(int fingerNum){
 
    switch(fingerNum){ //���������� ���� �������� ������������
        case 1:
            return First.read();
            break;
        case 2:
            return Second.read();
            break;
        case 3:
            return Third.read();
            break;
        case 4:
            return Fourth.read();
            break;
        case 5:
            return Fifth.read();
            break;
    
    }
   
   
   return 0;} 





