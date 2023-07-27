package src.interfaceGraphique;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import src.interfaceGraphique.composantsPrincipales.*;

import java.awt.BorderLayout;
import java.awt.Image;

public class Fenetre extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ModedTreeV2 tree = new ModedTreeV2();
	private MenuNavigation menu = new MenuNavigation(tree);
	private PanneauExplorateur explorer = new PanneauExplorateur(tree, menu);

	public Fenetre() {
		this.setTitle("Explorateur de Fichier : V2");
		this.setSize(1020, 660);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("assets/icons8-computer-96.png");
        Image iconImage = icon.getImage();
        Image resizedIcon = iconImage.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        this.setIconImage(resizedIcon);

		// Mise en place du panneau explorateur et de l'arbre cote a cote
		JSplitPane composantsCentre = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		composantsCentre.setComponentPopupMenu(explorer.getComponentPopupMenu());
        composantsCentre.setLeftComponent(new JScrollPane(tree.getTree()));
        composantsCentre.setRightComponent(explorer);
		composantsCentre.setDividerLocation(200);

		// Mise en place de la barre de menu et du toolbar au nord
		JPanel composantsNord = new JPanel();
        composantsNord.setLayout(new BorderLayout());
        composantsNord.add(menu.getMenu(), BorderLayout.NORTH);
        composantsNord.add(menu.getToolbar(),BorderLayout.CENTER);
		composantsNord.add(menu.getTextField(), BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		this.getContentPane().add(composantsCentre, BorderLayout.CENTER);
		this.getContentPane().add(composantsNord, BorderLayout.NORTH);

		this.setVisible(true);
	}
}
