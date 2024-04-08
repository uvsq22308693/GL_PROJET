package fr.uvsq.cprog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.runner.Result;



public class AppTest 
{
  
     public static void main(String[] args) {
        // mkdir
        Result mkdir = JUnitCore.runClasses(AppTest.class);

        if (mkdir.wasSuccessful()) {
            System.out.println("Tous les tests ont réussi !");
        } else {
            System.out.println("Échecs de test :");
            for (Failure failure : mkdir.getFailures()) {
                System.out.println(failure.toString());
            }
        } 
         


      // créer le fichier notes 
      /* 
      Result notes = JUnitCore.runClasses(AppTest.class);

        if (notes.wasSuccessful()) {
            System.out.println("Le test a réussi !");
        } else {
            System.out.println("Échec du test :");
            for (Failure failure : notes.getFailures()) {
                System.out.println(failure.toString());
            }
        }
        
        */
        //affichage des annotations 
       Result annot = JUnitCore.runClasses(AppTest.class);

        if (annot.wasSuccessful()) {
            System.out.println("Tous les tests ont réussi !");
        } else {
            System.out.println("Échec du test :");
            for (Failure failure : annot.getFailures()) {
                System.out.println(failure.toString());
            }
        }
        
    }

    // ------------------------------------------------ Test sur mkdir --------------------------------------------------------------
    @Test
    public void testCreateDirectory() {
        // Préparation des données de test
        String chemin = "C:\\Users\\hp\\Downloads\\S1\\CplProg\\miniprojet-grp-31_61\\src\\main\\java\\fr\\uvsq\\cprog\\cylia"; // Remplacez cela par votre chemin réel
        String nomDossier = "anais";

        // Appel de la fonction à tester
        GestionnaireFichiers.createDirectory(chemin, nomDossier);

        // Vérification du résultat (vous pouvez vérifier le résultat en vérifiant si le répertoire a été créé)
        File nouveauRepertoire = new File(chemin, nomDossier);
        assert nouveauRepertoire.exists(); // Vérifie si le répertoire a été créé avec succès
    } 
    
    //-------------------------------------------- Test accès au dossier (.)
     @Test
    public void testNaviguerEtAfficherContenu_RepertoireValide() throws IOException {
        // Création d'un répertoire temporaire pour les tests
        Path tempDirectory = Files.createTempDirectory("testDir");

        // Création de quelques fichiers dans le répertoire temporaire
        Path tempFile1 = Files.createFile(tempDirectory.resolve("file1.txt"));
        Path tempFile2 = Files.createFile(tempDirectory.resolve("file2.txt"));

        // Redirection de la sortie standard vers un flux de sortie pour capturer les sorties console
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Appel de la méthode à tester avec le répertoire temporaire
        GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
        gestionnaire.naviguerEtAfficherContenu(tempDirectory);

        // Récupération du contenu de la sortie console
        String consoleOutput = outContent.toString().trim();

        // Vérification du contenu affiché dans la console
        String expectedOutput = tempFile1.getFileName() + System.lineSeparator() + tempFile2.getFileName();
        assertEquals(expectedOutput, consoleOutput);

        // Restauration de la sortie standard
        System.setOut(System.out);

        // Suppression des fichiers temporaires
        Files.deleteIfExists(tempFile1);
        Files.deleteIfExists(tempFile2);
        Files.deleteIfExists(tempDirectory);
    }

    @Test
    public void testNaviguerEtAfficherContenu_RepertoireInvalide() {
        // Utilisation d'un chemin qui ne pointe pas vers un répertoire existant
        Path invalidDirectory = Paths.get("chemin/vers/un/repertoire/inexistant");

        // Redirection de la sortie standard vers un flux de sortie pour capturer les sorties console
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Appel de la méthode avec un chemin de répertoire invalide
        GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
        gestionnaire.naviguerEtAfficherContenu(invalidDirectory);

        // Récupération du contenu de la sortie console
        String consoleOutput = outContent.toString().trim();

        // Vérification du message affiché pour un répertoire invalide
        String expectedOutput = "L'entrée n'est pas un répertoire.";
        assertEquals(expectedOutput, consoleOutput);

        // Restauration de la sortie standard
        System.setOut(System.out);
    } 
     
    //--------------------------------------------------   Affichage des annotations 
    @Test
    public void testAfficherParNumero_NumeroTrouve() throws IOException {
        // Chemin vers un fichier de test existant contenant le numéro recherché
        String cheminFichierTest = "C:\\Users\\hp\\Downloads\\S1\\CplProg\\miniprojet-grp-31_61\\src\\main\\java\\fr\\uvsq\\cprog\\A.txt";
        
        // Création du contenu de test dans le fichier
        String contenuFichier = "Ligne 1\nLigne 2\nNuméro recherché : 1234\nLigne 4";
        Path fichierTest = Paths.get(cheminFichierTest);
        Files.write(fichierTest, contenuFichier.getBytes());
        
        // Appel de la méthode pour rechercher et afficher le numéro
        GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
        String numeroRecherche = "1234";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        gestionnaire.afficherParNumero(cheminFichierTest, numeroRecherche);
        
        // Vérification si le numéro recherché est trouvé dans la sortie console
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains(numeroRecherche));
        
