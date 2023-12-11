package src.interfacegraphique.composantsprincipales;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

// Bouton personnalise specialement pour l'affichage des fichiers sous forme de bouton depuis la classe PanneauExplorateur
public class ModedBouton extends JButton {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon iconeBouton;
    private String local_nomBouton;
    private boolean local_isDossier;

    public ModedBouton(String nomBouton, boolean isDossier) {
        super();
        this.local_nomBouton = nomBouton;
        this.local_isDossier = isDossier;
        setLayout(new BorderLayout());
        setIconeBouton();
        JLabel label = new JLabel(nomBouton);
        
        JLabel iconLabel = new JLabel(iconeBouton);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(iconLabel, BorderLayout.WEST);
        add(label, BorderLayout.CENTER);
        setFocusable(false);

        // Définition d'une taille personnalisée pour le bouton
        Dimension preferredSize = new Dimension(225, getPreferredSize().height);
        setPreferredSize(preferredSize);
    }

    public void setIconeBouton() {
        if (local_isDossier) {
            this.iconeBouton = new ImageIcon("assets/icons8-opened-folder-64-2.png");
        } else {
            // Extraction de l'extension du nom du fichier
            String extension = getFileExtension(local_nomBouton);
            switch (extension) {
                // case "txt":
                //     setIcon(new ImageIcon("assets/icons8-text-64.png"));
                //     break;
                // case "doc":
                // case "docx":
                //     setIcon(new ImageIcon("assets/icons8-word-64.png"));
                // break;
                // case "xls":
                // case "xlsx":
                //     setIcon(new ImageIcon("assets/icons8-excel-64.png"));
                // break;
                default:
                    this.iconeBouton = new ImageIcon("assets/icons8-symlink-file-64.png");
                break;
            }
        }
    }

    private String getFileExtension(String nomFichier) {
        int dernierPoint = nomFichier.lastIndexOf(".");
        if (dernierPoint != -1 && dernierPoint < nomFichier.length() - 1) {
            return nomFichier.substring(dernierPoint + 1).toLowerCase();
        }
        return "";
    }
}
