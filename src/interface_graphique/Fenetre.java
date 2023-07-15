package src.interface_graphique;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import src.interface_graphique.centre.ModedTreeV2;
import src.interface_graphique.centre.PanneauExplorateur;
import src.interface_graphique.nord.MenuNavigation;

import java.awt.BorderLayout;
// import java.util.List;

public class Fenetre extends JFrame {
	public ModedTreeV2 tree = new ModedTreeV2();
	public MenuNavigation menu = new MenuNavigation();
	public PanneauExplorateur explorer = new PanneauExplorateur(tree.getTree(), tree);

	public Fenetre() {
		this.setTitle("Explorateur de Fichier : V0");
		this.setSize(720, 420);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Configuration du Layout
		this.setLayout(new BorderLayout());
		this.getContentPane().add(new JScrollPane(tree.getTree()), BorderLayout.WEST);
		this.getContentPane().add(explorer, BorderLayout.CENTER);
		this.getContentPane().add(menu.getMenu(), BorderLayout.NORTH);
		System.out.println(menu.toString(menu.gItems2()) + "Affichage des elements du menu");


		// List<List<String>> hierarchy = tree.buildHierarchy();

		// // Afficher le tableau hiérarchisé
		// for (List<String> level : hierarchy) {
		// 	for (String item : level) {
		// 		System.out.print(item + "\t");
		// 	}
		// 	System.out.println();
		// }



		this.setVisible(true);
	}
}
