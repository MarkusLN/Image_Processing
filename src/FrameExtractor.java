import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

public class FrameExtractor {

    public static void extractFrames(String videoPath, String outputDirectory, int limit, String imageFormat) throws IOException, JCodecException {
        if(limit <= 0)
            limit = Integer.MAX_VALUE;
        File video = new File(videoPath);
        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(video));
        Picture frame;
        int i = 0; // To limit the number of images.
        while (null != (frame = grab.getNativeFrame()) && i < limit) {
            BufferedImage bufferedImage = AWTUtil.toBufferedImage(frame);
            ImageIO.write(bufferedImage, imageFormat, new File(outputDirectory + "/" +  i + "." + imageFormat));
            i++;
        }
    }

    public static void main(String[] args) throws IOException, JCodecException {
    }
}
