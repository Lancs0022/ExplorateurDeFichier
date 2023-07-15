package src.interface_graphique.nord;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuNavigation {
    private JMenuBar BarreDeMenu = new JMenuBar();

    private JMenu option1 = new JMenu("Fichiers");
    private JMenu option2 = new JMenu("Edition");
    
    // private JMenu option3 = new JMenu("Sous fichier");
    private JMenuItem[] item1 = {
        new JMenuItem("Ouvrir une nouvelle fenetre"),
        new JMenuItem("Fermer"),
        new JMenuItem("Lancer"),
        new JMenuItem("Arreter"),
    };
    private JMenuItem[] item2 = {
        new JMenuItem("Nouveau dossier", new ImageIcon("assets/icons8-new-folder-24px.png")),
        new JMenuItem("Nouveau fichier", new ImageIcon("assets/icons8-new-file-24px.png")),
        new JMenuItem("Copier", new ImageIcon("assets/icons8-copy-24.png")),
        new JMenuItem("Couper", new ImageIcon("assets/icons8-cut-24.png")),
        new JMenuItem("Coller", new ImageIcon("assets/icons8-paste-24.png")),
        new JMenuItem("Renommer", new ImageIcon("assets/rename 24px.png")),
        new JMenuItem("Supprimer", new ImageIcon("assets/delete(1).png")),
        new JMenuItem("Propriete", new ImageIcon("assets/icons8-property-24.png"))
    };

    public MenuNavigation(){
        setAll();
    }

    private void setAll(){
        for(JMenuItem item : item1){
            this.option1.add(item);
        }
        for(JMenuItem item : item2){
            this.option2.add(item);
        }
        this.BarreDeMenu.add(option1);
        this.BarreDeMenu.add(option2);
    }
    
    public JMenuBar getMenu(){
        return this.BarreDeMenu;
    }

    public JMenuItem[] gItems1(){
        return this.item1;
    }
    public JMenuItem[] gItems2(){
        return this.item2;
    }

    public String toString(JMenuItem[] item){
        String details = "";
        for(JMenuItem i : item)
        {
            details = details.concat(i.getText());
            details = details.concat("\n");
        }
        return details;
    }
}
