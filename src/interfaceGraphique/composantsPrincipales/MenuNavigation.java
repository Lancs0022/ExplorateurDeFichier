package src.interfacegraphique.composantsprincipales;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import src.interfacegraphique.Fenetre;

public class MenuNavigation {
    private JMenuBar BarreDeMenu = new JMenuBar();
    private JToolBar toolbar;
    private JTextField barreChemin = new JTextField(20);
    private JPanel champsTextuel;
    // Nouveaux attributs pour le panneau de recherche
    private JPanel recherchePanel;
    private JTextField rechercheField;
    private JButton rechercheButton;
    private JPopupMenu popupMenu = new JPopupMenu();
    private ModedTreeV2 local_modedTree;
    private String temp;
    private String copierOuCouper;
    // private JButton boutonRetour;

    private ImageIcon iconeNewfenetre = new ImageIcon("assets/icons8-new-window-24.png");
    private ImageIcon iconeOpenPowershell = new ImageIcon("assets/icons8-powershell-ise-24.png");
    private ImageIcon iconeFermer = new ImageIcon("assets/icons8-close-24(1).png");
    // private ImageIcon iconeRetour = new ImageIcon("assets/icons8-back-24-7.png");
    private ImageIcon iconeDossier = new ImageIcon("assets/icons8-new-folder-24px.png");
    private ImageIcon iconeFichier = new ImageIcon("assets/icons8-new-file-24px.png");
    private ImageIcon iconeCopie = new ImageIcon("assets/icons8-copy-24.png");
    private ImageIcon iconeCouper = new ImageIcon("assets/icons8-cut-24.png");
    private ImageIcon iconeColler = new ImageIcon("assets/icons8-paste-24.png");
    private ImageIcon iconeRennomer = new ImageIcon("assets/rename 24px.png");
    private ImageIcon iconeSuppression = new ImageIcon("assets/delete(1).png");
    private ImageIcon iconePropriete = new ImageIcon("assets/icons8-property-24.png");

    private JMenu option1 = new JMenu("Fichiers");
    private JMenu option2 = new JMenu("Edition");
    
    private JMenuItem[] item1 = {
        new JMenuItem("Ouvrir une nouvelle fenetre", iconeNewfenetre),
        new JMenuItem("Ouvrir Powershell", iconeOpenPowershell),
        new JMenuItem("Fermer", iconeFermer),
    };

    private JMenuItem[] item2 = {
        new JMenuItem("Nouveau dossier", iconeDossier),
        new JMenuItem("Nouveau fichier", iconeFichier),
        new JMenuItem("Copier", iconeCopie),
        new JMenuItem("Couper", iconeCouper),
        new JMenuItem("Coller", iconeColler),
        new JMenuItem("Renommer", iconeRennomer),
        new JMenuItem("Supprimer", iconeSuppression),
        new JMenuItem("Propriete", iconePropriete)
    };

    private JMenuItem[] item2Popup = {
        new JMenuItem("Nouveau dossier", iconeDossier),
        new JMenuItem("Nouveau fichier", iconeFichier),
        new JMenuItem("Copier", iconeCopie),
        new JMenuItem("Couper", iconeCouper),
        new JMenuItem("Coller", iconeColler),
        new JMenuItem("Renommer", iconeRennomer),
        new JMenuItem("Supprimer", iconeSuppression),
        new JMenuItem("Propriete", iconePropriete)
    };
    private JButton[] item2ToolBar = {
        new JButton("Nouveau dossier", iconeDossier),
        new JButton("Nouveau fichier", iconeFichier),
        new JButton("Copier", iconeCopie),
        new JButton("Couper", iconeCouper),
        new JButton("Coller", iconeColler),
        new JButton("Renommer", iconeRennomer),
        new JButton("Supprimer", iconeSuppression),
        new JButton("Propriete", iconePropriete)
    };

    public MenuNavigation(ModedTreeV2 modedTree){
        local_modedTree = modedTree;
        setAllForJMenuBar();
        createToolbar();
        setAllForPopupMenu();
        setAllForChampsTextuel();
        interactionSurItem1(this.item1, modedTree);
        interactionSurItem(this.item2, modedTree);
        interactionSurItem(this.item2Popup, modedTree);
        interactionSurItem(this.item2ToolBar,modedTree);
    }

