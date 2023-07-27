package src;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import src.interfaceGraphique.Fenetre;

/* *************************************  Presentation global du projet ********************************************************************** *\
 *                                                                                                                                               
 *   Ce projet represente un application de gestionnaire de fichier fonctionnel. 
 *   Les fichiers sources sont structures comme suit :
 * 
 *   	- Package "src" :                                        :  Contenant l'integralites des fichiers ".class" et ".java"
 *   	  \  -> ExplorateurApplication.java                      :  point d'entree du projet
 *   	  \  -  Package "src.interfaceGraphique"                 :  Contenant les fichiers principales relative au projet
 * 		      \\  -> Fenetre.java;                               :  creation de la fenetre et mise en place des composants
 * 			  \\  -  Package "src.composantsPrincipales"         :  Contient tous les classes personnalises indispensables
 *                  \\\  -> ModedTreeV2.java;                    :  cree un objet Jtree pour l'arborescence et
 *                                                                      definit des methodes pour interagir avec 
 *                                                                      (Element principale du projet et aussi le plus lourd a charger)
 *                  \\\  -> PanneauExplorateur.java;             :  extension qui perment d'afficher l'arborescence de facon detaille
 *                  \\\  -> MenuNavigation.java;                 :  tout ce qui est au nord de la fenetre
 *                  \\\  -> ModedBouton.java;                    :  bouton personnalise pour acceuillir les fichiers et dossiers du projet
 *                  \\\  -> FenetrePropriete.java;               :  cree une fenetre affichant les proprietes d'un element 
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
 ********************************************************************************************************************************************** */

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