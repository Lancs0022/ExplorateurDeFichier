package src.interfaceGraphique.composantsPrincipales;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;

// Classe permettant d'afficher les proprietes d'un element. Elle est accessible depuis les proprietes du MenuNavigation
public class FenetrePropriete extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel nameLabel;
    private JTextField champsChemin;
    private JTextField champParent;
    private JTextField champTaille;
    private JTextField champDate;
    private JTextField champType;

    public FenetrePropriete(String filePath) {
        setTitle("Propriétés");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Initialisations des champs qui stockera les informations
        nameLabel = new JLabel();
        champsChemin = new JTextField();
        champParent = new JTextField();
        champTaille = new JTextField();
        champDate = new JTextField();
        champType = new JTextField();

        // Mise en place des champs dans la fenetre de propriete
        add(new JLabel("Nom :"));
        add(nameLabel);
        add(new JLabel("Chemin :"));
        add(champsChemin);
        add(new JLabel("Dossier parent :"));
        add(champParent);
        add(new JLabel("Taille :"));
        add(champTaille);
        add(new JLabel("Date de modification :"));
        add(champDate);
        add(new JLabel("Type :"));
        add(champType);

        this.fillProperties(filePath);
        System.out.println("Appel du fenetre de propriete");
    }

    // S'occupe de remplir les proprietes
    public void fillProperties(String filePath) {
        File file = new File(filePath);

        nameLabel.setText(file.getName());
        champsChemin.setText(file.getAbsolutePath());
        champParent.setText(file.getParent());
        champTaille.setText(formatSize(file.length()));
        champDate.setText(formatDate(file.lastModified()));
        champType.setText(getFileType(file));

        setVisible(true);
    }

    // Formate la taille obtenue d'un fichier en octets, ko ou Mo pour les plus grands
    private String formatSize(long taille) {
        if (taille < 1024) {
            return taille + " octets";
        } else if (taille < 1024 * 1024) {
            return String.format("%.2f Ko", taille / 1024.0);
        } else {
            return String.format("%.2f Mo", taille / (1024.0 * 1024));
        }
    }

    // Utilise l'objet SimpleDateFormat pour filtrer une date de type long
    private String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }

    // Recupere l'extension d'un fichier apres le dernier point de son nom
    private String getFileType(File file) {
        if (file.isDirectory()) {
            return "Dossier";
        } else {
            String nomFichier = file.getName();
            int dernierPoint = nomFichier.lastIndexOf(".");
            if (dernierPoint != -1 && dernierPoint < nomFichier.length() - 1) {
                return nomFichier.substring(dernierPoint + 1).toUpperCase() + " File";
            } else {
                return "Fichier";
            }
        }
    }
}
