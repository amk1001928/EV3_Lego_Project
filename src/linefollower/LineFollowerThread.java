package linefollower;

import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

/**
 * Line follower logic based on reflected light intensity.
 */
public class LineFollowerThread implements Runnable {
    @Override
    public void run() {
        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
        SampleProvider light = colorSensor.getRedMode();
        float[] sample = new float[light.sampleSize()];
        float threshold = 0.2f;

        while (!Thread.currentThread().isInterrupted()) {
            if (LineFollowerRobot.obstacleDetected) {
                Delay.msDelay(100);  // Wait for obstacle handling
                continue;
            }

            light.fetchSample(sample, 0);
            float value = sample[0];

            if (value < threshold) {
                Motor.A.setSpeed(300);
                Motor.B.setSpeed(300);
            } else if (value > 0.6) {
                Motor.A.setSpeed(100);  // turn left
                Motor.B.setSpeed(300);
            } else {
                Motor.A.setSpeed(300);
                Motor.B.setSpeed(100);  // turn right
            }

            Motor.A.forward();
            Motor.B.forward();

            Delay.msDelay(50);
        }

        colorSensor.close();
    }
}
