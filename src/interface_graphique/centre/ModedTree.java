package src.interface_graphique.centre;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class ModedTree{
    protected DefaultMutableTreeNode oracine;
    private DefaultMutableTreeNode oRepertoire1;
    private DefaultMutableTreeNode oRepertoire2;
    protected JTree tree;

    public ModedTree() {
        listeurRepertoires();
        setTree();
    }

    public void listeurRepertoires() {
        setRacine(new DefaultMutableTreeNode("Home"));
        for(int i = 0; i<5 ; i++){
            setRepertoire1(new DefaultMutableTreeNode("dossier " + (i + 1)));
            oracine.add(oRepertoire1);
            for(int j = 0 ; j<5 ;j++){
                setRepertoire2(new DefaultMutableTreeNode("fichier " + (i + 1)));
                oRepertoire1.add(oRepertoire2);
            }
        }
	}

    //---------------------------------------------------------------
    public void setTree(){
        this.tree = new JTree(getRacine());
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
    //---------------------------------------------------------------
    public DefaultMutableTreeNode getRepertoire1() {
        return this.oRepertoire1;
    }
    public void setRepertoire1(DefaultMutableTreeNode repertoire1) {
        this.oRepertoire1 = repertoire1;
    }
    //---------------------------------------------------------------
    public DefaultMutableTreeNode getRepertoire2() {
        return this.oRepertoire2;
    }
    public void setRepertoire2(DefaultMutableTreeNode repertoire2) {
        this.oRepertoire2 = repertoire2;
    }
}
