public class Fichier {

    // afficher , renommer, supprimer , creer 
// +++++++++++++++++++++++++++++++++++ creer fichier +++++++++++++++++++++++++++++++++++++++++++++++++
 public void creerFichier(String chemin, String nomFichier) {
    // Créez un objet File avec le chemin spécifié
    File fichier = new File(chemin, nomFichier);

    try {
        // Créez le fichier
        if (fichier.createNewFile()) {
            System.out.println("Le fichier a été créé avec succès : " + fichier.getAbsolutePath());
        } else {
            System.out.println("Le fichier existe déjà : " + fichier.getAbsolutePath());
        }
    } catch (IOException e) {
        System.err.println("Erreur lors de la création du fichier : " + e.getMessage());
}
}
//+++++++++++++++++++++++++++++++++++++++++++++++ visu +++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public  void lireEtAfficherContenu(String cheminFichier) {
    // récupérer le fichier et lire le contenu et les caractères du fichier
    File fichier = new File(cheminFichier);
    try (BufferedReader lecteur = new BufferedReader(new FileReader(fichier))) {
        String ligne;
        // Lisez chaque ligne du fichier et affichez-la
        while ((ligne = lecteur.readLine()) != null) {
            System.out.println(ligne);
        }
        // Si le fichier est vide, afficher sa taille
        if (fichier.length() == 0) {
            System.out.println("Le fichier est vide. Taille du fichier : " + fichier.length() + " octets");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

//+++++++++++++++++++++++++++++++++++++++++++  annoter -  +++++++++++++++++++++++++++++++++++
public  void supprimerContenuFichier2(String cheminFichier,int num,Map<Integer, String> hashMap) {
    try (FileWriter writer = new FileWriter(cheminFichier, false)) {
     // Écrase le contenu du fichier en le réinitialisant
     writer.write("");
     System.out.println("Contenu du fichier supprimé avec succès.");
      String annotation = "Fichier numéro " + num + " a etait supprimé avec succès";
      String fc= getCheminParNom("annoter.txt", hashMap) ;
       try (BufferedWriter writerr = new BufferedWriter(new FileWriter(fc, true))) {
         writerr.write(annotation);
         writerr.newLine(); // Ajoute une nouvelle ligne si nécessaire
         System.out.println("Texte ajouté avec succès au fichier annoter .");
       }
       catch (IOException e) {
       System.err.println("Erreur lors de l'ajout du texte au fichier : " + e.getMessage());
      }  
 } catch (IOException e) {
       e.printStackTrace();
 }
}
//++++++++++++++++++++++++++++++++++++++++++++++  find  +++++++++++++++++++++++++++++++++++++++++++++++++
public void rechercheFichier(String nomFichier) {
    // Obtient le chemin du répertoire courant
    String cheminRepertoireCourant = System.getProperty("user.dir");
    // Initialise une liste pour stocker les chemins des fichiers trouvés
    List<String> cheminsFichiersTrouves = new ArrayList<>();
    // Appelle la fonction récursive de recherche
    rechercherFichierRecursive(new File(cheminRepertoireCourant), nomFichier, cheminsFichiersTrouves);
    // Affiche les chemins des fichiers trouvés
    for (String chemin : cheminsFichiersTrouves) {
        System.out.println("Fichier trouvé : " + chemin);
    }
}

public void rechercherFichierRecursive(File repertoire, String nomFichier, List<String> cheminsFichiersTrouves) {
    // Obtient la liste des fichiers dans le répertoire actuel
    File[] fichiers = repertoire.listFiles();

    if (fichiers != null) {
        for (File fichier : fichiers) {
            if (fichier.isDirectory()) {
                // Appelle récursivement la fonction pour les sous-répertoires
                rechercherFichierRecursive(fichier, nomFichier, cheminsFichiersTrouves);
            } else if (fichier.getName().equals(nomFichier)) {
                // Ajoute le chemin du fichier trouvé à la liste
                cheminsFichiersTrouves.add(fichier.getAbsolutePath());
            }
        }
    }
}
//=================================================== past =============================================================
public   void past(String cheminDestination,boolean fichierCoupe, String fichierACopier ,int num,Map<Integer, String> hashMap,String source) {
    // Appel à la méthode coller pour la réutilisation du code
    coller2(cheminDestination,fichierCoupe,fichierACopier,num,hashMap,source);
}
     
}
