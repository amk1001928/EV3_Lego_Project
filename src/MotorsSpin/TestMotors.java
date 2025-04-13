package MotorsSpin;

import lejos.hardware.motor.Motor;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class TestMotors {

    public static void main(String[] args) {
        // Set speed and move straight for 10 seconds
        Motor.A.setSpeed(400); // Speed in degrees/sec
        Motor.B.setSpeed(400);
        Motor.A.forward();
        Motor.B.forward();
        
        LCD.clear();
        LCD.drawString("Moving forward", 0, 0);
        Delay.msDelay(10000); // Move for 10 seconds
        
        Motor.A.stop(true);
        Motor.B.stop(true);
        
        // Perform a 360-degree turn
        LCD.clear();
        LCD.drawString("Turning 360", 0, 0);
        Motor.A.rotate(360, true);  // Rotate forward
        Motor.B.rotate(-360);       // Rotate backward
        
        // Display halfway message
        LCD.clear();
        LCD.drawString("Reached halfway", 0, 0);
        LCD.drawString("Returning to base", 0, 1);
        Delay.msDelay(3000);
               
        // Move back to start position
        Motor.A.setSpeed(400); // Corrected: Replacing setPower with setSpeed
        Motor.B.setSpeed(400);
        Motor.A.forward();
        Motor.B.forward();
        Delay.msDelay(10000); // Move for 10 seconds
        
        Motor.A.stop(true);
        Motor.B.stop(true);
        
        // Stop and display message
        LCD.clear();
        LCD.drawString("Motors stopped.", 0, 1);
        
        // Wait for button press to exit
        Button.waitForAnyPress();
    }
}