    // Méthode pour afficher les résultats de la recherche dans une fenêtre séparée
    private void afficherResultatsRecherche(ArrayList<String> results, ModedTreeV2 modedTree) {
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aucun résultat trouvé.", "Résultats de recherche", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel resultsPanel = new JPanel(new GridLayout(results.size(), 2));

        // Créer des boutons de sélection pour chaque résultat
        for (String result : results) {
            JButton selectButton = new JButton("Sélectionner");
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    local_modedTree.setSelectedNodeByPath(result);
                }
            });

            resultsPanel.add(new JLabel(result));
            resultsPanel.add(selectButton);
        }

        JScrollPane resultsScrollPane = new JScrollPane(resultsPanel);
        JOptionPane.showMessageDialog(null, resultsScrollPane, "Résultats de recherche", JOptionPane.PLAIN_MESSAGE);
    }


    // Met a jour le chemin du repertoire actuelle dans le champs de texte "barreChemin"
    public void updateParentNodePath(String path) {
        this.barreChemin.setText(path);
    }

    private void createToolbar() {
        // this.boutonRetour = new JButton("Retour", iconeRetour);
        this.toolbar = new JToolBar();
        // this.toolbar.add(boutonRetour);

        for (JButton item : item2ToolBar) {
            // Pour chaque JMenuItem, on crée un JButton correspondant avec son icône
                this.toolbar.add(item);
        }
    }

    private void setAllForChampsTextuel(){
        // Initialisation du panneau de recherche
        this.recherchePanel = new JPanel();
        this.rechercheField = new JTextField(20);
        this.rechercheButton = new JButton("Rechercher");
        this.recherchePanel.add(rechercheField);
        this.recherchePanel.add(rechercheButton);
        // Création du panneau "hcampsTextuel" pour regrouper le JTextField et le panneau de recherche
        this.champsTextuel = new JPanel(new BorderLayout());
        this.champsTextuel.add(barreChemin, BorderLayout.CENTER);
        this.champsTextuel.add(recherchePanel, BorderLayout.EAST);
        // Ajouter l'ActionListener pour la recherche
        rechercheButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textToSearch = rechercheField.getText();
                if (!textToSearch.isEmpty()) {
                    ArrayList<String> results = local_modedTree.rechercherNoeuds(textToSearch);
                    afficherResultatsRecherche(results, local_modedTree);
                }
            }
        });
    }

    private void setAllForJMenuBar(){
        for(JMenuItem item : item1){
            this.option1.add(item);
        }
        for(JMenuItem item : item2){
            this.option2.add(item);
        }
        this.BarreDeMenu.add(option1);
        this.BarreDeMenu.add(option2);
    }

    private void setAllForPopupMenu(){
        for (JMenuItem item : item2Popup) {
            this.popupMenu.add(item);
        }
    }
    
    public JMenuBar getMenu(){
        return this.BarreDeMenu;
    }

    public JToolBar getToolbar() {
        return this.toolbar;
    }

    public JTextField getTextField() {
        return this.barreChemin;
    }

    public JPanel getChampsTextuel() {
        return champsTextuel;
    }

    public JPopupMenu getPopupMenu(){
        return this.popupMenu;
    }

    public JMenuItem[] getItems1(){
        return this.item1;
    }
    public JMenuItem[] getItems2(){
        return this.item2;
    }
    public JMenuItem[] getItem2z() {
        return item2Popup;
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

    // 
    public void interactionSurItem(AbstractButton[] item, ModedTreeV2 modedTree){
        // Ajoute un listener pour créer un nouveau dossier
        item[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folderPath = modedTree.getDerniereSelection();
                if (folderPath != null) {
                    creerNouveauDossier(folderPath);
                }
            }
        });

        // Ajoute un listener pour créer un nouveau fichier
        item[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folderPath = modedTree.getDerniereSelection();
                if (folderPath != null) {
                    creerNouveauFichier(folderPath);
                }
            }
        });

        // Ajouter les écouteurs d'événements aux éléments du menu
        // Ajoute un listener pour COPIER un fichier
        item[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = modedTree.getDerniereSelection();
                if (filePath != null) {
                    copierFichier(filePath);
                }
            }
        });

        // Ajoute un listener pour COUPER un fichier
        item[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = modedTree.getDerniereSelection();
                if (filePath != null) {
                    couperFichier(filePath);
                }
            }
        });

        // Ajoute un listener pour COLLER un fichier
        item[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folderPath = modedTree.getDerniereSelection();
                if (folderPath != null) {
                    collerFichier(folderPath);
                }
            }
        });

        // Ajoute un listener pour RENOMMER un fichier
        item[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = modedTree.getDerniereSelection();
                System.out.println(filePath);
                if (filePath != null) {
                    renommerFichier(filePath);
                }
            }
        });

        // Ajoute un listener pour SUPPRIMER un fichier
        item[6].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = modedTree.getDerniereSelection();
                if (filePath != null) {
                    supprimerFichier(filePath);
                }
            }
        });

        item[7].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = modedTree.getDerniereSelection();
                if (filePath != null) {
                    new FenetrePropriete(filePath);
                }
            }
        });
    }

    public void interactionSurItem1(AbstractButton[] item, ModedTreeV2 modedTree){
        // Ajoute un listener pour créer un nouveau dossier
        item[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Fenetre();
            }
        });

        // Ajoute un listener pour créer un nouveau fichier
        item[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Ouvrir Windows PowerShell en utilisant le programme "powershell.exe"
                    Desktop.getDesktop().open(new java.io.File("C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Ajouter les écouteurs d'événements aux éléments du menu
        // Ajoute un listener pour COPIER un fichier
        item[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

/*----------------------------------------------------------- Methodes de manipulation de fichier --------------------------------------------------------------- */

    private void creerNouveauDossier(String folderPath) {
        if (folderPath == null) {
            afficherErreur("Veuillez sélectionner un dossier.");
            return;
        }
    
        String nouveauNom = demanderNomElement();
        if (nouveauNom == null) {
            return; // L'utilisateur a annulé la création
        }
    
        String cheminComplet = folderPath + File.separator + nouveauNom;
        File nouveauDossier = new File(cheminComplet);
    
        if (nouveauDossier.exists()) {
            // Le dossier existe déjà, ajouter "- copie" au nom
            String nouveauNomCopie = nouveauNom + "- copie";
            cheminComplet = folderPath + File.separator + nouveauNomCopie;
            nouveauDossier = new File(cheminComplet);
        }
    
        if (nouveauDossier.mkdir()) {
            afficherMessage("Le dossier a été créé avec succès.");
        } else {
            afficherErreur("Impossible de créer le dossier.");
        }
    }
    
    private void creerNouveauFichier(String folderPath) {
        if (folderPath == null) {
            afficherErreur("Veuillez sélectionner un dossier.");
            return;
        }
    
        String nouveauNom = demanderNomElement();
        if (nouveauNom == null) {
            return; // L'utilisateur a annulé la création
        }
    
        String cheminComplet = folderPath + File.separator + nouveauNom;
        File nouveauFichier = new File(cheminComplet);
    
        if (nouveauFichier.exists()) {
            // Le fichier existe déjà, ajouter "- copie" au nom
            String nouveauNomCopie = nouveauNom + "- copie";
            cheminComplet = folderPath + File.separator + nouveauNomCopie;
            nouveauFichier = new File(cheminComplet);
        }
    
        try {
            if (nouveauFichier.createNewFile()) {
                afficherMessage("Le fichier a été créé avec succès.");
            } else {
                afficherErreur("Impossible de créer le fichier.");
            }
        } catch (IOException ex) {
            afficherErreur("Une erreur s'est produite lors de la création du fichier.");
        }
    }
    
    private String demanderNomElement() {
        String nouveauNom = JOptionPane.showInputDialog("Nom du nouvel élément :");
        if (nouveauNom != null && nouveauNom.trim().isEmpty()) {
            afficherErreur("Le nom de l'élément ne peut pas être vide.");
            return null;
        }
        return nouveauNom;
    }
    
    private void afficherMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
    
    private void afficherErreur(String erreur) {
        JOptionPane.showMessageDialog(null, erreur, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    private void supprimerFichier(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            int choice = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce fichier ?",
                    "Supprimer le fichier", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                if (file.delete()) {
                    JOptionPane.showMessageDialog(null, "Le fichier a été supprimé avec succès.");
                } else {
                    JOptionPane.showMessageDialog(null, "Impossible de supprimer le fichier.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Le fichier n'existe pas.");
        }
    }

    private void renommerFichier(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            String newFileName = JOptionPane.showInputDialog("Nouveau nom de fichier :");
            if (newFileName != null && !newFileName.isEmpty()) {
                String parentPath = file.getParent();
                File newFile = new File(parentPath + File.separator + newFileName);
                if (file.renameTo(newFile)) {
                    JOptionPane.showMessageDialog(null, "Le fichier a été renommé avec succès.");
                } else {
                    JOptionPane.showMessageDialog(null, "Impossible de renommer le fichier.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Le fichier n'existe pas.");
        }
    }

    private void copierFichier(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            temp = filePath;
            copierOuCouper = "copie";
            JOptionPane.showMessageDialog(null, "Le fichier est prêt à être copié.");
        } else {
            JOptionPane.showMessageDialog(null, "Le fichier n'existe pas.");
        }
    }

    private void couperFichier(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            temp = filePath;
            copierOuCouper = "couper";
            JOptionPane.showMessageDialog(null, "Le fichier est prêt à être déplacé.");
        } else {
            JOptionPane.showMessageDialog(null, "Le fichier n'existe pas.");
        }
    }

    private void collerFichier(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            if (temp != null && !temp.isEmpty()) {
                File sourceFile = new File(temp);
                String fileName = sourceFile.getName();
                File destinationFile = new File(folderPath + File.separator + fileName);
                try {
                    if (copierOuCouper.equals("copie")) {
                        Files.copy(sourceFile.toPath(), destinationFile.toPath(),
                                StandardCopyOption.REPLACE_EXISTING);
                        JOptionPane.showMessageDialog(null, "Le fichier a été copié avec succès.");
                    } else if (copierOuCouper.equals("couper")) {
                        Files.move(sourceFile.toPath(), destinationFile.toPath(),
                                StandardCopyOption.REPLACE_EXISTING);
                        JOptionPane.showMessageDialog(null, "Le fichier a été déplacé avec succès.");
                    }
                    temp = null;
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null,
                            "Une erreur s'est produite lors de la copie/déplacement du fichier.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Il n'y a pas de fichier à copier/déplacer.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Le dossier de destination n'existe pas.");
        }
    }
}
