package Trash;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

// Cette classe creee un Pannel modifie pour afficher les enfants d'un dossier sous forme de bouton 
public class PanneauExplorateur extends JPanel {

    private JTree localTree;
    private JButton backButton;
    private JPanel contentPanel;

    // La classe entiere depend imperativement d'un JTree pour fonctionner
    public PanneauExplorateur(JTree tree) {
        this.localTree = tree;

        setLayout(new BorderLayout());

        // Création du bouton de retour appelant la methode "navigateBack" pour sortir vers le dossier parent
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateBack();
            }
        });

        // // Création du panneau de contenu pour afficher les boutons
        // contentPanel = new JPanel();
        // contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // // Ajout des composants au panneau principal
        // add(backButton, BorderLayout.NORTH);
        // add(new JScrollPane(contentPanel), BorderLayout.CENTER);

        // Création du panneau de contenu pour afficher les boutons
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 4, 10, 10)); // Utilisation d'un GridLayout

        // Utilisation d'un ScrollPane pour la barre de défilement verticale
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Ajout des composants au panneau principal
        add(backButton, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Écouteur pour détecter les sélections de nœuds dans l'arbre
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            // Si le noeud est vide c'est un fichier
            if (selectedNode != null) {
                showChildren(selectedNode);
            }
        });
    }

    // // Affiche l'integralite d'un dossier dans l'attribut "private JPanel contentPanel", Panneau integrant l'ensemble des boutons
    // private void showChildren(DefaultMutableTreeNode parentNode) {
    //     // On retire tout les composant du panneau avant tout : permet d'actualiser le panneau
    //     contentPanel.removeAll();


    //     Enumeration<TreeNode> children = parentNode.children();
    //     while (children.hasMoreElements()) {
    //         DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) children.nextElement();
    //         String nodeName = childNode.toString();

    //         // Création du bouton représentant le fichier/dossier
    //         JButton button = new JButton(nodeName);
    //         button.setPreferredSize(new Dimension(120, 80));
    //         button.setVerticalTextPosition(SwingConstants.BOTTOM);
    //         button.setHorizontalTextPosition(SwingConstants.CENTER);

    //         // Écouteur pour détecter les clics sur les boutons de fichier/dossier
    //         button.addActionListener(new ActionListener() {
    //             @Override
    //             public void actionPerformed(ActionEvent e) {
    //                 if (childNode.getChildCount() > 0) {
    //                     // Afficher les enfants du nœud lorsqu'il est cliqué
    //                     showChildren(childNode);
    //                 }
    //             }
    //         });

    //         contentPanel.add(button);
    //     }

    //     contentPanel.revalidate();
    //     contentPanel.repaint();
    // }

    private void showChildren(DefaultMutableTreeNode parentNode) {
        contentPanel.removeAll();
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement entre les boutons
    
        Enumeration<TreeNode> children = parentNode.children();
        int col = 0;
        int row = 0;
    
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) children.nextElement();
            String nodeName = childNode.toString();
    
            // Création du bouton représentant le fichier/dossier
            JButton button = new JButton(nodeName);
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setPreferredSize(new Dimension(120, 80)); // Définir une taille fixe pour le bouton
    
            // Positionner le bouton dans le GridBagLayout
            gbc.gridx = col;
            gbc.gridy = row;
            contentPanel.add(button, gbc);
    
            col++; // Incrémenter la colonne
    
            // Passer à la prochaine ligne si nécessaire
            if (col >= 4) {
                col = 0;
                row++;
            }
        }
    
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Bouton retour du panneau
    private void navigateBack() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) localTree.getLastSelectedPathComponent();
        if (selectedNode != null) {
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
            if (parentNode != null) {
                localTree.setSelectionPath(new TreePath(parentNode.getPath()));
                showChildren(parentNode);
            }
        }
    }
}