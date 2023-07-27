package src.interfaceGraphique.composantsPrincipales;

import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
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
        System.out.println("Appel du listeur de repertoire");
        // On commence par lister tous les repertoires racine et lettres du pc
        for (File racines : File.listRoots()) {
            DefaultMutableTreeNode peripheriquesPrincipales = new DefaultMutableTreeNode(racines.getAbsolutePath());
            try {
                // Pour chaque Racine on liste les fichiers a l'aide du methode listFiles
                for (File fichierTemp_ : racines.listFiles()) {
                    // On finit par obtenir un noeud et on les ajoute 1 par 1
                    DefaultMutableTreeNode noeud = new DefaultMutableTreeNode(fichierTemp_.getName() + "\\");
                    System.out.println("Valeurs de fichier temp et noeud : " + fichierTemp_ + " - " + noeud);
                    peripheriquesPrincipales.add(this.listFile(fichierTemp_, noeud));
                }
            } catch (NullPointerException e) {}
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
                if (count <= 10) {
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

    // // Méthode pour actualiser l'arbre
    // public void actualiserArbre() {
    //     DefaultTreeModel model = (DefaultTreeModel) this.tree.getModel();
    //     // this.oracine.removeAllChildren();
    //     listeurRepertoires();
    //     this.setTree();
    //     model.nodeStructureChanged(oracine);
    //     model.reload();
    //     System.out.println("Fin de l'actualisation");
    // }

    /* 
        Place le Noeud actuel selectionne sur le noeud passe en parametre
        Methode utile pour synchoniser la selection depuis la classe PanneauExplorateur
    */
    public void setSelectedNode(DefaultMutableTreeNode noeud) {
        TreePath path = new TreePath(noeud.getPath());
        tree.setSelectionPath(path);
        tree.scrollPathToVisible(path);
    }

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

    

    /*      
    *      Code residuelles 
    *     ------------------
    */

    public List<List<String>> buildHierarchy(DefaultMutableTreeNode noeud) {
        System.out.println("Debut de construction de la hierachie");
        List<List<String>> hierarchy = new ArrayList<>();
        // DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
        traverseHierarchy(noeud, hierarchy, 0);
        System.out.println("Fin de construction de la hierachie");
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

    // private void getHierarchie(DefaultMutableTreeNode noeud){
    //     List<List<String>> hierarchy = this.buildHierarchy(noeud);
    //     for (List<String> level : hierarchy) {
    //         for (String item : level) {
    //         	System.out.print(item + "\t");
    //         }
    //         System.out.println();
    //     }
    // }

    // public String getCheminNoeud(DefaultMutableTreeNode node) {
    //     StringBuilder path = new StringBuilder();

    //     // Parcours du chemin du nœud jusqu'à la racine
    //     while (node != null) {
    //         Object userObject = node.getUserObject();

    //         // Vérification si le nœud représente un répertoire
    //         if (userObject instanceof String) {
    //             String nodeName = (String) userObject;
    //             if (!nodeName.endsWith("\\")) {
    //                 nodeName += "\\";
    //             }
    //             // Ajout du nom du répertoire au chemin
    //             path.insert(0, nodeName);
    //         }

    //         node = (DefaultMutableTreeNode) node.getParent();
    //     }

    //     return path.toString();
    // }

    
    // Cree un instance File depuis un noeud de l'arbre 
    public File createFileOrStrFromNode(DefaultMutableTreeNode node) {
        String filePath = getAbsolutePath(new TreePath(node.getPath()));
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(file.getName());
                    newNode.setAllowsChildren(false);
                    DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
                    treeModel.insertNodeInto(newNode, node, node.getChildCount());
                    tree.scrollPathToVisible(new TreePath(newNode.getPath()));
                    tree.setSelectionPath(new TreePath(newNode.getPath()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
