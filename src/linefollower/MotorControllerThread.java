package linefollower;

import lejos.hardware.motor.Motor;
import lejos.utility.Delay;

/**
 * Controls robot behavior when obstacle is detected.
 */
public class MotorControllerThread implements Runnable {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (LineFollowerRobot.obstacleDetected) {
                avoidObstacle();
                LineFollowerRobot.obstacleDetected = false;
                LineFollowerRobot.avoidanceComplete = true;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private void avoidObstacle() {
        // Stop
        Motor.A.stop(true);
        Motor.B.stop(true);

        // Back up a bit
        Motor.A.backward();
        Motor.B.backward();
        Delay.msDelay(500);

        // Turn right
        Motor.A.setSpeed(300);
        Motor.B.setSpeed(100);
        Motor.A.forward();
        Motor.B.backward();
        Delay.msDelay(600);

        // Forward (avoidance route)
        Motor.A.setSpeed(300);
        Motor.B.setSpeed(300);
        Motor.A.forward();
        Motor.B.forward();
        Delay.msDelay(1000);

        // Turn left to align back to line
        Motor.A.setSpeed(100);
        Motor.B.setSpeed(300);
        Motor.A.backward();
        Motor.B.forward();
        Delay.msDelay(600);

        // Done
    }
}
