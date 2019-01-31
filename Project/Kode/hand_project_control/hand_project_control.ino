#include <LiquidCrystal_I2C.h>
#include <Wire.h>
#include <Servo.h>
#include<HandControl.h>


unsigned int inb;//входящий байт
unsigned char inf;// входящий флаг
unsigned char ind;
const unsigned char shift='а'; 
int c;
bool check=false;
int pos = 0;
Hand hand;
LiquidCrystal_I2C lcd(0x27,20,4);
   
void setup() { 
  hand.att();           // Комнада из библиотеки. Подключаем сервоприводы к портам, указанным в header-файле
  Serial.begin(9600);
  lcd.init();           // Инициируем работу с LCD дисплеем
  lcd.backlight();      // Включаем подсветку LCD дисплея
  lcd.setCursor(0, 0);
}

void loop() {
  inf =7;
 while(Serial.peek()<0){
    delay(1);
  }                 //Если Serial-порт пуст, то задерживаем программу на 1мс
  
if(Serial.available()>0){ 
  if((Serial.peek()==0)||(Serial.peek()==1)||(Serial.peek()==7)){ //если входящие данные равны одному из возможных значений флага,
    inf=Serial.read();  // то записываем их во флаг
    Serial.println("Flag2:");
    Serial.println(inf);
  }
  else
    inf=inf;
}


while(Serial.peek()<0){ //останавливаем программу до передачи самих данных
    delay(1);
}
 

  

   
if(inf!=7&&inf==0&&(Serial.available()>0)){ //если флаг равен 0, то обрабатываем данные, как числа (ручной режим управления)
  Serial.println("Digit");
  
  lcd.setCursor(0, 0);
  lcd.print("Manual Mode");
  lcd.setCursor(0, 1);
  
  while(Serial.peek()>0){

  int deg[5];
  
  for(int i=0; i<5; i++){
    deg[i]=Serial.read(); //заполняем массив входящими данными. Элемент масиива -- значение угла поворота сервопривода для одного пальца
    lcd.print(deg[i]);
    lcd.print(" ");
    lcd.leftToRight();
    
    
    delay(1000);
    }

  hand.SetPosition(deg); //Команда из библиотеки. Устанавливаем положения сервоприводов согласно заполненному выше массиву
  }
  
  lcd.clear();
}

else
if(inf!=7&&inf==1&&(Serial.available()>0)){ //Если флаг равен 1, то обрабатываем данные, как текст
  Serial.println("Word ");
  lcd.setCursor(0, 0);
  lcd.print("Translate Mode");
  lcd.setCursor(0, 1);
  while(Serial.peek()>0){
  inb=Serial.read();
  inb=inb+shift; //переводим в кодировку
  

  lcd.print(inb);
  lcd.leftToRight();
  lcd.print(" ");
  
  if(inb==208/*null*/)
  inb=' ';
  
  
  
  if(inb>191&&inb<224) //если буквы заглавные, заменяем их на строчные
    inb=inb+32;
     
  //Serial.println(inb);
  
  hand.SymbolTranslate(inb); //Функция библиотеки. Переводим букву в жест.
  delay(1000);// задерживаем программу на 5 секунды для того, чтобы человек успел воспринимать жесты, показываемые манипулятором
  }
  lcd.clear();
}
}
