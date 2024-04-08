package fr.uvsq.cprog;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Date;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;


public class Repertoire {

    //========================================affichage des  elements du repertoire courant========================== 
    public static void afficherContenuRepertoiree(Path repertoire,Map<Integer, String> hashMap) {
        try {
            int numero = 1;
            DirectoryStream<Path> stream = Files.newDirectoryStream(repertoire);
            for (Path chemin : stream) {
                // recuperer le numero puis le nom 
                System.out.println(numero + " " + chemin.getFileName());
                //recuperer le chemin 
                System.out.println("  " + chemin.toAbsolutePath());
                hashMap.put(numero, chemin.toAbsolutePath().toString());
                numero++;
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la récupération du contenu du répertoire.");
            e.printStackTrace();
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++  crrer repertoire : MKDIR +++++++++++++++++++++++++++++++++++++++++++++
   public static void createDirectory(String chemin, String nomDossier) {
    // Création d'un objet File avec le chemin complet du répertoire à créer
    File nouveauRepertoire = new File(chemin, nomDossier);
    // Vérification si le répertoire n'existe pas déjà
    if (!nouveauRepertoire.exists()) {
        // Utilisation de mkdir pour créer le répertoire
        boolean creationReussie = nouveauRepertoire.mkdir();
        if (creationReussie) {
            System.out.println("Répertoire créé avec succès : " + nouveauRepertoire.getAbsolutePath());
        } else {
            System.err.println("Échec de la création du répertoire : " + nouveauRepertoire.getAbsolutePath());
        }
    } else {
        System.out.println("Le répertoire existe déjà : " + nouveauRepertoire.getAbsolutePath());
    }
       // renommer 
}

//++++++++++++++++++++++++++++++++++++++++ acceder au dossier (.) +++++++++++++++++++++++++++++++++++++
    public  void naviguerEtAfficherContenu(Path repertoireCourant) {
        // Vérifiez si l'entrée est un répertoire
        if (Files.isDirectory(repertoireCourant)) {
            // Affichez le contenu du répertoire
            afficherContenuRepertoire(repertoireCourant);
        } else {
            System.out.println("L'entrée n'est pas un répertoire.");
        }
    }

    public void afficherContenuRepertoire(Path repertoire) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(repertoire)) {
            for (Path chemin : stream) {
                System.out.println(chemin.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//+++++++++++++++++++++++++++++++++++++++ remonter le dossier (..) ++++++++++++++++++++++++++++++++++++++
public  String remonterDossier(Path repertoire) {
    // Utilisez la méthode getParent() pour obtenir le répertoire parent
    Path repertoireParent = repertoire.getParent();
    if (repertoireParent != null) {
        System.out.println("Chemin du répertoire parent est : " + repertoireParent);
        return repertoireParent.toString();
    } else {
        System.out.println("Le répertoire courant n'a pas de répertoire parent.");
        return repertoire.toString();
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
