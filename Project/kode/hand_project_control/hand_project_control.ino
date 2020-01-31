#include <Servo.h>
#include<HandControl.h>
unsigned char inb;//входящий байт
unsigned char inf;// входящий флаг
unsigned char ind;
const unsigned char shift='а'; 
int c;
bool check=false;
 int pos = 0;
   Hand hand;
   
void setup() { 
  hand.att(); //Комнада из библиотеки. Подключаем сервоприводы к портам, указанным в header-файле
  Serial.begin(9600);
}

void loop() {
  while(Serial.peek()<0){
    delay(10);
  } //Если Serial-порт пуст, то задерживаем программу на 10мс
  
if(Serial.available()>0){ 
  if((Serial.peek()==0)||(Serial.peek()==1)){ //если входящие данные равны одному из возможных значений флага,
    inf=Serial.read();// то записываем их во флаг
  }
  else
    inf=inf;
}


  while(Serial.peek()<0){ //останавливаем программу до передачи самих данных
    delay(10);
}
 

  

   
if(inf==0&&(Serial.available()>0)){ //если флаг равен 0, то обрабатываем данные, как числа (ручной режим управления)
  //Serial.println("Digit");
  
  while(Serial.peek()>0){

  int deg[5];
  
  for(int i=0; i<5; i++){
    deg[i]=Serial.read(); //заполняем массив входящими данными. Элемент масиива -- значение угла поворота сервопривода для одного пальца
    //Serial.println(deg[i]);
    //Serial.println("Hi there");
    delay(100);
    
    }

  hand.SetPosition(deg); //Команда из библиотеки. Устанавливаем положения сервоприводов согласно заполненному выше массиву
  
  }
  
 
}

else
if(inf==1&&(Serial.available()>0)){ //Если флаг равен 1, то обрабатываем данные, как текст
  //Serial.println("Word ");
  while(Serial.peek()>0){
  inb=Serial.read();
  
  inb=inb; //переводим в кодировку
  
  if(inb==208)
  inb=' ';
  
  if(inb>191&&inb<224) //если буквы заглавные, заменяем их на строчные
    inb=inb+32;
    
  //Serial.println(inb);

  hand.SymbolTranslate(inb); //Функция библиотеки. Переводим букву в жест.
  delay(100);// задерживаем программу на 4 секунды для того, чтобы человек успел воспринимать жесты, показываемые манипулятором
  }
  
}
}
