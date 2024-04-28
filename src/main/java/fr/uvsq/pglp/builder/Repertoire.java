
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

//+++++++++++++++++++++++++++++++++++++ copier copy colle ++++++++++++++++++++++++++++++++++++++++++++++
public void copier(String cheminSource,boolean fichierCoupe,String fichierACopier ,int num,Map<Integer, String> hashMap) {
    fichierACopier = cheminSource;
    fichierCoupe = false;
    /*String annotation = "Fichier numéro " + num + " a etait copié du repetoire";
         String fc= getCheminParNom("annoter.txt", hashMap) ;
          try (BufferedWriter writerr = new BufferedWriter(new FileWriter(fc, true))) {
            writerr.write(annotation);
            writerr.newLine(); // Ajoute une nouvelle ligne si nécessaire
            System.out.println("Texte ajouté avec succès au fichier annoter .");
          }
          catch (IOException e) {
          System.err.println("Erreur lors de l'ajout du texte au fichier : " + e.getMessage());
         }*/
    System.out.println("Fichier copié : " + fichierACopier);
}


 public void couper(String cheminSource, boolean fichierCoupe, String fichierACopier ,int num,Map<Integer, String> hashMap) {
    fichierACopier = cheminSource;
    fichierCoupe = true;
    /*String annotation = "Fichier numéro " + num + " a etait coupé du repertoire ";
         String fc= getCheminParNom("annoter.txt", hashMap) ;
          try (BufferedWriter writerr = new BufferedWriter(new FileWriter(fc, true))) {
            writerr.write(annotation);
            writerr.newLine(); // Ajoute une nouvelle ligne si nécessaire
            System.out.println("Texte ajouté avec succès au fichier annoter .");
          }
          catch (IOException e) {
          System.err.println("Erreur lors de l'ajout du texte au fichier : " + e.getMessage());
         }*/
    System.out.println("Fichier coupé : " + fichierACopier);
}

 public   Path ajusterNomFichierExistants(Path destinationPath) {
    int count = 1;
    String originalFileName = destinationPath.getFileName().toString();

    while (Files.exists(destinationPath)) {
        String newFileName = originalFileName.replaceFirst("[.][^.]+$", "") + "-copy" + count + originalFileName.replaceAll(".*[.]", ".");
        destinationPath = destinationPath.resolveSibling(newFileName);
        count++;
    }

    return destinationPath;
}

//////////////////////================================
 public   void coller2(String cheminDestination, boolean fichierCoupe, String fichierACopier, int num, Map<Integer, String> hashMap, String sourcePath) {
        if (fichierACopier != null) {
            Path sourcePathObj = Paths.get(fichierACopier);
            Path destinationPath = Paths.get(cheminDestination, sourcePathObj.getFileName().toString());

            // Vérifier si le fichier existe déjà dans la destination
            if (Files.exists(destinationPath)) {
                // Appeler la fonction pour ajuster le nom du fichier
                destinationPath = ajusterNomFichierExistants(destinationPath);
            }

            try {
                if (Files.isDirectory(sourcePathObj)) {
                    // Si c'est un dossier, copier son contenu récursivement
                    copierDossier(sourcePathObj, destinationPath, fichierCoupe, num, hashMap);
                } else {
                    // Sinon, copier le fichier normalement
                    copierFichier(sourcePathObj, destinationPath, fichierCoupe, num, hashMap);
                }

                // Réinitialiser les variables après le collage
                fichierACopier = null;
                fichierCoupe = false;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erreur lors du collage ou du déplacement du fichier.");
            }
        } else {
            System.out.println("Veuillez d'abord copier ou couper un fichier.");
        }
    }


     public   void copierDossier(Path sourcePath, Path destinationPath, boolean fichierCoupe, int num, Map<Integer, String> hashMap) throws IOException {
        // Créer le dossier de destination s'il n'existe pas
        if (!Files.exists(destinationPath)) {
            Files.createDirectories(destinationPath);
        }

        // Copier le contenu du dossier récursivement
        Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relativePath = sourcePath.relativize(file);
                Path destinationFile = destinationPath.resolve(relativePath);

                if (fichierCoupe) {
                    // Utiliser Files.move si le fichier a été coupé
                    Files.move(file, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Fichier coupé et déplacé avec succès à : " + destinationFile);
                } else {
                    // Utiliser Files.copy si le fichier a été simplement copié
                    Files.copy(file, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Fichier copié avec succès à : " + destinationFile);
                }

                // Ajouter l'annotation dans le fichier annoter.txt
               /* String annotation = "Fichier numéro " + num + " a été collé";
                String fc = getCheminParNom("annoter.txt", hashMap);
                try (BufferedWriter writerr = new BufferedWriter(new FileWriter(fc, true))) {
                    writerr.write(annotation);
                    writerr.newLine(); // Ajoute une nouvelle ligne si nécessaire
                    System.out.println("Texte ajouté avec succès au fichier annoter.txt.");
                } catch (IOException e) {
                    System.err.println("Erreur lors de l'ajout du texte au fichier annoter.txt : " + e.getMessage());
                }*/

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path relativePath = sourcePath.relativize(dir);
                Path destinationDir = destinationPath.resolve(relativePath);

                if (!Files.exists(destinationDir)) {
                    Files.createDirectories(destinationDir);
                }

                return FileVisitResult.CONTINUE;
            }
        });

        // Si c'est une coupe, supprimer le dossier source
        if (fichierCoupe) {
            Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    //=================================================== past =============================================================
 public   void past(String cheminDestination,boolean fichierCoupe, String fichierACopier ,int num,Map<Integer, String> hashMap,String source) {
    // Appel à la méthode coller pour la réutilisation du code
    coller2(cheminDestination,fichierCoupe,fichierACopier,num,hashMap,source);
}


}
