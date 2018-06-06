#include"HandControl.h"
#include<Arduino.h>
#include<Servo.h>

 /*
 Using CP1251
 »спользуетс€ таблица CP1251
 */
const int navigate[Hand::n][Hand::m]= {
{224, 180, 180, 180, 180, 180},//а
{225, 90, 0, 180, 180, 180},//б
{226, 0, 0, 0, 0, 0},//в
{227, 0, 90, 180, 180, 180},//г
{228, 180, 0, 0,180, 180},//д
{229, 90, 180, 180, 90, 90},//е
{230, 90, 180, 180, 90, 90},//ж
{231, 180, 0, 180, 180, 180},//з
{232, 180, 180, 180, 0, 0},//и
{233, 180, 180, 180, 0, 0},//й
{234, 180, 0, 0,180, 180},//к
{235, 180, 90, 90, 180, 180},//л
{236, 180, 90, 90, 90, 180},//м
{237, 180, 0, 0, 180, 0},//н
{238, 90, 180, 0, 0, 0},//о
{239, 180, 90, 90, 180, 180},//п
{240, 180, 0, 180, 0, 0},//р
{241, 90, 90, 90, 90, 90},//с
{242, 180, 90, 90, 90, 180},//т
{243, 0, 180, 180, 180, 0},//у
{244, 0, 90, 90, 90, 90},//ф
{245, 0, 0, 180, 180, 180},//х
{246, 180, 0, 0, 180, 180},//ц
{247, 0, 90, 180, 180, 180},//ч
{248, 180, 0, 0, 0, 180},//ш
{249, 180, 0, 0, 0, 180},//щ
{250, 0, 0, 180, 180, 180},//ъ
{251, 0, 90, 180, 180, 90},//ы
{252, 0, 0, 180, 180, 180},//ь
{253, 90, 90, 180, 180, 180},//э
{254, 0, 90, 90, 90, 0},//ю
{255, 90, 90, 90, 180, 180}};//€
 
 
	void Hand :: att(){

    First.attach(ServoIn1);
    Second.attach(ServoIn2);
    Third.attach(ServoIn3);
    Fourth.attach(ServoIn4);
    Fifth.attach(ServoIn5);
}
 
 
	 bool Hand:: check(int inf){//¬озвращает true если передано число
  	
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





