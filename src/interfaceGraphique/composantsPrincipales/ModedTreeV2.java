package src.interfacegraphique.composantsprincipales;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *   Cette classe a pour but de creer un objet JTree nomme "tree" qui affichera l'arborescence 
 *   de tous les peripheriques de stockage dotes d'une lettre de l'appareil. C'est la classe la plus lourde a charger du projet
 *   Un limitateur est en place pour permettre de reduire le nombre de noeud a afficher dans le JTree.
 *   
 *   ! Un element est tres important pour bien comprendre le projet : la seule attribut de cette classe qui est "derniereSelection"
 *   est le moyen de communication de cette classe - qui s'occupe de l'arbre - avec les autres classes du projet. 
 *   Elle est mise a jour :  - Au niveau du constructeur dans le TreeSelectionListener ;
 *                           - Au niveau de PanneauExplorateur car cette classe traite une instance de ModedTreeV2 et a besoin l'arbre
 *                             pour afficher son contenu ;
 *
 *   Voici l'inventaire des methodes utilises dans la classe 
*/
public class ModedTreeV2{
    protected JTree tree;
    private String derniereSelection;
    protected DefaultMutableTreeNode oracine;

    /**
     *  Constructeur de la classe :  - Ordonnance les branches de l'arbre
     *  ---------------------------  - Creation de l'arbre
     *                               - Interaction : 
     */
    public ModedTreeV2() {
        listeurRepertoires();
        setTree();
        this.tree.setRootVisible(true);
        this.tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                if (tree.getLastSelectedPathComponent() != null) {
                    setDerniereSelection(getAbsolutePath(e.getPath()));
                    System.out.println(getDerniereSelection());
                }
            }
        });
        System.out.println("ModedTreeV2 charge !");
    }

/* ********************************************************************************************************************** *\
|                                                                                                                          |
|                                                                                                                          |
|                   C R E A T I O N   L ' A R B O R E S C E N C E   S U R   L E   N O E U D   o R a c i n e                |
|                                                                                                                          |
|                                                                                                                          |
\* ********************************************************************************************************************** */

    public void listeurRepertoires() {
        if(this.oracine != null)
        this.oracine.removeAllChildren();
        setRacine(new DefaultMutableTreeNode("Ce PC"));
        System.out.println("\nAppel du listeur de repertoire \n");
        // On commence par lister tous les repertoires racine et lettres du pc
        for (File racines : File.listRoots()) {
            DefaultMutableTreeNode peripheriquesPrincipales = new DefaultMutableTreeNode(racines.getAbsolutePath());
            try {
                // Pour chaque Racine on liste les fichiers a l'aide du methode listFiles
                for (File fichierTemp_ : racines.listFiles()) {
                    // On finit par obtenir un noeud et on les ajoute 1 par 1
                    DefaultMutableTreeNode noeud = new DefaultMutableTreeNode(fichierTemp_.getName() + "\\");
                    System.out.println(noeud);
                    peripheriquesPrincipales.add(this.listFile(fichierTemp_, noeud));
                }
            } catch (NullPointerException e) {}
            this.oracine.add(peripheriquesPrincipales);
        }
        System.out.println("\nFin du listeur de repertoire \n");
    }

    // Verifie si un noeud de l'arbre est, ou non un fichier
    private DefaultMutableTreeNode listFile(File file, DefaultMutableTreeNode node) {
        int limiteur = 0;
        if (file.isFile())
            return new DefaultMutableTreeNode(file.getName());
        else {
            File[] list = file.listFiles();
            if (list == null)
                return new DefaultMutableTreeNode(file.getName());

            // Le code ci-dessous liste le contenu d'une repertoire
            // On boucle le tout jusqu'a la fin du tableau list
            for (File nom : list) {
                limiteur++;
                /*
                 * Limiteur : permet de limiter le nombre de fichiers a afficher pour tous les repertoires
                 * Permet d'accelerer le chargement de l'application
                 */
                if (limiteur <= 20) {
                    DefaultMutableTreeNode subNode;
                    if (nom.isDirectory()) {
                        System.out.println(nom);
                        subNode = new DefaultMutableTreeNode(nom.getName() + "\\");
                        node.add(this.listFile(nom, subNode));
                    } else {
                        subNode = new DefaultMutableTreeNode(nom.getName());
                    }
                    node.add(subNode);
                }
            }
            // System.out.println("Fin du listeur de repertoire");
            return node;
        }
    }

