
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


public class GestionnaireFichiers{
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

    

//++++++++++++++++++++++++++++++++++++++++++ affichage de la hmap ++++++++++++++++++++++++++++++++++++++++++++
    public void afficherContenuHashMap(Map<Integer, String> hashMap) {
        System.out.println("Contenu de la HashMap :");
        for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
            System.out.println("Numéro : " + entry.getKey() + ", Chemin : " + entry.getValue());
        }
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
//++++++++++++++++++++++++++++++++++++++++++++++ MKDIR +++++++++++++++++++++++++++++++++++++++++++++
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
//++++++++++++++++++++++++++++++++++++++++ acceder au dossier (.) +++++++++++++++++++++++++++++++++++++
 public   void naviguerEtAfficherContenu(Path repertoireCourant) {
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
//+++++++++++++++++++++++++++++++++++  annoter +  ++++++++++++++++++++++++++++++++++++++++++++

public void ajouterTexteAuFichier2(String nomFichier, String texteAAjouter,int num,Map<Integer, String> hashMap) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier, true))) {
        writer.write(texteAAjouter);
        writer.newLine(); // Ajoute une nouvelle ligne si nécessaire

         String annotation = "Fichier numéro " + num + " : Ajout de texte: "+texteAAjouter;
         String fc= getCheminParNom("annoter.txt", hashMap) ;
          try (BufferedWriter writerr = new BufferedWriter(new FileWriter(fc, true))) {
            writerr.write(annotation);
            writerr.newLine(); // Ajoute une nouvelle ligne si nécessaire
            System.out.println("Texte ajouté avec succès au fichier annoter .");
          }
          catch (IOException e) {
          System.err.println("Erreur lors de l'ajout du texte au fichier : " + e.getMessage());
         }

        System.out.println("Texte ajouté avec succès au fichier.");
    } catch (IOException e) {
        System.err.println("Erreur lors de l'ajout du texte au fichier : " + e.getMessage());
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
}
// ===============================================  Autres options =======================================================
//================== details fichiers ===================
 public void afficherDetails(File fichierOuRepertoire) {
        System.out.println("Détails pour : " + fichierOuRepertoire.getAbsolutePath());
        System.out.println("Est-ce un fichier ? " + fichierOuRepertoire.isFile());
        System.out.println("Est-ce un répertoire ? " + fichierOuRepertoire.isDirectory());
        System.out.println("Taille : " + fichierOuRepertoire.length() + " octets");
        System.out.println("Chemin absolu : " + fichierOuRepertoire.getAbsolutePath());
        //System.out.println("Dernière modification : " + fichierOuRepertoire.lastModified());
    }
  
      public void afficherdate(File fichierOuRepertoire) {
        // ... (le reste du code reste inchangé)

        // Formate la date de dernière modification
        Date dateDerniereModification = new Date(fichierOuRepertoire.lastModified());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dateFormatee = format.format(dateDerniereModification);

        System.out.println("Dernière modification : " + dateFormatee);
    }

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
}