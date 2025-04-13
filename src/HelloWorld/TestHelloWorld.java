package HelloWorld;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class TestHelloWorld {
    public static void main(String[] args) {
        String message1 = "This is my 1st LEGO code";
        String message2 = "Make me autonomous";
        String message3 = "Press any button to Stop.";
        
        LCD.clear();
        LCD.drawString("Welcome", 0, 0);
        Delay.msDelay(1000);
        
        LCD.clear();
        TextWrap(message1);
        Delay.msDelay(2000);
        
        LCD.clear();
        TextWrap(message2);
        Delay.msDelay(2000);
        
        LCD.clear();
        TextWrap(message3);
        Delay.msDelay(2000);
        
        // Wait for a button press to exit
        Button.waitForAnyPress();
    }
    
    public static void TextWrap(String msg) {
        int maxLength = 16;
        int Localx = 0, Localy = 0;
        
        for (int i = 0; i < msg.length(); i += maxLength) {
            String line = msg.substring(i, Math.min(i + maxLength, msg.length()));
            LCD.drawString(line, Localx, Localy++);
        }
    }
}
