package encriptacion.views;

import javax.swing.*;
import java.awt.*;

public class AppTheme {

    public static final Color BG_DARK      = new Color(15,  23,  42);
    public static final Color BG_MEDIUM    = new Color(30,  41,  59);
    public static final Color BG_LIGHT     = new Color(248, 250, 252);
    public static final Color BG_WHITE     = Color.WHITE;

    public static final Color ACCENT       = new Color(99,  102, 241);
    public static final Color ACCENT_HOVER = new Color(79,  70,  229);
    public static final Color SUCCESS      = new Color(34,  197,  94);
    public static final Color DANGER       = new Color(239,  68,  68);
    public static final Color WARNING      = new Color(234, 179,   8);
    public static final Color MUTED        = new Color(148, 163, 184);

    public static final Color TEXT_PRIMARY   = new Color(15,  23,  42);
    public static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    public static final Color TEXT_WHITE     = Color.WHITE;
    public static final Color BORDER_COLOR   = new Color(226, 232, 240);

    public static final Font FONT_TITLE    = new Font("SansSerif", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE = new Font("SansSerif", Font.BOLD, 15);
    public static final Font FONT_BODY     = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_SMALL    = new Font("SansSerif", Font.PLAIN, 12);
    public static final Font FONT_BOLD     = new Font("SansSerif", Font.BOLD,  13);
    public static final Font FONT_MONO     = new Font("Monospaced", Font.PLAIN, 13);

    public static void apply() {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        UIManager.put("Panel.background",            BG_LIGHT);
        UIManager.put("OptionPane.background",       BG_WHITE);
        UIManager.put("OptionPane.messageForeground", TEXT_PRIMARY);
        UIManager.put("Button.font",     FONT_BODY);
        UIManager.put("Label.font",      FONT_BODY);
        UIManager.put("TextField.font",  FONT_BODY);
        UIManager.put("TextArea.font",   FONT_MONO);
        UIManager.put("ComboBox.font",   FONT_BODY);
    }

    public static JButton btnPrimary(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BOLD);
        b.setBackground(ACCENT);
        b.setForeground(TEXT_WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(ACCENT_HOVER); }
            public void mouseExited (java.awt.event.MouseEvent e) { b.setBackground(ACCENT); }
        });
        return b;
    }

    public static JButton btnSecondary(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BODY);
        b.setBackground(BG_WHITE);
        b.setForeground(TEXT_PRIMARY);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        return b;
    }

    public static JButton btnSuccess(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BOLD);
        b.setBackground(new Color(240, 253, 244));
        b.setForeground(new Color(21, 128, 61));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(134, 239, 172), 1, true),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        return b;
    }

    public static JButton btnDanger(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BOLD);
        b.setBackground(new Color(254, 242, 242));
        b.setForeground(DANGER);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(252, 165, 165), 1, true),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        return b;
    }

    public static JButton winBtn(int type, Color bg) {
        JButton b = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int cx = getWidth() / 2, cy = getHeight() / 2;
                if (type == 0) {
                    g2.drawLine(cx - 6, cy + 3, cx + 6, cy + 3);
                } else if (type == 1) {
                    g2.drawRect(cx - 6, cy - 6, 12, 12);
                } else {
                    g2.drawLine(cx - 5, cy - 5, cx + 5, cy + 5);
                    g2.drawLine(cx + 5, cy - 5, cx - 5, cy + 5);
                }
                g2.dispose();
            }
        };
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setPreferredSize(new Dimension(40, 48));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Color hover = type == 2 ? new Color(220, 38, 38) : new Color(71, 85, 105);
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(hover); b.repaint(); }
            public void mouseExited (java.awt.event.MouseEvent e) { b.setBackground(bg);    b.repaint(); }
        });
        return b;
    }
}
