package com.BuilderBadge.HHframeGenerator.service;

import com.BuilderBadge.HHframeGenerator.dto.BadgeRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Component
public class BadgeRenderer {

    private static final int W = 1080, H = 1350;
    private static final int FRAME_X = 240, FRAME_Y = 300, FRAME_SIZE = 600, FRAME_RADIUS = 34;

    private static final Color INK = new Color(0x0B, 0x1E, 0x2D);
    private static final Color TEAL = new Color(0x12, 0x4B, 0x4F);
    private static final Color CORAL = new Color(0xFF, 0x6B, 0x4A);
    private static final Color GOLD = new Color(0xF4, 0xB9, 0x42);
    private static final Color SAND = new Color(0xF6, 0xEF, 0xE3);
    private static final Color PALM = new Color(0x2F, 0x6D, 0x5C);

    private Font displayBold;   // Space Grotesk Bold
    private Font monoBold;      // JetBrains Mono Bold
    private Font monoRegular;   // JetBrains Mono Regular

    public BadgeRenderer() {
        displayBold = loadFont("/fonts/SpaceGrotesk-Bold.ttf", Font.BOLD, 56f);
        monoBold = loadFont("/fonts/JetBrainsMono-Bold.ttf", Font.BOLD, 26f);
        monoRegular = loadFont("/fonts/JetBrainsMono-Regular.ttf", Font.PLAIN, 22f);
    }

    private Font loadFont(String classpath, int fallbackStyle, float size) {
        try (InputStream is = new ClassPathResource(classpath.substring(1)).getInputStream()) {
            Font f = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(f);
            return f.deriveFont(size);
        } catch (Exception e) {
            // Font file missing — fall back so rendering still works during the demo
            return new Font("SansSerif", fallbackStyle, (int) size);
        }
    }

    public BufferedImage render(BadgeRequest request) throws IOException {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        drawBackground(g);
        drawDotGrid(g);
        drawWaves(g);
        drawHeader(g);
        drawPhoto(g, request.getPhoto());
        drawName(g, request.getName());
        drawRolePill(g, request.getRole());
        drawTerminal(g, request.getTitle());
        drawHashtag(g);

        g.dispose();
        return img;
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(INK);
        g.fillRect(0, 0, W, H);

        RadialGradientPaint glow = new RadialGradientPaint(
                new Point(W / 2, (int) (H * 0.4)), W,
                new float[]{0f, 1f},
                new Color[]{new Color(18, 75, 79, 140), new Color(11, 30, 45, 0)}
        );
        g.setPaint(glow);
        g.fillRect(0, 0, W, H);
    }

    private void drawDotGrid(Graphics2D g) {
        g.setColor(new Color(246, 239, 227, 15));
        for (int y = 40; y < H - 40; y += 34) {
            for (int x = 40; x < W - 40; x += 34) {
                g.fillOval(x, y, 3, 3);
            }
        }
    }

    private void drawWaves(Graphics2D g) {
        Polygon wave1 = new Polygon();
        wave1.addPoint(0, H - 180);
        for (int x = 0; x <= W; x += 20) {
            int yy = (int) (H - 180 + Math.sin((x / (double) W) * Math.PI * 2.2) * 16);
            wave1.addPoint(x, yy);
        }
        wave1.addPoint(W, H);
        wave1.addPoint(0, H);
        g.setColor(new Color(47, 109, 92, 90));
        g.fillPolygon(wave1);

        Polygon wave2 = new Polygon();
        wave2.addPoint(0, H - 120);
        for (int x = 0; x <= W; x += 20) {
            int yy = (int) (H - 120 + Math.sin((x / (double) W) * Math.PI * 2.6 + 1.4) * 20);
            wave2.addPoint(x, yy);
        }
        wave2.addPoint(W, H);
        wave2.addPoint(0, H);
        g.setColor(TEAL);
        g.fillPolygon(wave2);
    }

    private void drawHeader(Graphics2D g) {
        g.setFont(monoBold.deriveFont(24f));
        g.setColor(CORAL);
        drawCentered(g, "H A C K E R   H O U S E", W / 2, 92);

        g.setFont(displayBold.deriveFont(76f));
        g.setColor(SAND);
        drawCentered(g, "GOA 2026", W / 2, 172);

        g.setFont(monoRegular.deriveFont(18f));
        g.setColor(new Color(246, 239, 227, 120));
        drawCentered(g, "15.2993°N, 74.1240°E · BUILDER BADGE", W / 2, 206);
    }

