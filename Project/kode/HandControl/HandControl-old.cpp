#include"HandControl.h"
#include<Arduino.h>
#include<Servo.h>

 /*
 Using CP1251
 »спользуетс€ таблица CP1251
 */
const int navigate[Hand::n][Hand::m]= { //навигационна€ матрица
{224, 90, 45, 90, 90, 90},//а
{225, 45, 0, 90, 90, 90},//б
{226, 0, 0, 0, 0, 0},//в
{227, 0, 45, 90, 90, 90},//г
{228, 90, 0, 0,90, 90},//д
{229, 45, 45, 90, 45, 45},//е
{230, 45, 45, 90, 45, 45},//ж
{231, 90, 0, 90, 90, 90},//з
{232, 90, 45, 90, 0, 0},//и
{233, 90, 45, 90, 0, 0},//й
{234, 90, 0, 0,90, 90},//к
{235, 90, 45, 45, 90, 90},//л
{236, 90, 45, 45, 45, 90},//м
{237, 90, 0, 0, 90, 0},//н
{238, 45, 90, 0, 0, 0},//о
{239, 90, 45, 45, 90, 90},//п
{240, 90, 0, 90, 0, 0},//р
{241, 45, 45, 45, 45, 45},//с
{242, 90, 45, 45, 45, 90},//т
{243, 0, 45, 90, 90, 0},//у
{244, 0, 45, 45, 45, 45},//ф
{245, 0, 0, 90, 90, 90},//х
{246, 90, 0, 0, 90, 90},//ц
{247, 0, 45, 90, 90, 90},//ч
{248, 90, 0, 0, 0, 90},//ш
{249, 90, 0, 0, 0, 90},//щ
{250, 0, 0, 90, 90, 90},//ъ
{251, 0, 45, 90, 90, 45},//ы
{252, 0, 0, 90, 90, 90},//ь
{253, 45, 45, 90, 90, 90},//э
{254, 0, 45, 45, 45, 0},//ю
{255, 45, 45, 45, 90, 90}};//€
 
 
	void Hand :: att(){ //процедура инициации сервоприводов

    First.attach(ServoIn1);
    Second.attach(ServoIn2);
    Third.attach(ServoIn3);
    Fourth.attach(ServoIn4);
    Fifth.attach(ServoIn5);
}
 
 
	 bool Hand:: check(int inf){//¬озвращает true если передано число (цз учета флага)
  	
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
 

 
 
void Hand :: SymbolTranslate(unsigned char a){ //перевод символов в жесты
    
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

    First.write(navigate[str][1]); //высавл€ем сервоприводы в нужное положение
    Second.write(navigate[str][2]);
    Third.write(navigate[str][3]);
    Fourth.write(navigate[str][4]);
    Fifth.write(navigate[str][5]);
    delay(200);

   	Serial.println(str);
    Serial.println("Succsessfull");
	}
}
 
void Hand :: SentenceTranslate(char* s){ //перевод предложени€ в жесты
    unsigned char a;
    for(size_t i=0; i< strlen(s); i++){
        a=s[i];
        SymbolTranslate(a);
        delay(2000);
    }
}
    
void  Hand :: SetPosition(int a[5]){ //–ежим ручного управлени€. 
    
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
 
    switch(fingerNum){ //возвращает угол поворота сервопривода
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





