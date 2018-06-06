#include"HandControl.h"
#include<Arduino.h>
#include<Servo.h>

 /*
 Using CP1251
 ������������ ������� CP1251
 */
const int navigate[Hand::n][Hand::m]= {
{224, 180, 180, 180, 180, 180},//�
{225, 90, 0, 180, 180, 180},//�
{226, 0, 0, 0, 0, 0},//�
{227, 0, 90, 180, 180, 180},//�
{228, 180, 0, 0,180, 180},//�
{229, 90, 180, 180, 90, 90},//�
{230, 90, 180, 180, 90, 90},//�
{231, 180, 0, 180, 180, 180},//�
{232, 180, 180, 180, 0, 0},//�
{233, 180, 180, 180, 0, 0},//�
{234, 180, 0, 0,180, 180},//�
{235, 180, 90, 90, 180, 180},//�
{236, 180, 90, 90, 90, 180},//�
{237, 180, 0, 0, 180, 0},//�
{238, 90, 180, 0, 0, 0},//�
{239, 180, 90, 90, 180, 180},//�
{240, 180, 0, 180, 0, 0},//�
{241, 90, 90, 90, 90, 90},//�
{242, 180, 90, 90, 90, 180},//�
{243, 0, 180, 180, 180, 0},//�
{244, 0, 90, 90, 90, 90},//�
{245, 0, 0, 180, 180, 180},//�
{246, 180, 0, 0, 180, 180},//�
{247, 0, 90, 180, 180, 180},//�
{248, 180, 0, 0, 0, 180},//�
{249, 180, 0, 0, 0, 180},//�
{250, 0, 0, 180, 180, 180},//�
{251, 0, 90, 180, 180, 90},//�
{252, 0, 0, 180, 180, 180},//�
{253, 90, 90, 180, 180, 180},//�
{254, 0, 90, 90, 90, 0},//�
{255, 90, 90, 90, 180, 180}};//�
 
 
	void Hand :: att(){

    First.attach(ServoIn1);
    Second.attach(ServoIn2);
    Third.attach(ServoIn3);
    Fourth.attach(ServoIn4);
    Fifth.attach(ServoIn5);
}
 
 
	 bool Hand:: check(int inf){//���������� true ���� �������� �����
  	
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
 

 
 
void Hand :: SymbolTranslate(unsigned char a){
    
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

    First.write(navigate[str][1]);
    Second.write(navigate[str][2]);
    Third.write(navigate[str][3]);
    Fourth.write(navigate[str][4]);
    Fifth.write(navigate[str][5]);
    delay(200);

   	Serial.println(str);
    Serial.println("Succsessfull");
	}
}
 
void Hand :: SentenceTranslate(char* s){
    unsigned char a;
    for(size_t i=0; i< strlen(s); i++){
        a=s[i];
        SymbolTranslate(a);
        delay(2000);
    }
}
    
void  Hand :: SetPosition(int a[5]){
    
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
 
    switch(fingerNum){
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