    private void drawPhoto(Graphics2D g, MultipartFile photo) throws IOException {
        RoundRectangle2D frame = new RoundRectangle2D.Double(
                FRAME_X, FRAME_Y, FRAME_SIZE, FRAME_SIZE, FRAME_RADIUS, FRAME_RADIUS);

        // glow behind the frame
        g.setColor(new Color(255, 107, 74, 90));
        g.fill(new RoundRectangle2D.Double(FRAME_X - 6, FRAME_Y - 6,
                FRAME_SIZE + 12, FRAME_SIZE + 12, FRAME_RADIUS + 4, FRAME_RADIUS + 4));

        Shape oldClip = g.getClip();
        g.setClip(frame);

        if (photo != null && !photo.isEmpty()) {
            BufferedImage src = ImageIO.read(photo.getInputStream());
            if (src != null) {
                drawCoverFit(g, src, FRAME_X, FRAME_Y, FRAME_SIZE, FRAME_SIZE);
            } else {
                g.setColor(new Color(24, 53, 69));
                g.fillRect(FRAME_X, FRAME_Y, FRAME_SIZE, FRAME_SIZE);
            }
        } else {
            g.setColor(new Color(24, 53, 69));
            g.fillRect(FRAME_X, FRAME_Y, FRAME_SIZE, FRAME_SIZE);
        }

        g.setClip(oldClip);

        g.setColor(SAND);
        g.setStroke(new BasicStroke(6));
        g.draw(frame);
    }

    /** Center-crop "cover" fit — mirrors the canvas demo's default framing. */
    private void drawCoverFit(Graphics2D g, BufferedImage src, int x, int y, int w, int h) {
        double srcRatio = (double) src.getWidth() / src.getHeight();
        double targetRatio = (double) w / h;

        int drawW, drawH, offX = 0, offY = 0;
        if (srcRatio > targetRatio) {
            drawH = h;
            drawW = (int) (h * srcRatio);
            offX = (drawW - w) / 2;
        } else {
            drawW = w;
            drawH = (int) (w / srcRatio);
            offY = (drawH - h) / 2;
        }
        g.drawImage(src, x - offX, y - offY, drawW, drawH, null);
    }

    private void drawName(Graphics2D g, String name) {
        String text = (name == null || name.isBlank()) ? "Your Name" : name;
        g.setColor(SAND);
        Font f = fitFont(g, displayBold, text, W - 160, 56f);
        g.setFont(f);
        drawCentered(g, text, W / 2, FRAME_Y + FRAME_SIZE + 82);
    }

    private void drawRolePill(Graphics2D g, String role) {
        if (role == null || role.isBlank()) return;
        String label = role.toUpperCase();
        g.setFont(monoBold.deriveFont(24f));
        FontMetrics fm = g.getFontMetrics();
        int textW = fm.stringWidth(label);
        int padX = 26, pillW = textW + padX * 2, pillH = 50;
        int pillX = W / 2 - pillW / 2, pillY = FRAME_Y + FRAME_SIZE + 108;

        g.setColor(TEAL);
        g.fill(new RoundRectangle2D.Double(pillX, pillY, pillW, pillH, pillH, pillH));
        g.setColor(GOLD);
        drawCentered(g, label, W / 2, pillY + pillH / 2 + fm.getAscent() / 2 - 4);
    }

    private void drawTerminal(Graphics2D g, String title) {
        boolean hasRole = true; // pill spacing offset kept simple/consistent
        int termY = FRAME_Y + FRAME_SIZE + 178;
        int termH = 128, termX = 90, termW = W - 180;

        RoundRectangle2D term = new RoundRectangle2D.Double(termX, termY, termW, termH, 16, 16);
        g.setColor(new Color(0x08, 0x17, 0x22));
        g.fill(term);
        g.setColor(new Color(246, 239, 227, 30));
        g.setStroke(new BasicStroke(2));
        g.draw(term);

        Color[] dots = {CORAL, GOLD, PALM};
        for (int i = 0; i < 3; i++) {
            g.setColor(dots[i]);
            g.fillOval(termX + 24 + i * 26, termY + 22, 14, 14);
        }

        g.setFont(monoRegular.deriveFont(24f));
        g.setColor(new Color(246, 239, 227, 140));
        g.drawString("$ whoami", termX + 30, termY + 62);

        String titleText = "> " + ((title == null || title.isBlank()) ? "Builder" : title);
        Font f = fitFont(g, monoBold, titleText, termW - 100, 28f);
        g.setFont(f);
        g.setColor(GOLD);
        g.drawString(titleText, termX + 30, termY + 102);
    }

    private void drawHashtag(Graphics2D g) {
        g.setFont(monoBold.deriveFont(28f));
        g.setColor(CORAL);
        drawCentered(g, "#FrameInGoa", W / 2, H - 42);
    }

    // ---------- helpers ----------

    private void drawCentered(Graphics2D g, String text, int cx, int y) {
        FontMetrics fm = g.getFontMetrics();
        int x = cx - fm.stringWidth(text) / 2;
        g.drawString(text, x, y);
    }

    /** Shrinks font size until the text fits maxWidth, so long names/titles don't overflow. */
    private Font fitFont(Graphics2D g, Font base, String text, int maxWidth, float startSize) {
        float size = startSize;
        Font f = base.deriveFont(size);
        g.setFont(f);
        FontMetrics fm = g.getFontMetrics(f);
        while (fm.stringWidth(text) > maxWidth && size > 18f) {
            size -= 2f;
            f = base.deriveFont(size);
            fm = g.getFontMetrics(f);
        }
        return f;
    }
}