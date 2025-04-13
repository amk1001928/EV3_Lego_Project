package linefollower;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

/**
 * Detects obstacles using the ultrasonic sensor.
 */
public class ObstacleDetectorThread implements Runnable {
    @Override
    public void run() {
        EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(SensorPort.S2);
        SampleProvider distance = usSensor.getDistanceMode();
        float[] sample = new float[distance.sampleSize()];

        while (!Thread.currentThread().isInterrupted()) {
            distance.fetchSample(sample, 0);
            if (sample[0] < 0.15) {  // less than 15 cm
                LineFollowerRobot.obstacleDetected = true;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }

        usSensor.close();
    }
}
