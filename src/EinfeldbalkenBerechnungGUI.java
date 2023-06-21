import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class EinfeldbalkenBerechnungGUI extends JFrame {
private JTextField lField;
private JTextField pField;
private JTextArea resultArea;
public EinfeldbalkenBerechnungGUI() {
    // Setzen des Look and Feel für den Dark Mode
    try {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (Exception e) {
        e.printStackTrace();
    }

    // GUI-Komponenten erstellen
    JLabel lLabel = new JLabel("Balkenlänge (l):");
    lLabel.setFont(new Font("Arial", Font.BOLD, 14));
    lField = new JTextField(10);

    JLabel pLabel = new JLabel("Belastung (p):");
    pLabel.setFont(new Font("Arial", Font.BOLD, 14));
    pField = new JTextField(10);

    JButton berechnenButton = new JButton("Berechnen");
    berechnenButton.setFont(new Font("Arial", Font.BOLD, 14));
    berechnenButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            berechnung();
        }
    });

    resultArea = new JTextArea(8, 30);
    resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
    resultArea.setEditable(false);
    resultArea.setBackground(Color.DARK_GRAY);
    resultArea.setForeground(Color.WHITE);

    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setBorder(null);

    // Panel erstellen und GUI-Komponenten hinzufügen
    JPanel panel = new JPanel();
    panel.setBackground(Color.DARK_GRAY);
    panel.add(lLabel);
    panel.add(lField);
    panel.add(pLabel);
    panel.add(pField);
    panel.add(berechnenButton);
    panel.add(scrollPane);

    // JFrame-Einstellungen festlegen
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Einfeldbalken Berechnung");
    setResizable(false);
    add(panel);
    pack();
    setLocationRelativeTo(null);
}

private void berechnung() {
    try {
        // Eingabewerte aus den Textfeldern abrufen und in Double konvertieren
        double l = Double.parseDouble(lField.getText());
        double p = Double.parseDouble(pField.getText());

        // Auflagerkräfte und Schnittgrößen berechnen
        double ra = p * l / 2;
        double rb = p * l / 2;
        double mx = p * l / 2;
        double qx = p;

        // Ergebnisse im Textbereich anzeigen
        resultArea.setText("Auflagerkräfte:\n");
        resultArea.append("Ra: " + ra + "\n");
        resultArea.append("Rb: " + rb + "\n");
        resultArea.append("Schnittgrößen:\n");
        resultArea.append("Mx: " + mx + "\n");
        resultArea.append("Qx: " + qx + "\n");

        // Ergebnisse in eine Textdatei exportieren
        exportErgebnisse(ra, rb, mx, qx);
    } catch (NumberFormatException ex) {
        // Fehlerbehandlung für ungültige Eingabe
        JOptionPane.showMessageDialog(this, "Ungültige Eingabe. Bitte geben Sie numerische Werte ein.",
                "Fehler", JOptionPane.ERROR_MESSAGE);
    }
}

private void exportErgebnisse(double ra, double rb, double mx, double qx) {
    try {
        FileWriter writer = new FileWriter("ergebnisse.txt");
        writer.write("Auflagerkräfte:\n");
        writer.write("Ra: " + ra + "\n");
        writer.write("Rb: " + rb + "\n");
        writer.write("Schnittgrößen:\n");
        writer.write("Mx: " + mx + "\n");
        writer.write("Qx: " + qx + "\n");
        writer.close();
        System.out.println("Ergebnisse wurden in die Datei ergebnisse.txt exportiert.");
    } catch (IOException e) {
        // Fehlerbehandlung für Dateizugriffsfehler
        System.out.println("Fehler beim Export der Ergebnisse.");
    }
}

public static void main(String[] args) {
    // GUI starten
    EinfeldbalkenBerechnungGUI gui = new EinfeldbalkenBerechnungGUI();
    gui.setVisible(true);
}
}