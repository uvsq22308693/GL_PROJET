
package fr.uvsq.pglp.builder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import java.util.List; 


public class Fichier extends Element{
    String contenu;
    public Fichier(int ner, String nom, String chemin, String contenu){
        super(ner, nom, chemin);
        this.contenu = contenu;
    }

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

// +++++++++++++++++++++++++++++++++++ a partir du nom recuperer le chemin comme la fonction found 
/* 
public String getCheminParNom(String nomFichier, Map<Integer, String> hashMap) {
    for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
        String nomDuFichier = new File(entry.getValue()).getName();
        if (nomDuFichier.equals(nomFichier)) {
            return entry.getValue();
        }
    }

    return null; // Retourne null si aucun fichier trouvé avec le nom spécifié
}
*/
//+++++++++++++++++++++++++++++++++++++++++++  annoter -  +++++++++++++++++++++++++++++++++++
public  void supprimerContenuFichier2(String cheminFichier,int num,Map<Integer, String> hashMap) {
    try (FileWriter writer = new FileWriter(cheminFichier, false)) {
     // Écrase le contenu du fichier en le réinitialisant
     writer.write("");
     System.out.println("Contenu du fichier supprimé avec succès.");
      /* String annotation = "Fichier numéro " + num + " a etait supprimé avec succès";
      String fc= getCheminParNom("annoter.txt", hashMap) ;
       try (BufferedWriter writerr = new BufferedWriter(new FileWriter(fc, true))) {
         writerr.write(annotation);
         writerr.newLine(); // Ajoute une nouvelle ligne si nécessaire
         System.out.println("Texte ajouté avec succès au fichier annoter .");
       }
       catch (IOException e) {
       System.err.println("Erreur lors de l'ajout du texte au fichier : " + e.getMessage());
      } */ 
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

//++++++++++++++++++++++++++++++++++++++++ recuperer le chemin a partir du numero du fichier +++++++++++++++++++
/*public String recupererCheminParNumero(int numeroFichier, Map<Integer, String> hashMap) {
    if (hashMap.containsKey(numeroFichier)) {
        return hashMap.get(numeroFichier);
    } else {
        System.out.println("Aucun fichier trouvé avec le numéro " + numeroFichier);
        return null; // Ou vous pouvez lancer une exception ou renvoyer une chaîne vide selon vos besoins
    }
}*/

//+++++++++++++++++++++++++++++++++++  annoter +  ++++++++++++++++++++++++++++++++++++++++++++

public void ajouterTexteAuFichier2(String nomFichier, String texteAAjouter,int num,Map<Integer, String> hashMap) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier, true))) {
        writer.write(texteAAjouter);
        writer.newLine(); // Ajoute une nouvelle ligne si nécessaire

         /*String annotation = "Fichier numéro " + num + " : Ajout de texte: "+texteAAjouter;
         String fc= getCheminParNom("annoter.txt", hashMap) ;
          try (BufferedWriter writerr = new BufferedWriter(new FileWriter(fc, true))) {
            writerr.write(annotation);
            writerr.newLine(); // Ajoute une nouvelle ligne si nécessaire
            System.out.println("Texte ajouté avec succès au fichier annoter .");
          }
          catch (IOException e) {
          System.err.println("Erreur lors de l'ajout du texte au fichier : " + e.getMessage());
         } */

        System.out.println("Texte ajouté avec succès au fichier.");
    } catch (IOException e) {
        System.err.println("Erreur lors de l'ajout du texte au fichier : " + e.getMessage());
    }
}

/* 
// +++++++++++++++++++++++++++++++++++++++++ creer fichier annoter  ++++++++++++++++++++++++++++++++++++++++++
public   void creerFichierDansNouveauRepertoire(String cheminRepertoireParent, String nomFichier) {
    // Crée le répertoire parent s'il n'existe pas
    File repertoireParent = new File(cheminRepertoireParent);
    if (!repertoireParent.exists()) {
        repertoireParent.mkdirs();
    }

    // Crée le chemin complet du fichier
    File fichier = new File(repertoireParent, nomFichier);

    // Vérifie si le fichier existe déjà
    if (!fichier.exists()) {
        try {
            // Crée le fichier
            if (fichier.createNewFile()) {
                System.out.println("Fichier créé avec succès à : " + fichier.getAbsolutePath());
            } else {
                System.out.println("Échec de la création du fichier.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la création du fichier.");
        }
    } else {
        System.out.println("Le fichier existe déjà à : " + fichier.getAbsolutePath());
    }
}
//+++++++++++++++++++++++++++++++++++++++++++++ affichage  des annotations ++++++++++++++++++++++++++++

public void afficherParNumero(String cheminFichierEntree, String numeroRecherche) {
    if (cheminFichierEntree == null) {
        System.out.println("Le chemin du fichier est null.");
        return;
    }

    boolean numeroTrouve = false;
    try (BufferedReader lecteur = new BufferedReader(new FileReader(cheminFichierEntree))) {
        System.out.println("cvvvvvvvvvvvvvvv");
        String ligne;
        // Parcourir chaque ligne du fichier d'entrée
        while ((ligne = lecteur.readLine()) != null) {
            // Vérifier si le numéro recherché est présent dans la ligne
            if (ligne.contains(numeroRecherche)) {
                // Afficher la ligne correspondante
                System.out.println(ligne);
                numeroTrouve = true;
            }
        }
    } catch (IOException e) {
        System.out.println("bleemme");
        e.printStackTrace();
    }

    if (!numeroTrouve) {
        System.out.println("Aucune ligne avec le numéro " + numeroRecherche + " n'a été trouvée.");
    }
}*/

}