        // Suppression du fichier de test après le test
        Files.deleteIfExists(fichierTest);
    }

    @Test
    public void testAfficherParNumero_NumeroNonTrouve() throws IOException {
        // Chemin vers un fichier de test qui ne contient pas le numéro recherché
        String cheminFichierTest = "C:\\Users\\hp\\Downloads\\S1\\CplProg\\miniprojet-grp-31_61\\src\\main\\java\\fr\\uvsq\\cprog\\cylia\\anais.txt";
        
        // Création du contenu de test dans le fichier
        String contenuFichier = "Ligne 1\nLigne 2\nLigne 3\nLigne 4";
        Path fichierTest = Paths.get(cheminFichierTest);
        Files.write(fichierTest, contenuFichier.getBytes());
        
        // Appel de la méthode pour rechercher et afficher un numéro non présent
        GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
        String numeroRecherche = "1234"; // Numéro non présent dans le fichier de test
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        gestionnaire.afficherParNumero(cheminFichierTest, numeroRecherche);
        
        // Vérification si un message approprié est affiché pour un numéro non trouvé
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Aucune ligne avec le numéro " + numeroRecherche + " n'a été trouvée."));
        
        // Suppression du fichier de test après le test
        Files.deleteIfExists(fichierTest);
    }
    // --------------------------------------------------- Test fichier courant 
    @Test
    public void testObtenirCheminRepertoireCourant() {
        // Appel de la méthode pour obtenir le chemin du répertoire courant
        GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
        Path cheminRepertoireCourant = gestionnaire.obtenirCheminRepertoireCourant();

        // Vérification si le chemin du répertoire courant est non nul
        assertNotNull(cheminRepertoireCourant);

        // Vérification si le chemin du répertoire courant n'est pas vide
        assertFalse(cheminRepertoireCourant.toString().isEmpty());
        
        // Vous pouvez également ajouter d'autres assertions pour des vérifications plus spécifiques
    } 

    // ------------------------------------------------- Test créer fichier 
    @Test
    public void testCreerFichier_CreationNouveauFichier() {
        // Création d'un répertoire temporaire pour le test
        Path tempDirectory = Paths.get(System.getProperty("java.io.tmpdir"), "testDir");
        try {
            Files.createDirectories(tempDirectory);

            // Redirection de la sortie standard pour capturer les sorties console
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Appel de la méthode à tester
            GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
            gestionnaire.creerFichier(tempDirectory.toString(), "testFile.txt");

            // Récupération de la sortie console
            String consoleOutput = outContent.toString().trim();

            // Vérification de la création du fichier
            assertTrue(Files.exists(tempDirectory.resolve("testFile.txt")));
            assertEquals("Le fichier a été créé avec succès : " + tempDirectory.resolve("testFile.txt"), consoleOutput);

            // Restauration de la sortie standard
            System.setOut(System.out);
        } catch (Exception e) {
            fail("Exception inattendue : " + e.getMessage());
        } finally {
            // Nettoyage des fichiers temporaires
            try {
                Files.deleteIfExists(tempDirectory.resolve("testFile.txt"));
                Files.deleteIfExists(tempDirectory);
            } catch (Exception e) {
                fail("Impossible de supprimer les fichiers temporaires : " + e.getMessage());
            }
        }
    } 

    // --------------------------------------------  Test détails fichiers  ------------------------------------
    @Test
    public void testAfficherDetails() {
        // Création d'un fichier temporaire pour le test
        File tempFile = new File("tempFile.txt");
        try {
            if (tempFile.createNewFile()) {
                // Redirection de la sortie standard pour capturer les sorties console
                ByteArrayOutputStream outContent = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outContent));

                // Appel de la méthode à tester
                GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
                gestionnaire.afficherDetails(tempFile);

                // Récupération de la sortie console
                String consoleOutput = outContent.toString();

                // Vérification des détails affichés
                assertTrue(consoleOutput.contains("Détails pour : "));
                assertTrue(consoleOutput.contains("Est-ce un fichier ?"));
                assertTrue(consoleOutput.contains("Est-ce un répertoire ?"));
                assertTrue(consoleOutput.contains("Taille :"));
                assertTrue(consoleOutput.contains("Chemin absolu :"));

                // Suppression du fichier temporaire
                tempFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    // -------------------------------------------------- Affichage contenu de hmap
    @Test
    public void testAfficherContenuHashMap() {
        // Création d'une nouvelle HashMap pour tester la méthode
        Map<Integer, String> testHashMap = new HashMap<>();
        testHashMap.put(1, "Chemin 1");
        testHashMap.put(2, "Chemin 2");

        // Capture de la sortie console pour le test
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream oldOut = System.out;
        System.setOut(printStream);

        // Appel de la méthode à tester
        GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
        gestionnaire.afficherContenuHashMap(testHashMap);

        // Récupération de la sortie console capturée
        System.out.flush();
        System.setOut(oldOut);

        String consoleOutput = outputStream.toString();
        
        // Vérification de la sortie console
        assertTrue(consoleOutput.contains("Numéro : 1, Chemin : Chemin 1"));
        assertTrue(consoleOutput.contains("Numéro : 2, Chemin : Chemin 2"));
    }
    // ----------------------------------- Récupérer le chemin à partir num fichier 
    @Test
    public void testRecupererCheminParNumero_KeyExists() {
        // Création d'une HashMap pour tester la méthode
        Map<Integer, String> testHashMap = new HashMap<>();
        testHashMap.put(1, "Chemin 1");
        testHashMap.put(2, "Chemin 2");

        // Appel de la méthode pour tester la récupération d'un chemin avec une clé existante
        GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
        String cheminResultat = gestionnaire.recupererCheminParNumero(1, testHashMap);

        // Vérification du résultat
        assertEquals("Chemin 1", cheminResultat);
    }

    @Test
    public void testRecupererCheminParNumero_KeyNotExists() {
        // Création d'une HashMap pour tester la méthode
        Map<Integer, String> testHashMap = new HashMap<>();
        testHashMap.put(1, "Chemin 1");
        testHashMap.put(2, "Chemin 2");

        // Appel de la méthode pour tester la récupération d'un chemin avec une clé absente
        GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
        String cheminResultat = gestionnaire.recupererCheminParNumero(3, testHashMap);

        // Vérification du résultat
        assertNull(cheminResultat);
    }
    // -------------------------------  a partir du nom recuperer le chemin comme la fonction found
    @Test
    public void testGetCheminParNom_FileFound() {
        // Création d'une HashMap pour tester la méthode
        Map<Integer, String> testHashMap = new HashMap<>();
        testHashMap.put(1, "Chemin 1\\fichier1.txt");
        testHashMap.put(2, "Chemin 2\\fichier2.txt");

        // Appel de la méthode pour tester la récupération du chemin par nom de fichier existant
        GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
        String cheminResultat = gestionnaire.getCheminParNom("fichier1.txt", testHashMap);

        // Vérification du résultat
        assertEquals("Chemin 1\\fichier1.txt", cheminResultat);
    }

    @Test
    public void testGetCheminParNom_FileNotFound() {
        // Création d'une HashMap pour tester la méthode
        Map<Integer, String> testHashMap = new HashMap<>();
        testHashMap.put(1, "Chemin 1\\fichier1.txt");
        testHashMap.put(2, "Chemin 2\\fichier2.txt");

        // Appel de la méthode pour tester la récupération du chemin par nom de fichier non existant
        GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
        String cheminResultat = gestionnaire.getCheminParNom("fichier3.txt", testHashMap);

        // Vérification du résultat
        assertNull(cheminResultat);
    }
    // ---------------------------------------- test visu
    @Test
    public void testLireEtAfficherContenu_NonEmptyFile() {
        // Créer un fichier temporaire avec un contenu spécifique pour le test
        File tempFile = createTempFileWithContent("TestContent");

        // Rediriger la sortie standard vers un flux pour capturer le texte affiché
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Appeler la fonction à tester
        GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
        gestionnaire.lireEtAfficherContenu(tempFile.getAbsolutePath());

        // Récupérer le texte affiché
        String output = outputStream.toString();

        // Vérifier si le texte affiché correspond au contenu du fichier
        assertTrue(output.contains("TestContent"));

        // Restaurer la sortie standard
        System.setOut(System.out);

        // Supprimer le fichier temporaire après les tests
        tempFile.delete();
    }

    @Test
    public void testLireEtAfficherContenu_EmptyFile() {
        // Créer un fichier temporaire vide pour le test
        File tempFile = createTempFileWithContent("");

        // Rediriger la sortie standard vers un flux pour capturer le texte affiché
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Appeler la fonction à tester
        GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
        gestionnaire.lireEtAfficherContenu(tempFile.getAbsolutePath());

        // Récupérer le texte affiché
        String output = outputStream.toString();

        // Vérifier si le texte affiché indique que le fichier est vide
        assertTrue(output.contains("Le fichier est vide"));

        // Restaurer la sortie standard
        System.setOut(System.out);

        // Supprimer le fichier temporaire après les tests
        tempFile.delete();
    }

    // Méthode utilitaire pour créer un fichier temporaire avec un contenu donné
    private File createTempFileWithContent(String content) {
        try {
            File tempFile = File.createTempFile("test", ".txt");
            // Écrire le contenu dans le fichier temporaire
            java.nio.file.Files.writeString(tempFile.toPath(), content);
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    // ------------------------------------------ Test find
    @Test
    public void testRechercheFichier() throws IOException {
        // Créer une structure de répertoire temporaire pour le test
        File directory = Files.createTempDirectory("test-dir").toFile();
        File subdir1 = new File(directory, "subdir1");
        File subdir2 = new File(directory, "subdir2");
        subdir1.mkdir();
        subdir2.mkdir();

        File file1 = new File(directory, "testfile.txt");
        file1.createNewFile();

        File file2 = new File(subdir1, "testfile.txt");
        file2.createNewFile();

        File file3 = new File(subdir2, "anotherfile.txt");
        file3.createNewFile();

        // Appel de la méthode à tester
        GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
        List<String> cheminsFichiersTrouves = new ArrayList<>();
        gestionnaire.rechercherFichierRecursive(directory, "testfile.txt", cheminsFichiersTrouves);

        // Vérification du résultat
        assertEquals(2, cheminsFichiersTrouves.size());
        assertTrue(cheminsFichiersTrouves.contains(file1.getAbsolutePath()));
        assertTrue(cheminsFichiersTrouves.contains(file2.getAbsolutePath()));

        // Supprimer les fichiers temporaires après le test
        file1.delete();
        file2.delete();
        file3.delete();
        subdir1.delete();
        subdir2.delete();
        directory.delete();
    }
    // 
    
    // ---------------------------------------------- créer fichier annoter
    
    @Test
    public void testCreerFichierDansNouveauRepertoire() throws IOException {
        // Créer un répertoire temporaire pour le test
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "testDir");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        // Créer une instance de GestionnaireFichiers
        GestionnaireFichiers gestionnaire = new GestionnaireFichiers();

        // Appeler la méthode creerFichierDansNouveauRepertoire
        gestionnaire.creerFichierDansNouveauRepertoire(tempDir.getAbsolutePath(), "testFile.txt");

        // Vérifier si le fichier a été créé avec succès
        File createdFile = new File(tempDir, "testFile.txt");
        assertTrue(createdFile.exists());

        // Nettoyer après le test en supprimant le répertoire temporaire
        deleteDirectory(tempDir);
    }

    // Méthode utilitaire pour supprimer récursivement un répertoire et son contenu
    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }
    // ------------------------------------------------- Test copier 
    @Test
    public void testCopier() throws IOException {
        // Création d'un fichier de test pour la copie
        Path sourceFile = Paths.get("sourceFile.txt");
        Files.createFile(sourceFile);

        // Initialisation d'une HashMap pour le test
        Map<Integer, String> hashMap = new HashMap<>();
        String annoterPath = "annoter.txt";
        hashMap.put(1, annoterPath);

        // Appel de la fonction copier
        GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
        gestionnaire.copier("sourceFile.txt", false, "destinationFile.txt", 1, hashMap);

        // Vérification si les fichiers annoter.txt et sourceFile.txt ont été créés avec succès
        assertTrue(Files.exists(Paths.get(annoterPath)));
        assertTrue(Files.exists(sourceFile));

        // Suppression des fichiers de test après le test
        Files.deleteIfExists(sourceFile);
        Files.deleteIfExists(Paths.get("destinationFile.txt"));
        Files.deleteIfExists(Paths.get(annoterPath));
    }
    // ------------------------------------------------------- Test coller
    @Test
    public void testCouper() throws IOException {
        // Création d'un fichier de test pour la coupe
        Path sourceFile = Paths.get("sourceFile.txt");
        Files.createFile(sourceFile);

        // Initialisation d'une HashMap pour le test
        Map<Integer, String> hashMap = new HashMap<>();
        String annoterPath = "annoter.txt";
        hashMap.put(1, annoterPath);

        // Appel de la fonction couper
        GestionnaireFichiers gestionnaire = new GestionnaireFichiers();
        gestionnaire.couper("sourceFile.txt", true, "destinationFile.txt", 2, hashMap);

        // Vérification si les fichiers annoter.txt et sourceFile.txt ont été créés avec succès
        assertTrue(Files.exists(Paths.get(annoterPath)));
        assertTrue(Files.exists(sourceFile));

        // Suppression des fichiers de test après le test
        Files.deleteIfExists(sourceFile);
        Files.deleteIfExists(Paths.get("destinationFile.txt"));
        Files.deleteIfExists(Paths.get(annoterPath));
    }
}
    