package encriptacion.views;

import encriptacion.cipher.Vigenere;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.datatransfer.*;

public class MainForm extends JFrame {

    private JTextArea txtEntrada;
    private JTextArea txtSalida;
    private JTextField txtClave;
    private JLabel lblEstado;
    private JLabel lblModo;
    private boolean modoCifrar = true;

    public MainForm() {
        AppTheme.apply();
        setUndecorated(true);
        initComponents();
    }

    private void initComponents() {
        setTitle("Cifrado Vigenère");
        setSize(820, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(AppTheme.BG_LIGHT);
        root.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER_COLOR, 1));

        root.add(buildTopBar(),    BorderLayout.NORTH);
        root.add(buildContent(),   BorderLayout.CENTER);
        root.add(buildStatusBar(), BorderLayout.SOUTH);

        setContentPane(root);
    }

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(AppTheme.BG_DARK);
        bar.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        bar.setPreferredSize(new Dimension(0, 48));

        final int[] drag = {0, 0};
        bar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                drag[0] = e.getX(); drag[1] = e.getY();
            }
        });
        bar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent e) {
                Point p = getLocation();
                setLocation(p.x + e.getX() - drag[0], p.y + e.getY() - drag[1]);
            }
        });

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        left.setOpaque(false);

        JLabel ico = new JLabel("🔐");
        ico.setFont(new Font("SansSerif", Font.PLAIN, 18));

        JLabel titulo = new JLabel("Cifrado Vigenère");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 15));
        titulo.setForeground(AppTheme.TEXT_WHITE);

        left.add(ico);
        left.add(titulo);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setOpaque(false);
        Color btnBg = new Color(51, 65, 85);
        JButton btnMin   = AppTheme.winBtn(0, btnBg);
        JButton btnMax   = AppTheme.winBtn(1, btnBg);
        JButton btnClose = AppTheme.winBtn(2, btnBg);

        btnMin.addActionListener(e -> setState(Frame.ICONIFIED));
        btnMax.addActionListener(e -> {
            if ((getExtendedState() & Frame.MAXIMIZED_BOTH) != 0)
                setExtendedState(Frame.NORMAL);
            else
                setExtendedState(Frame.MAXIMIZED_BOTH);
        });
        btnClose.addActionListener(e -> dispose());

        right.add(btnMin);
        right.add(btnMax);
        right.add(btnClose);

        bar.add(left,  BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout(0, 0));
        content.setBackground(AppTheme.BG_LIGHT);
        content.setBorder(BorderFactory.createEmptyBorder(24, 28, 16, 28));

        // Header de modo
        JPanel modoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        modoPanel.setOpaque(false);

        lblModo = new JLabel("MODO: CIFRAR");
        lblModo.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblModo.setForeground(AppTheme.ACCENT);

        JButton btnToggle = AppTheme.btnSecondary("Cambiar modo");
        btnToggle.setFont(AppTheme.FONT_SMALL);
        btnToggle.addActionListener(e -> toggleModo());

        modoPanel.add(lblModo);
        modoPanel.add(btnToggle);
        content.add(modoPanel, BorderLayout.NORTH);

        // Panel central: entrada + salida
        JPanel center = new JPanel(new GridLayout(1, 2, 16, 0));
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(16, 0, 16, 0));

        center.add(buildTextPanel("Texto de entrada", true));
        center.add(buildTextPanel("Resultado", false));
        content.add(center, BorderLayout.CENTER);

        // Panel inferior: clave + botones
        content.add(buildBottomPanel(), BorderLayout.SOUTH);
        return content;
    }

    private JPanel buildTextPanel(String titulo, boolean esEntrada) {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setOpaque(false);

        JLabel lbl = new JLabel(titulo.toUpperCase());
        lbl.setFont(new Font("SansSerif", Font.BOLD, 11));
        lbl.setForeground(AppTheme.TEXT_SECONDARY);

        JTextArea area = new JTextArea();
        area.setFont(AppTheme.FONT_MONO);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBackground(AppTheme.BG_WHITE);
        area.setForeground(AppTheme.TEXT_PRIMARY);
        area.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        area.setCaretColor(AppTheme.ACCENT);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER_COLOR, 1, true));
        scroll.getViewport().setBackground(AppTheme.BG_WHITE);

        if (esEntrada) {
            txtEntrada = area;
        } else {
            txtSalida = area;
            txtSalida.setEditable(false);
            txtSalida.setBackground(new Color(250, 250, 255));
        }

        // Botón copiar (solo en resultado)
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(lbl, BorderLayout.WEST);
        if (!esEntrada) {
            JButton btnCopiar = new JButton("Copiar");
            btnCopiar.setFont(AppTheme.FONT_SMALL);
            btnCopiar.setBackground(AppTheme.BG_WHITE);
            btnCopiar.setForeground(AppTheme.ACCENT);
            btnCopiar.setFocusPainted(false);
            btnCopiar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.ACCENT, 1, true),
                BorderFactory.createEmptyBorder(3, 10, 3, 10)
            ));
            btnCopiar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnCopiar.setOpaque(true);
            btnCopiar.addActionListener(e -> copiarResultado());
            header.add(btnCopiar, BorderLayout.EAST);
        }

        p.add(header, BorderLayout.NORTH);
        p.add(scroll,  BorderLayout.CENTER);
        return p;
    }

    private JPanel buildBottomPanel() {
        JPanel p = new JPanel(new BorderLayout(16, 0));
        p.setOpaque(false);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, AppTheme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(16, 0, 0, 0)
        ));

        // Clave
        JPanel clavePanel = new JPanel(new BorderLayout(8, 0));
        clavePanel.setOpaque(false);

        JLabel lblClave = new JLabel("Clave:");
        lblClave.setFont(AppTheme.FONT_BOLD);
        lblClave.setForeground(AppTheme.TEXT_PRIMARY);
        lblClave.setPreferredSize(new Dimension(50, 0));

        txtClave = new JTextField();
        txtClave.setFont(AppTheme.FONT_MONO);
        txtClave.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppTheme.BORDER_COLOR, 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtClave.setBackground(AppTheme.BG_WHITE);

        clavePanel.add(lblClave, BorderLayout.WEST);
        clavePanel.add(txtClave, BorderLayout.CENTER);

        // Botones
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setOpaque(false);

        JButton btnLimpiar   = AppTheme.btnSecondary("Limpiar");
        JButton btnProcesar  = AppTheme.btnPrimary("Cifrar");

        btnLimpiar.addActionListener(e -> limpiar());
        btnProcesar.addActionListener(e -> procesar());

        // Actualizar texto del botón al cambiar modo
        this.addPropertyChangeListener("modoCifrar", evt ->
            btnProcesar.setText(modoCifrar ? "Cifrar" : "Descifrar")
        );

        btns.add(btnLimpiar);
        btns.add(btnProcesar);

        // Guardar referencia para actualizar texto
        this.btnProcesarRef = btnProcesar;

        p.add(clavePanel, BorderLayout.CENTER);
        p.add(btns,        BorderLayout.EAST);
        return p;
    }

    private JButton btnProcesarRef;

    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 6));
        bar.setBackground(new Color(241, 245, 249));
        bar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, AppTheme.BORDER_COLOR));

        lblEstado = new JLabel("Listo.");
        lblEstado.setFont(AppTheme.FONT_SMALL);
        lblEstado.setForeground(AppTheme.TEXT_SECONDARY);

        JLabel lblAlf = new JLabel("Alfabeto: " + Vigenere.ALFABETO.length() + " caracteres");
        lblAlf.setFont(AppTheme.FONT_SMALL);
        lblAlf.setForeground(AppTheme.MUTED);

        bar.add(lblEstado);
        bar.add(new JSeparator(JSeparator.VERTICAL));
        bar.add(lblAlf);
        return bar;
    }

    private void toggleModo() {
        modoCifrar = !modoCifrar;
        lblModo.setText("MODO: " + (modoCifrar ? "CIFRAR" : "DESCIFRAR"));
        lblModo.setForeground(modoCifrar ? AppTheme.ACCENT : AppTheme.SUCCESS);
        if (btnProcesarRef != null)
            btnProcesarRef.setText(modoCifrar ? "Cifrar" : "Descifrar");
        limpiar();
    }

    private void procesar() {
        String texto = txtEntrada.getText();
        String clave = txtClave.getText().trim();

        if (texto.isEmpty()) {
            estado("Escribe el texto de entrada.", AppTheme.WARNING);
            return;
        }
        if (clave.isEmpty()) {
            estado("Ingresa la clave.", AppTheme.WARNING);
            return;
        }

        try {
            String resultado = modoCifrar
                ? Vigenere.cifrar(texto, clave)
                : Vigenere.descifrar(texto, clave);
            txtSalida.setText(resultado);
            estado("Completado. " + resultado.length() + " caracteres procesados.", AppTheme.SUCCESS);
        } catch (Exception ex) {
            estado("Error: " + ex.getMessage(), AppTheme.DANGER);
        }
    }

    private void limpiar() {
        txtEntrada.setText("");
        txtSalida.setText("");
        txtClave.setText("");
        estado("Listo.", AppTheme.TEXT_SECONDARY);
    }

    private void copiarResultado() {
        String texto = txtSalida.getText();
        if (texto.isEmpty()) { estado("No hay resultado para copiar.", AppTheme.WARNING); return; }
        Toolkit.getDefaultToolkit().getSystemClipboard()
            .setContents(new StringSelection(texto), null);
        estado("Copiado al portapapeles.", AppTheme.SUCCESS);
    }

    private void estado(String msg, Color color) {
        lblEstado.setText(msg);
        lblEstado.setForeground(color);
    }
}
