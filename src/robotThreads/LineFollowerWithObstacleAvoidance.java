package linefollowerobstacle;

import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.lcd.LCD;
import lejos.hardware.Button;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class LineFollowerWithObstacleAvoidance {

    static volatile boolean obstacleDetected = false; // Shared flag for thread communication

    public static void main(String[] args) {

        // Initialize sensors
        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);
        SampleProvider light = colorSensor.getRedMode();
        float[] lightSample = new float[light.sampleSize()];

        EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
        SampleProvider distance = ultrasonicSensor.getDistanceMode();
        float[] distanceSample = new float[distance.sampleSize()];

        // Start obstacle detection thread
        Thread obstacleThread = new Thread(() -> {
            while (!Button.ESCAPE.isDown()) {
                distance.fetchSample(distanceSample, 0);
                if (distanceSample[0] < 0.2) { // less than 20 cm
                    obstacleDetected = true;
                } else {
                    obstacleDetected = false;
                }

                try {
                    Thread.sleep(100); // check every 100 ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        obstacleThread.start();

        // Set base motor speed
        Motor.A.setSpeed(300);
        Motor.B.setSpeed(300);

        // Main line-following logic
        while (!Button.ESCAPE.isDown()) {
            light.fetchSample(lightSample, 0);
            float intensity = lightSample[0];
            float threshold = 0.2f;

            LCD.clear();
            LCD.drawString("Light: " + (int)(intensity * 100) + "%", 0, 0);
            LCD.drawString("Obs: " + (obstacleDetected ? "YES" : "NO"), 0, 1);

            if (obstacleDetected) {
                Motor.A.stop(true);
                Motor.B.stop(true);
                LCD.drawString("Obstacle!", 0, 2);
                continue; // Skip rest of loop
            }

            // Line-following logic
            if (intensity < threshold) {
                // On the line
                Motor.A.setSpeed(300);
                Motor.B.setSpeed(300);
            } else {
                if (intensity > 0.6) {
                    // Very bright, turn left
                    Motor.A.setSpeed(300);
                    Motor.B.setSpeed(150);
                } else {
                    // Medium bright, turn right
                    Motor.A.setSpeed(150);
                    Motor.B.setSpeed(300);
                }
            }

            Motor.A.forward();
            Motor.B.forward();

            Delay.msDelay(50); // Delay for smoother operation
        }

        // Cleanup
        Motor.A.stop();
        Motor.B.stop();
        colorSensor.close();
        ultrasonicSensor.close();
    }
}
