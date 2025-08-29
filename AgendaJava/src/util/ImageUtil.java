package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageUtil {

    public static void copiarRedimensionandoQuadrado(File origem, File destino, int lado) throws Exception {
        BufferedImage src = ImageIO.read(origem);
        if (src == null) throw new IOException("Formato de imagem não suportado.");

        // cortar para quadrado central
        int min = Math.min(src.getWidth(), src.getHeight());
        int x = (src.getWidth() - min) / 2;
        int y = (src.getHeight() - min) / 2;
        BufferedImage square = src.getSubimage(x, y, min, min);

        BufferedImage scaled = new BufferedImage(lado, lado, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaled.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(square, 0, 0, lado, lado, null);
        g.dispose();

        ImageIO.write(scaled, "png", destino);
    }
}
