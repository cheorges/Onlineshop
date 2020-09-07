package ch.zotteljedi.onlineshop.helper;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Photo {

   public final static int MAX_IMAGE_LENGTH = 400;

   public static byte[] scale(byte[] photo) throws IOException {
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(photo);
      BufferedImage originalBufferedImage = ImageIO.read(byteArrayInputStream);

      double originalWidth = originalBufferedImage.getWidth();
      double originalHeight = originalBufferedImage.getHeight();
      double relevantLength = Math.max(originalWidth, originalHeight);

      double transformationScale = MAX_IMAGE_LENGTH / relevantLength;
      int width = (int) Math.round( transformationScale * originalWidth );
      int height = (int) Math.round( transformationScale * originalHeight );

      BufferedImage resizedBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      Graphics2D g2d = resizedBufferedImage.createGraphics();
      g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
      AffineTransform affineTransform = AffineTransform.getScaleInstance(transformationScale, transformationScale);
      g2d.drawRenderedImage(originalBufferedImage, affineTransform);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(resizedBufferedImage, "PNG", baos);
      return baos.toByteArray();
   }
}
