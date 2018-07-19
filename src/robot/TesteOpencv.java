package robot;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TesteOpencv {

	public static void main(String[] args) {
		Robot robot;
		String fileScreen = "/home/gustavo/screen.jpg";
		String fileSearch = "/home/gustavo/search.jpg";
		String fileSearch2 = "/home/gustavo/search2.jpg";
		
		try {
			robot = new Robot();
			BufferedImage screen = robot.createScreenCapture(new Rectangle(300, 300));
			File outputfile = new File(fileScreen);
			ImageIO.write(screen, "jpg", outputfile);

			System.out.println(Mini.compareFeature(fileSearch, fileSearch));
		} catch (AWTException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
