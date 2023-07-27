package src.interfaceGraphique.composantsPrincipales;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

// Cette classe creee un Pannel modifie pour afficher les enfants d'un dossier sous forme de bouton 
public class PanneauExplorateur extends JPanel {

    private JTree localTree;
    private JButton backButton;
    private JButton refreshButton;
    private JPanel panneauPrincipale;
    private ModedTreeV2 localModedTree;
    private MenuNavigation localMenuNavigation;
    private JButton dernierBoutonClique = null; 


    // La classe entiere depend imperativement d'un ModedTree pour fonctionner
    // Des methodes de ModedTree ont ete employes au niveau de showChildren(DefaulltMutableTreeNode noeudParent)
    public PanneauExplorateur(ModedTreeV2 instanceArbre, MenuNavigation menu) {
        this.localModedTree = instanceArbre;
        this.localTree = instanceArbre.getTree();
        this.localMenuNavigation = menu;
        this.setLayout(new BorderLayout());
        this.refreshButton = new JButton("Actualiser");
        this.backButton = new JButton("Retour", new ImageIcon("assets/icons8-back-30-6.png"));
        this.panneauPrincipale = new JPanel();
        this.panneauPrincipale.setLayout(new GridLayout(0, 4, 10, 10)); // Utilisation d'un GridLayout
        this.setBackground(Color.white);
        JScrollPane scrollSurPanneauPrincipale = new JScrollPane(panneauPrincipale);
        scrollSurPanneauPrincipale.setPreferredSize(new Dimension(500, 500));

        // Ajout des composants au panneau principal
        this.add(backButton, BorderLayout.NORTH);
        this.add(scrollSurPanneauPrincipale, BorderLayout.CENTER);
        this.add(refreshButton, BorderLayout.SOUTH);
        setEcouteurs();
        this.setComponentPopupMenu(menu.getPopupMenu());
        System.out.println("PanneauExplorateur charge !");
    }

    private void showPopupMenu(MouseEvent e) {
        JPopupMenu popupMenu = this.getComponentPopupMenu();
        if (popupMenu != null) {
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }



    /* 
        Affiche l'integralite d'un dossier dans l'attribut "private JPanel panneauPrincipale", Panneau integrant l'ensemble des boutons
    */

    private void showChildren(DefaultMutableTreeNode noeudParent) {
        // On retire tout les composant du panneau avant tout : permet d'actualiser le panneau
        this.localModedTree.setSelectedNode(noeudParent);
        this.panneauPrincipale.removeAll();

        // Les enfants extraits du noeud parent seront dans un enumeration
        Enumeration<TreeNode> enfant = noeudParent.children();

        while (enfant.hasMoreElements()) {  
            DefaultMutableTreeNode noeudEnfant = (DefaultMutableTreeNode) enfant.nextElement();
            String nomExtrait = noeudEnfant.toString();
            String cheminEnfant = convertirEnCheminAbsolu(noeudEnfant);
            File fichierEnfant = new File(cheminEnfant);
            boolean estDossier = fichierEnfant.isDirectory();
            
            String parentNodePath = convertirEnCheminAbsolu(noeudParent);
            localMenuNavigation.updateParentNodePath(parentNodePath);

            JButton button = new ModedBouton(nomExtrait, estDossier); // Utiliser le paramètre estDossier pour l'instanciation de ModedBouton
            button.setPreferredSize(new Dimension(120, 80));
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setHorizontalTextPosition(SwingConstants.CENTER);

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        localModedTree.setDerniereSelection(cheminEnfant);
                        localModedTree.getDerniereSelection();
                        System.out.println(cheminEnfant);
                        System.out.println(localModedTree.getDerniereSelection());
                        showPopupMenu(e);
                    }
                }
            });


            // Écouteur pour détecter les clics sur les boutons de fichier/dossier
            button.addActionListener(new ActionListener() {
                private boolean isButtonClicked = false;

                @Override
                public void actionPerformed(ActionEvent e) {
                    // Vérifier si le bouton actuel est différent du dernier bouton cliqué
                    if (dernierBoutonClique != null && dernierBoutonClique != button) {
                        // Annuler le premier clic sur le premier bouton en restaurant sa couleur de fond à la normale (blanc)
                        dernierBoutonClique.setBackground(Color.WHITE);
                    }
                    if (isButtonClicked) {
                        if (estDossier)
                            // Afficher les enfants du nœud lorsqu'il est cliqué
                            showChildren(noeudEnfant);
                        else{
                            // Mise à jour de la dernière sélection dans ModedTreeV2
                            String filePath = convertirEnCheminAbsolu(noeudEnfant);
                            localModedTree.setDerniereSelection(filePath);
                            localModedTree.setSelectedNode(noeudEnfant);
                            System.out.println(filePath);
                            ouvrirFichier(cheminEnfant);
                        }
                    } else {
                        // Premier clic, changer la couleur du bouton en bleu
                        String filePath = convertirEnCheminAbsolu(noeudEnfant);
                            localModedTree.setDerniereSelection(filePath);
                            //modedTree.setSelectedNode(noeudEnfant);
                            // System.out.println(filePath);
                            System.out.println("!!!!".concat(localModedTree.getDerniereSelection()));
                        button.setBackground(Color.BLUE);
                    }
                    dernierBoutonClique = button;
                    // Inverser l'état du bouton pour le prochain clic
                    isButtonClicked = !isButtonClicked;
                }
            });

            
            // Ici a la fin de la boucle on a 1 bouton fonctionnel a alligner dans le GridLayout dans le content Panel
            this.panneauPrincipale.add(button);
        }
        panneauPrincipale.revalidate();
        panneauPrincipale.repaint();
    }

    private String convertirEnCheminAbsolu(DefaultMutableTreeNode noeud){
        return this.localModedTree.getAbsolutePath(new TreePath(noeud.getPath()));
    }

    // Bouton retour du panneau
    private void navigateBack() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) localTree.getLastSelectedPathComponent();
        if (selectedNode != null) {
            DefaultMutableTreeNode noeudParent = (DefaultMutableTreeNode) selectedNode.getParent();
            if (noeudParent != null) {
                localTree.setSelectionPath(new TreePath(noeudParent.getPath()));
                showChildren(noeudParent);
            }
        }
    }

    public static void ouvrirFichier(String cheminFichier) {
        if (Desktop.isDesktopSupported()) {
            try {
                File fichier = new File(cheminFichier);
                Desktop.getDesktop().open(fichier);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Le bureau n'est pas pris en charge sur cette plate-forme.");
        }
    }

    private void setEcouteurs(){
        // Ecouteur pour un clic droit de la souris
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }
        });

        this.refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               actualiserArbre(); // Appel de la méthode pour rafraîchir l'arbre
            }
        });

        // Écouteur pour détecter les sélections de nœuds dans l'arbre
        this.localTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) localTree.getLastSelectedPathComponent();
            // Si le noeud est vide c'est un fichier
            if (selectedNode != null) {
                showChildren(selectedNode);
            }
        });

        this.backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateBack();
            }
        });
    }
    public void actualiserArbre() {
        localModedTree.getRacine().removeAllChildren();
        localModedTree.listeurRepertoires(); // Mettre à jour l'arborescence dans ModedTreeV2
        DefaultTreeModel model = (DefaultTreeModel) localTree.getModel();
        DefaultMutableTreeNode racine = (DefaultMutableTreeNode) model.getRoot();
        showChildren(racine); // Mettre à jour l'affichage du panneau principal avec la nouvelle arborescence
    }
}