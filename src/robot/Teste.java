package robot;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class Teste {

	public static void main(String[] args) {
		try {
			Robot robot = new Robot();
			
			robot.delay(1000);
			//digitarCaracter(robot, KeyEvent.VK_O);
			//digitarCaracter(robot, KeyEvent.VK_K);
			robot.mouseMove(50, 50);
			BufferedImage screen = robot.createScreenCapture(new Rectangle(300, 300));
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void digitarCaracter(Robot robot, int keyCode) {
		robot.keyPress(keyCode);
		robot.keyRelease(keyCode);
		robot.delay(10);
	}

}
