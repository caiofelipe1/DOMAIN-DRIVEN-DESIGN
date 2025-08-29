package util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IconLoader {

    // cache por (path+size) para não reescalar sempre
    private static final Map<String, ImageIcon> CACHE = new ConcurrentHashMap<>();

    /** Carrega do classpath ("/icons/..") e redimensiona para size (ex.: 20 ou 24). */
    public static ImageIcon loadScaled(String resourcePath, int size) {
        String key = resourcePath + "@" + size;
        if (CACHE.containsKey(key)) return CACHE.get(key);

        String cp = resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath;
        URL url = IconLoader.class.getResource(cp);
        if (url == null) {
            System.err.println("[IconLoader] Recurso não encontrado: " + cp);
            // fallback 1x1: evita NPE
            ImageIcon fallback = new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
            CACHE.put(key, fallback);
            return fallback;
        }

        ImageIcon base = new ImageIcon(url);
        Image scaled = base.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaled);
        CACHE.put(key, icon);
        return icon;
    }

    /** Helper para criar JButton já com ícone escalado + ajustes visuais. */
    public static JButton button(String text, String resourcePath, int iconSize, Color bg, Color fg) {
        JButton b = new JButton(text, loadScaled(resourcePath, iconSize));
        b.setFocusPainted(false);
        if (bg != null) b.setBackground(bg);
        if (fg != null) b.setForeground(fg);
        b.setMargin(new Insets(8, 12, 8, 12));   // respiro
        b.setIconTextGap(8);                     // espaço entre ícone e texto
        b.setHorizontalAlignment(SwingConstants.CENTER);
        b.setVerticalAlignment(SwingConstants.CENTER);
        // tamanho mínimo simpático; ajuste se quiser
        b.setPreferredSize(new Dimension(Math.max(120, text.length() * 9 + 40), 36));
        return b;
    }
}
