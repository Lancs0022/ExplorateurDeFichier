package src.interfaceGraphique.composantsPrincipales;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class FenetrePropriete extends JFrame {

    private JLabel nameLabel;
    private JTextField pathField;
    private JTextField parentField;
    private JTextField sizeField;
    private JTextField dateField;
    private JTextField typeField;

    public FenetrePropriete(String filePath) {
        // this.localModedTree = instanceTree;
        setTitle("Propriétés");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        nameLabel = new JLabel();
        pathField = new JTextField();
        parentField = new JTextField();
        sizeField = new JTextField();
        dateField = new JTextField();
        typeField = new JTextField();

        add(new JLabel("Nom :"));
        add(nameLabel);
        add(new JLabel("Chemin :"));
        add(pathField);
        add(new JLabel("Dossier parent :"));
        add(parentField);
        add(new JLabel("Taille :"));
        add(sizeField);
        add(new JLabel("Date de modification :"));
        add(dateField);
        add(new JLabel("Type :"));
        add(typeField);
        this.fillProperties(filePath);
    }

    public void fillProperties(String filePath) {
        // filePath = this.localModedTree.getDerniereSelection();
        
        File file = new File(filePath);

        nameLabel.setText(file.getName());
        pathField.setText(file.getAbsolutePath());
        parentField.setText(file.getParent());
        sizeField.setText(formatSize(file.length()));
        dateField.setText(formatDate(file.lastModified()));
        typeField.setText(getFileType(file));

        setVisible(true);
    }

    private String formatSize(long size) {
        if (size < 1024) {
            return size + " octets";
        } else if (size < 1024 * 1024) {
            return String.format("%.2f Ko", size / 1024.0);
        } else {
            return String.format("%.2f Mo", size / (1024.0 * 1024));
        }
    }

    private String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }

    private String getFileType(File file) {
        if (file.isDirectory()) {
            return "Dossier";
        } else {
            String fileName = file.getName();
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
                return fileName.substring(dotIndex + 1).toUpperCase() + " File";
            } else {
                return "Fichier";
            }
        }
    }
}
