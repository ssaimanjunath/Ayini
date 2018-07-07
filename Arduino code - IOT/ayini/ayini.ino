#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

int sense_Pin = 0; // sensor input at Analog pin A0

const char* ssid     = "";//wifi username here
const char* password = "";///wifi password here

const char* host = "helloagri.000webhostapp.com";
int led_pin = 4;
int pH_pin = 16;

int moisture_value = 0;
int ferti_value = 0;
float ph_value = 0;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  delay(200);
  pinMode(led_pin,OUTPUT);
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");  
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
  }

void loop() {
  // put your main code here, to run repeatedly:   
   Serial.print("MOISTURE LEVEL : ");
   moisture_value= analogRead(sense_Pin);   
   moisture_value = constrain(moisture_value, 485, 1023);
   moisture_value = map(moisture_value, 485, 1023, 100, 0);
   Serial.print(moisture_value);
   Serial.println("%");

   ph_value = analogRead(pH_pin);
   if(ph_value==0)
   {
      ph_value=6.75;
      Serial.print(ph_value);
      Serial.println(" pH");
   }
   else
   {
      ph_value=8.15;
      Serial.print(ph_value);
      Serial.println(" pH");
   }
   delay(3000);
   if((ph_value==6.75)&&(moisture_value>=90))
   {
      ferti_value=90;
   }
   else if((ph_value==6.75)&&(moisture_value<90 && moisture_value>=75))
   {
      ferti_value=75;
   }
   else if((ph_value==6.75)&&(moisture_value<75 && moisture_value>50))
   {
      ferti_value=60;
   }
   else
   {
      ferti_value=40;
   }
   Serial.print(ferti_value);
   Serial.println(" %");

   /*if(moisture_value<=45)
   {
      digitalWrite(led_pin,HIGH);
   }
   else
   {
      digitalWrite(led_pin,LOW);
   }*/

  HTTPClient http;  //Declare an object of class HTTPClient
 
    http.begin("http://helloagri.000webhostapp.com/getIrrigation.php");  //Specify request destination
    int httpCode = http.GET();                                                                  //Send the request
 
    if (httpCode > 0) { //Check the returning code
 
      String payload = http.getString();   //Get the request response payload
      if(moisture_value<=55)
      {
        if(payload=="1")
        {
          digitalWrite(led_pin,HIGH);  
        }
        else
        {
          digitalWrite(led_pin,LOW);  
        }
      }  
      else if(moisture_value<=45)
      {               
          digitalWrite(led_pin,HIGH);          
      }
      else
      {
          digitalWrite(led_pin,LOW);  
      }
 
    }
 
    http.end();   //Close connection

   
   Serial.print("Connecting to ");
   Serial.println(host);
   // Use WiFiClient class to create TCP connections
  WiFiClient client;
  const int httpPort = 80;
  if (!client.connect(host, httpPort)) {
    Serial.println("Connection failed");
    return;
  }
  
  // We now create a URI for the request
  String url = "/post_Device.php?&mois=";
  url += moisture_value;
  url += "&ph=";
  url += ph_value;
  url += "&ferti=";
  url += ferti_value;
  Serial.print("Requesting URL: ");
  Serial.println(url);
  
  // This will send the request to the server
  client.print(String("GET ") + url + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" + 
               "Connection: close\r\n\r\n");
               
  delay(3000);  
  Serial.println();
  Serial.println("Value added to database...");  
  Serial.println();
  delay(3000);
}
