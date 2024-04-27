
package fr.uvsq.pglp.builder;

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

import java.util.ArrayList;
import java.util.List;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;


public class Repertoire extends Element{
    public Repertoire (int ner, String nom, String chemin) {
        super(ner, nom, chemin);
        

    }

    // ================================repertoire courant==================================
    public Path obtenirCheminRepertoireCourant() {
     return Paths.get("").toAbsolutePath();
    }

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




}
