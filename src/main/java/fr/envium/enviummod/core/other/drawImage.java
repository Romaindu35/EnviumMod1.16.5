package fr.envium.enviummod.core.other;

import fr.envium.enviummod.EnviumMod;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

public class drawImage {
    /* Permet des desiner logo */
    public static ByteBuffer createIcon(String path)
    {
        BufferedImage image = null;
        try
        {
            // System.out.print(ClientProxy.class.getResource(path).getPath().replaceAll("%20",
            // " ") + "\n");
            image = ImageIO.read(EnviumMod.class.getResource(path));
            return imageToByteBuffer(image);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static ByteBuffer imageToByteBuffer(BufferedImage image)
    {
        if(image.getType() != BufferedImage.TYPE_INT_ARGB_PRE)
        {
            BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
            Graphics2D g = convertedImage.createGraphics();
            double width = image.getWidth() * (double)1;
            double height = image.getHeight() * (double)1;
            g.drawImage(image, (int)((convertedImage.getWidth() - width) / 2), (int)((convertedImage.getHeight() - height) / 2), (int)width, (int)height, null);
            g.dispose();
            image = convertedImage;
        }

        byte[] imageBuffer = new byte[image.getWidth() * image.getHeight() * 4];
        int counter = 0;
        for(int i = 0; i < image.getHeight(); i++)
            for(int j = 0; j < image.getWidth(); j++)
            {
                int colorSpace = image.getRGB(j, i);
                imageBuffer[counter + 0] = (byte)(colorSpace << 8 >> 24);
                imageBuffer[counter + 1] = (byte)(colorSpace << 16 >> 24);
                imageBuffer[counter + 2] = (byte)(colorSpace << 24 >> 24);
                imageBuffer[counter + 3] = (byte)(colorSpace >> 24);
                counter += 4;
            }
        return ByteBuffer.wrap(imageBuffer);
    }
}
