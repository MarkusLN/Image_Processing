import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageCentering {
    private static int top = Integer.MAX_VALUE;
    private static int left = Integer.MAX_VALUE;
    private static int right = Integer.MIN_VALUE;
    private static int bottom = Integer.MIN_VALUE;
    private static BufferedImage image;

    public static void executeCenteringForEveryFile(String directory, String outputDirectory, int backgroundColor) throws IOException {
        List<String> results = new ArrayList<String>();
        File[] files = new File(directory).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                System.out.println(file.getName());
                results.add(file.getName());
            }
        }
        for(String a : results){
            ImageCentering.centerImage(directory + "/" + a, a, outputDirectory, backgroundColor);
        }
    }

    private static void centerImage(String filePath, String fileName, String outputDirectory, int backgroundColor) throws IOException {
        File imageFile = new File(filePath);
        assert imageFile.exists();
        image = ImageIO.read(imageFile);
        calculateBounds(backgroundColor);
        center(backgroundColor);
        createOutputFile(outputDirectory, fileName);
    }

    private static void calculateBounds(int Backgroundcolor){
        top = Integer.MAX_VALUE;
        left = Integer.MAX_VALUE;
        right = Integer.MIN_VALUE;
        bottom = Integer.MIN_VALUE;
        if(image != null) {
            for (int i = 0; i < image.getHeight() - 1; i++) {
                for (int j = 0; j < image.getWidth() - 1; j++) {
                    if (image.getRGB(j, i) != Backgroundcolor) {
                        if (i < top) {
                            top = i;
                        } else if (i > bottom)
                            bottom = i;
                        else if (j < left)
                            left = j;
                        else if (j > right)
                            right = j;
                    }
                }
            }

        }
    }

    private static void center(int Backgroundcolor) {
        if(image != null) {
            int diffY = bottom - top;
            int diffX = right - left;
            int midY = image.getHeight() / 2;
            int midX = image.getWidth() / 2;
            BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    newImage.setRGB(j, i, Backgroundcolor);
                }
            }
            int x = midX - (diffX / 2);
            int y = midY - (diffY / 2);
            for (int i = top; i < bottom + 1; i++) {
                for (int j = left; j < right + 1; j++) {
                    newImage.setRGB(x, y, image.getRGB(j, i));
                    x++;
                }
                x = midX - (diffX / 2);
                y++;
            }
            image = newImage;
        }
    }

    private static void createOutputFile(String outputDirectory, String fileName) throws IOException {
        if(image != null) {
            File output = new File(outputDirectory + "\\" + fileName);
            System.out.println(outputDirectory + "\\" + fileName);
            ImageIO.write(image, "png", output);
        }
    }

    public static void main(String[] args) throws IOException {
        //ImageCentering.executeCenteringForEveryFile("C:\\Users\\ljung\\Desktop\\MLE\\Synthetic_Images\\old_foregrounds", "C:\\Users\\ljung\\Desktop\\testDirectory", -16777216);
    }

}
