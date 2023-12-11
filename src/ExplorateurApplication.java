package src;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import src.interfacegraphique.Fenetre;

/* *************************************  Presentation global du projet ********************************************************************** *\
 *                                                                                                                                               
 *   Ce projet represente un application de gestionnaire de fichier fonctionnel. 
 * 
 *   Un dossier "asset" sert a collectionner les icones personnalises utilises dans le projet
 *   Un dossier "bin" a ete creee pour le fichier ".jar" executable de l'application
 * 
 *   Notes et Astuce : 
 *      La creation de l'arborescence de fichier demande beaucoup de temps et retarde l'affichage de l'interface graphique du programme.
 *      Veuillez a surveiller la console de l'application pour voir l'etat actuel du programme. 
 *      Un limiteur est place a la ligne 97 de la classe ModedTreeV2 pour accelerer l'execution de l'application et peut etre modifie a volonte
 * 
 *                                     !!! Veuillez utiliser un JDK recent pour utiliser l'application !!!
 * 
 ********************************************************************************************************************************************* */

class ExplorateurApplication {
    public static void main(String[] args) {
        try {
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            // UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
		}

        // Lancement d'une instance de fenetre
        new Fenetre();
    }
}