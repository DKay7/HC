#include <Servo.h>
#include<HandControl.h>
unsigned char inb;
unsigned char inf;
unsigned char ind;
const unsigned char shift='а';
int c;
bool check=false;
 int pos = 0;
   Hand hand;
   
void setup() {  
  hand.att();
  Serial.begin(9600);
}
 
void loop() {
  while(Serial.peek()<0){
    delay(10);
  }
  
if(Serial.available()>0){
  if((Serial.peek()==0)||(Serial.peek()==1)){
    inf=Serial.read();
  }
  else
    inf=inf;
}


  while(Serial.peek()<0){
    delay(10);
}
 

  

   
if(inf==0&&(Serial.available()>0)){
  //Serial.println("Digit");
  
  while(Serial.peek()>0){

  int deg[5];
  
  for(int i=0; i<5; i++){
    deg[i]=Serial.read();
    //Serial.println(deg[i]);
    //Serial.println("Hi there");
    delay(1000);
    
    }

  hand.SetPosition(deg);
  
  }
  
 
}

else
if(inf==1&&(Serial.available()>0)){
  //Serial.println("Word ");
  while(Serial.peek()>0){
  inb=Serial.read();
  
  inb=inb+shift;
  
  if(inb==208)
  inb=' ';
  
  if(inb>191&&inb<224)
    inb=inb+32;
    
  //Serial.println(inb);

  hand.SymbolTranslate(inb);
  delay(1000);
  }
  
}
}

