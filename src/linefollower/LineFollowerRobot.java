package linefollower;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class LineFollowerRobot {
    public static volatile boolean obstacleDetected = false;
    public static volatile boolean avoidanceComplete = false;

    public static void main(String[] args) {
        LCD.drawString("Press any button", 0, 0);
        Button.waitForAnyPress();

        // Create and start threads
        Thread lineFollower = new Thread(new LineFollowerThread());
        Thread ultrasonic = new Thread(new ObstacleDetectorThread());
        Thread motorController = new Thread(new MotorControllerThread());

        lineFollower.start();
        ultrasonic.start();
        motorController.start();

        // Wait until ESCAPE is pressed
        while (!Button.ESCAPE.isDown()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Robot stopping message
        LCD.clear();
        LCD.drawString("Stopping robot", 0, 0);

        // Let threads finish
        lineFollower.interrupt();
        ultrasonic.interrupt();
        motorController.interrupt();
    }
}
