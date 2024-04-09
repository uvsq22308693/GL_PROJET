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
import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;


public class responsable {
    
// +++++++++++++++++++++++++++++++++++ a partir du nom recuperer le chemin comme la fonction found 

public String getCheminParNom(String nomFichier, Map<Integer, String> hashMap) {
    for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
        String nomDuFichier = new File(entry.getValue()).getName();
        if (nomDuFichier.equals(nomFichier)) {
            return entry.getValue();
        }
    }

    return null; // Retourne null si aucun fichier trouvé avec le nom spécifié
}

//++++++++++++++++++++++++++++++++++++++++ recuperer le chemin a partir du numero du fichier +++++++++++++++++++
public String recupererCheminParNumero(int numeroFichier, Map<Integer, String> hashMap) {
    if (hashMap.containsKey(numeroFichier)) {
        return hashMap.get(numeroFichier);
    } else {
        System.out.println("Aucun fichier trouvé avec le numéro " + numeroFichier);
        return null; // Ou vous pouvez lancer une exception ou renvoyer une chaîne vide selon vos besoins
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

     public   void copierFichier(Path sourcePath, Path destinationPath, boolean fichierCoupe, int num, Map<Integer, String> hashMap) throws IOException {
        if (fichierCoupe) {
            // Utiliser Files.move si le fichier a été coupé
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Fichier coupé et déplacé avec succès à : " + destinationPath);
        } else {
            // Utiliser Files.copy si le fichier a été simplement copié
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Fichier copié avec succès à : " + destinationPath);
        }

       /* // Ajouter l'annotation dans le fichier annoter.txt
        String annotation = "Fichier numéro " + num + " a été collé";
        String fc = getCheminParNom("annoter.txt", hashMap);
        try (BufferedWriter writerr = new BufferedWriter(new FileWriter(fc, true))) {
            writerr.write(annotation);
            writerr.newLine(); // Ajoute une nouvelle ligne si nécessaire
            System.out.println("Texte ajouté avec succès au fichier annoter.txt.");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'ajout du texte au fichier annoter.txt : " + e.getMessage());
        }*/
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
