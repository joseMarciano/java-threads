package com.java.threads3.optimizingForLatency;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.abs;

public class Main {
    public static final String SOURCE_FILE = "./resources/many-flowers.jpg";
    public static final String DESTINATION_FILE = "./out/many-flowers.jpg";

    public static void main(String[] args) throws Exception {
        final var originalImage = ImageIO.read(new File(SOURCE_FILE));

        final var resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        recolorSingleThreaded(originalImage, resultImage);

        ImageIO.write(resultImage, "jpg", new File(DESTINATION_FILE));
    }

    public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage){
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }

    public static void recolorImage(BufferedImage originalImage,
                                    BufferedImage resultImage,
                                    int leftCorner,
                                    int topCorner,
                                    int width,
                                    int height){

        for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth() ; x++) {
            for (int y = topCorner; y < topCorner + height && y < originalImage.getHeight() ; y++) {
                recolorPixel(originalImage, resultImage, x, y);
            }
        }

    }

    public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
      try{
          int rgb = originalImage.getRGB(x, y);

          int red = getRed(rgb);
          int green = getGreen(rgb);
          int blue = getBlue(rgb);

          int newRed = red;
          int newGreen = green;
          int newBlue = blue;

          if (isShadeOfGray(red, green, blue)) {
              newRed = Math.min(255, red + 10);
              newGreen = Math.max(0, green - 80);
              newBlue = Math.max(0, blue - 20);
          }

          int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
          setRGB(resultImage, x, y, newRGB);
      }catch (RuntimeException e) {
          e.printStackTrace();
      }
    }

    public static void setRGB(BufferedImage resultImage, int x, int y, int rgb) {
        resultImage.getRaster().setDataElements(x, y, resultImage.getColorModel().getDataElements(rgb, null));
    }

    public static boolean isShadeOfGray(int red, int green, int blue) {
        return abs(red - green) < 30 && abs(red - blue) < 30 && abs(green - blue) < 30;
    }

    public static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0;

        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;

        rgb |= 0xFF000000;
        return rgb;
    }

    public static int getBlue(int rgb) {
        return rgb & 0x000000FF;
    }

    public static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    public static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }

}
