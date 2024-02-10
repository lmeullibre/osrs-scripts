package banker;

import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import javax.imageio.ImageIO;

public class IconUtils {
    public static ImageIcon decodeToImage(String imageString) {
        try {
            byte[] imageByte = Base64.getDecoder().decode(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            BufferedImage image = ImageIO.read(bis);
            bis.close();
            return new ImageIcon(image);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ImageIcon decodeBase64ToIcon(String base64String) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64String);
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
            return new ImageIcon(img);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}