import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

public class Main
{

    public void convertToGrayscale()
    {
        BufferedImage image = null;
        try
        {
            //Input image
            image = ImageIO.read(new File("\\\\filestore.soton.ac.uk\\users\\bjg1g17\\mydocuments\\Space Cadets\\src\\image.jpg"));

            //Create a greyscale version
            BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            ColorConvertOp op = new ColorConvertOp(image.getColorModel().getColorSpace(), newImage.getColorModel().getColorSpace(), null);
            op.filter(image, newImage);

            //Output the new image
            File outputFile = new File("\\\\filestore.soton.ac.uk\\users\\bjg1g17\\mydocuments\\Space Cadets\\src\\newimage.jpg");
            ImageIO.write(newImage, "jpg", outputFile);
        }
        catch (IOException ioe)
        {
            System.out.println("IOException");
        }
    }

    public static void main(String[] args)
    {
        Main program = new Main();
        program.convertToGrayscale();
    }

}
