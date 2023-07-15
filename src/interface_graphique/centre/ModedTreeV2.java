package src.interface_graphique.centre;

import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/*
    Cette classe a pour but de creer un objet JTree nomme "tree" qui affichera l'arborescence 
    de tous les peripheriques de stockage dotes d'une lettre
*/
public class ModedTreeV2 extends ModedTree {

    /**
     * Constructeur de la classe : - Ordonnance les branches de l'arbre
     * - Creation de l'arbre
     */
    public ModedTreeV2() {
        listeurRepertoires();
        setTree();
        this.tree.setRootVisible(false);
        this.tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                if (tree.getLastSelectedPathComponent() != null) {

                }
            }
        });
    }

    @Override
    public void listeurRepertoires() {
        setRacine(new DefaultMutableTreeNode());

        // On commence par lister tous les repertoires racine et lettres du pc
        for (File racines : File.listRoots()) {
            DefaultMutableTreeNode peripheriquesPrincipales = new DefaultMutableTreeNode(racines.getAbsolutePath());
            try {
                // Pour chaque Racine on liste les fichiers a l'aide du methode listFiles
                for (File fichierTemp_ : racines.listFiles()) {
                    // On finit par obtenir un noeud et on les ajoute 1 par 1
                    DefaultMutableTreeNode noeud = new DefaultMutableTreeNode(fichierTemp_.getName() + "\\");
                    peripheriquesPrincipales.add(this.listFile(fichierTemp_, noeud));
                }
            } catch (NullPointerException e) {
            }
            this.oracine.add(peripheriquesPrincipales);
        }
    }

    // Verifie si un noeud de l'arbre est, ou non un fichier
    private DefaultMutableTreeNode listFile(File file, DefaultMutableTreeNode node) {
        int count = 0;
        if (file.isFile())
            return new DefaultMutableTreeNode(file.getName());
        else {
            File[] list = file.listFiles();
            if (list == null)
                return new DefaultMutableTreeNode(file.getName());

            // Le code ci-dessous liste le contenu d'une repertoire
            // On boucle le tout jusqu'a la fin du tableau list
            for (File nom : list) {
                count++;
                // Pas plus de 5 enfants par noeud
                if (count <= 20) {
                    DefaultMutableTreeNode subNode;
                    if (nom.isDirectory()) {
                        subNode = new DefaultMutableTreeNode(nom.getName() + "\\");
                        node.add(this.listFile(nom, subNode));
                    } else {
                        subNode = new DefaultMutableTreeNode(nom.getName());
                    }
                    node.add(subNode);
                }
            }
            return node;
        }
    }

    public List<List<String>> buildHierarchy() {
        List<List<String>> hierarchy = new ArrayList<>();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
        traverseHierarchy(rootNode, hierarchy, 0);
        return hierarchy;
    }

    private void traverseHierarchy(DefaultMutableTreeNode node, List<List<String>> hierarchy, int level) {
        if (node == null) {
            return;
        }

        if (hierarchy.size() <= level) {
            hierarchy.add(new ArrayList<>());
        }

        List<String> levelList = hierarchy.get(level);
        levelList.add(node.toString());

        Enumeration<TreeNode> children = node.children();
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) children.nextElement();
            traverseHierarchy(childNode, hierarchy, level + 1);
        }
    }

    @Override
    public DefaultMutableTreeNode getRacine() {
        return super.getRacine();
    }

    // Place le Noeud actuel selectionne sur le noeud passe en parametre
    // Methode utile pour synchoniser la selection depuis la classe PanneauExplorateur
    public void setSelectedNode(DefaultMutableTreeNode noeud) {
        TreePath path = new TreePath(noeud.getPath());
        tree.setSelectionPath(path);
        tree.scrollPathToVisible(path);
    }
}