//------------------------------------------------------------------------------------------------------------------------//

    /* 
        Place le Noeud actuel selectionne sur le noeud passe en parametre
        Methode utile pour synchoniser la selection depuis la classe PanneauExplorateur
    */
    public void setSelectedNode(DefaultMutableTreeNode noeud) {
        TreePath path = new TreePath(noeud.getPath());
        tree.setSelectionPath(path);
        tree.scrollPathToVisible(path);
    }

    /*
     *  Fait resortir une chaine contenant le chemin absolu d'un fichier ou un repertoire 
     *  a partir d'un chemin d'un noeud d'un arbre
     */
    public String getAbsolutePath(TreePath treePath) {
        StringBuilder str = new StringBuilder();
    
        // On balaie le contenu de l'objet TreePath en excluant la racine
        for (Object name : treePath.getPath()) {
            // On vérifie si l'objet est la racine de l'arbre
            if (tree.getModel().getRoot().equals(name)) {
                continue; // On passe à l'itération suivante pour exclure la racine
            }
            // Si l'objet a un nom, on l'ajoute au chemin
            if (name != null) {
                str.append(name.toString());
            }
        }
        return str.toString();
    }

    /**
     * @return String return the derniereSelection
     */
    public String getDerniereSelection() {
        return derniereSelection;
    }

    /**
     * @param derniereSelection the derniereSelection to set
     */
    public void setDerniereSelection(String derniereSelection) {
        this.derniereSelection = derniereSelection;
    }

    //---------------------------------------------------------------
    public void setTree(){
        this.tree = new JTree(this.oracine);
        // tree.setRootVisible(false);
    }
    public JTree getTree(){
        return this.tree;
    }
    //---------------------------------------------------------------
    public DefaultMutableTreeNode getRacine() {
        return this.oracine;
    }
    public void setRacine(DefaultMutableTreeNode racine) {
        this.oracine = racine;
    }

    // Cette méthode permet de rechercher tous les nœuds de l'arbre qui contiennent une certaine chaîne de texte
    public ArrayList<String> rechercherNoeuds(String texteRecherche) {
        // results est une collection de String qui contiendra tous les occurences
        ArrayList<String> results = new ArrayList<>();
        // On parcours l'arborescence de l'arbre depuis la recine
        Enumeration<TreeNode> enumeration = this.oracine.depthFirstEnumeration();

        while (enumeration.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumeration.nextElement();
            String nodeText = node.toString();
            if (nodeText.contains(texteRecherche.toLowerCase())) {
                // On ajoute l'occurence trouve
                results.add(getNodeAbsolutePath(node));
            }
        }
        return results;
    }

    // Méthode pour obtenir le chemin absolu d'un noeud
    private String getNodeAbsolutePath(DefaultMutableTreeNode node) {
        StringBuilder path = new StringBuilder();
        TreeNode[] nodes = node.getPath();
        for (int i = 0; i < nodes.length; i++) {
            if (i > 0) {
                path.append(File.separator);
            }
            path.append(nodes[i].toString());
        }
        return path.toString();
    }

    // Méthode pour changer le noeud sélectionné en utilisant son chemin absolu
    public void setSelectedNodeByPath(String path) {
        DefaultMutableTreeNode node = findNodeByPath(path);
        if (node != null) {
            setSelectedNode(node);
        }
    }

    // Méthode pour trouver un noeud à partir de son chemin absolu
    private DefaultMutableTreeNode findNodeByPath(String path) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.tree.getModel().getRoot();
        Enumeration<TreeNode> enumeration = root.breadthFirstEnumeration();

        while (enumeration.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumeration.nextElement();
            String nodePath = getNodeAbsolutePath(node);
            if (nodePath.equals(path)) {
                return node;
            }
        }

        return null;
    }
}

