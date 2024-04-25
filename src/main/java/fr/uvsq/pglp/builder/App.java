package fr.uvsq.pglp.builder;

import java.io.File;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class App 
{
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main( String[] args ){

    Scanner scanner = new Scanner(System.in);
    // variable pour la sauvegarde 
    String[] stock= new String[3];
    String commande="";
    String fichier="";
    int num=0;
    String fichiercopier=" ";
    String rep =" ";
    String parent = " ";
    String fichierACopier=" ";
    boolean fichierCoupe  = false;
    String cop=" ";
    int numfich=0;
    GestionnaireFichiers gf = new GestionnaireFichiers();  
    Repertoire repertoire = new Repertoire(2,"src","./src");
    Fichier file = new Fichier(14,"cylia.txt","./src/main/cylia.txt","Bonjour");
    Element ress= new Element(12,"fichier","src/...");

    while (true) {

        Path repertoireCourantt = Paths.get(System.getProperty("user.dir"));
        Map<Integer, String> hashMap = new HashMap<>();
// +++++++++++++++++Partie 1: Cheminement depuis la racine du système de fichiers
        // Appel de la fonction pour obtenir le chemin du répertoire courant
        //Path repertoireCourant = gf.obtenirCheminRepertoireCourant();
        Path repertoireCourant = repertoire.obtenirCheminRepertoireCourant();
        if(rep.trim().isEmpty()){
        
        // Affichez le chemin du répertoire courant
        System.out.println("Chemin du répertoire courant : " + repertoireCourant);
           }else{
        // Affichez le chemin du répertoire courant
        System.out.println("Chemin du répertoire courant : " + Path.of(rep));
           }

// +++++++++++++++++Partie 2: Affiche la note associée à l'élément
       /*  if(rep.trim().isEmpty()){ 
       // gf.creerFichierDansNouveauRepertoire(repertoireCourant.toString(), "annoter.txt");
         file.creerFichierDansNouveauRepertoire(repertoireCourant.toString(), "annoter.txt");
        }else{
        //gf.creerFichierDansNouveauRepertoire(rep, "annoter.txt");
        file.creerFichierDansNouveauRepertoire(rep, "annoter.txt");
        }
    
        if(num!=0){
        Path cheminBasePath = Paths.get(rep);
        Path cheminFinalPath = cheminBasePath.resolve("annoter.txt");
        // String fcc= getCheminParNomm("annoter.txt", hashMap) ;
         System.out.println("Chemin : "+ cheminFinalPath);
        //gf.afficherParNumero(cheminFinalPath.toString(),String.valueOf(numfich));
        }else{ 
         System.out.println("fichierrr vide ");
        }*/

// +++++++++++++++++++Partie 3: Affiche les ER du répertoire avec leurs NER
        //Path repertoireCourantt = Paths.get(System.getProperty("user.dir"));
        //Map<Integer, String> hashMap = new HashMap<>();
        // Affichez le contenu du répertoire courant
        if(rep.trim().isEmpty()){
        //gf.afficherContenuRepertoiree(repertoireCourant,hashMap );
        Repertoire.afficherContenuRepertoiree(repertoireCourant,hashMap );
        }else{
       // gf.afficherContenuRepertoiree(Path.of(rep),hashMap ); 
        Repertoire.afficherContenuRepertoiree(Path.of(rep),hashMap ); 
        }
        System.out.println("affichageeeeeeeeeeeee de hmap  ");
         //afficherContenuHashMap(hashMap);

        // ++++++++++++++++++++++Partie 4: Prompt pour saisir une commande
        System.out.println("les commandes  : \ncut\ncopy\npast\nfind\nvisu\n.\n..\nmkdir\n+\n-\noption\nXX\n  ");
        System.out.print("Entrez votre commande : ");
        String input = scanner.nextLine();
        
            // Utilisation d'une expression régulière pour extraire les éléments
        String[] elements = input.split("\\s+");   
        // Vérifier si le tableau a la taille attendue (3 éléments)
        if (elements.length == 3) {
            num = Integer.parseInt(elements[0]);
            commande = elements[1];
            fichier = elements[2];
            // on a stocké 
            stock[0]=String.valueOf(num);
            stock[1]=commande;
            stock[2]=fichier;

        // Afficher les éléments récupérés (et ne pas afficher le dernier élément s'il est vide)
            //System.out.println("Numéro : " + num);
           // System.out.println("Commande : " + commande);
            if (!fichier.isEmpty()) {
                System.out.println("Fichier : " + fichier);
            }
        } else {
            if (elements.length == 2) {
            if (isNumeric(elements[0])){
            num= Integer.parseInt(elements[0]);
            commande = elements[1];
            stock[0]=String.valueOf(num);
            stock[1]=commande;
            stock[2]=" ";
           // System.out.println("Numéro : " + num);
           // System.out.println("Commande : " + commande);
             
        }else{
             //System.out.println(" ces des lettres  ");
            /*  if(elements[1].equalsIgnoreCase(".")){
                commande = elements[1];
                fichier = elements[0];

             }else{*/
            commande = elements[0];
            fichier = elements[1];        
          //  System.out.println("Commande : " + commande);
           // System.out.println("nom fichier : " + fichier );
            // }
        }
            }else {
            if (elements.length == 1) {
            commande = elements[0];
           // System.out.println("Commande : " + commande);
            stock[1]=commande;
           // System.out.println(" stock : " + stock[0] + " "+stock[1] );
            }else if (elements.length == 0){

                System.out.println("vous avez entrez une commande vide");
            }
            }      
        }

        // Utilisation de switch-case pour effectuer une action en fonction du numéro choisi
            switch (commande) {
                case "copy":
                    numfich=num;
                    System.out.println("Vous avez choisi copier ");
                    //String chemin = gf.recupererCheminParNumero(num, hashMap);
                    String chemin = ress.recupererCheminParNumero(num, hashMap);
                     System.out.println("chemin "+ chemin);
                    cop =chemin;
                    fichierCoupe=false;
                    System.out.println(fichierACopier);
                    //gf.copier(chemin,fichierCoupe,fichierACopier,num, hashMap);
                    ress.copier(chemin,fichierCoupe,fichierACopier,num, hashMap);
                    break;
                case "mkdir":
                if(rep.trim().isEmpty()){
                    //gf.createDirectory(repertoireCourant.toString(),fichier);
                    repertoire.createDirectory(repertoireCourant.toString(),fichier);
                    }else{
                    //gf.createDirectory(rep,fichier);
                    repertoire.createDirectory(rep,fichier);
                    } 
                    break;
                case "cut":
                    numfich=num;
                    System.out.println("Vous avez choisi cut");
                    //chemin = gf.recupererCheminParNumero(num, hashMap);
                    chemin = ress.recupererCheminParNumero(num, hashMap);
                    cop =chemin;
                    fichierCoupe=true;
                    //gf.couper(chemin,fichierCoupe,fichierACopier,num, hashMap);
                    ress.couper(chemin,fichierCoupe,fichierACopier,num, hashMap);
                    // ici fonction de collage avec colle comme entree 
                    break;
                case "past":
                     System.out.println("Vous avez choisi past ");
                     //numfich=num;
                    
                       // gf.past(rep,fichierCoupe,cop,num, hashMap,cop);
                       ress.past(rep,fichierCoupe,cop,num, hashMap,cop);
                    break;
                case "find":
                    System.out.println("Vous avez choisi find ");
                    //find(fichier,hashMap);
                    //gf.rechercheFichier(fichier);
                    // ici fonction recherche avec fichier comme entree

                        // Sinon, copier le fichier normalement
                        //file.rechercheFichier(fichier);
                        commande findFichier = new Find (new Fichier(num, " ", " "," "),fichier);
                        findFichier.execute();
                    
                    break;
                case "..":
                    //System.out.println("Vous avez choisi remonter au repetoire parent (..) ");
                   //chemin = gf.recupererCheminParNumero(num, hashMap);
                   chemin = repertoire.recupererCheminParNumero(num, hashMap);
                   if(rep.trim().isEmpty()){
                  // parent= gf.remonterDossier(repertoireCourant);
                   parent= repertoire.remonterDossier(repertoireCourant);
                   }else{
                    //parent= gf.remonterDossier(Path.of(rep));
                    parent= repertoire.remonterDossier(Path.of(rep));
                   }
                    rep =parent;
                    break;
                case ".":
                    //System.out.println("Vous avez choisi (.) ");
                    // stock[0] pour recuperer en cas on a pas saisi la commande
                     //chemin = gf.recupererCheminParNumero(num, hashMap);
                     chemin = repertoire.recupererCheminParNumero(num, hashMap);
                     //gf.naviguerEtAfficherContenu(Path.of(chemin));
                     
                     repertoire.naviguerEtAfficherContenu(Path.of(chemin));
                    rep= chemin;

                    
                    // afficherContenuRepertoiree(Path.of(rep),hashMap );
                    break;
                case "visu":
                    //numfich=num;
                    System.out.println("Vous avez choisi visu ");
                    //String fich=getCheminParNom(fichier,hashMap);
                    //chemin = gf.recupererCheminParNumero(num, hashMap);
                    //gf.lireEtAfficherContenu(chemin);
                    chemin = file.recupererCheminParNumero(num, hashMap);
                   // file.lireEtAfficherContenu(chemin);

                    commande visuFichier = new Visu(new Fichier(num," " ,chemin," "),chemin);
                    visuFichier.execute();
                    break;
                case "+":
                    numfich=num;
                    System.out.println("Vous avez choisi annoter (+) num : "+num);
                    //chemin = gf.recupererCheminParNumero(num, hashMap);
                    chemin = file.recupererCheminParNumero(num, hashMap);
                    System.out.println("Saisir un text :  ");
                    String text  = scanner.nextLine();
                    //gf.ajouterTexteAuFichier2(chemin, text,num,hashMap);
                    //file.ajouterTexteAuFichier2(chemin, text,num,hashMap);
                   //ajouterTexteAuFichierAvecAnnotation(chemin, text,num,rep);


                   commande annoFichier = new Annoter(new Fichier(num," " ,chemin," "),chemin, text,num,hashMap);
                  annoFichier.execute();
                    break;
                case "-":
                    numfich=num;
                    System.out.println("Vous avez choisi annoter (-) ");
                    //chemin = gf.recupererCheminParNumero(num, hashMap);
                    chemin = file.recupererCheminParNumero(num, hashMap);
                    //gf.supprimerContenuFichier2(chemin,num,hashMap);
                    //file.supprimerContenuFichier2(chemin,num,hashMap);
                    commande annoFichierM = new AnnoterM(new Fichier(num," " ,chemin," "),chemin,num,hashMap);
                    annoFichierM.execute();
                    break;

                    case "touch":
                    System.out.print(" donner nom du fichier ");
                    String nomfich = scanner.nextLine();
                      if(rep.trim().isEmpty()){ 
                        // file.creerFichier(repertoireCourant.toString(), nomfich);
                         
                         commande creerFichier = new Fichiercreer(new Fichier(0," "," "," "),repertoireCourant.toString(), "monFichier.txt");
                        creerFichier.execute();
                    }else{
                        file.creerFichier(rep, nomfich);
                    }
  
                    
                    //System.exit(0); 
                    break;
                    

                case "O":
                    System.out.println(" options  : \n1-visualiser les details d'un fichier \n2-aide \3creer un nouveau fichier   ");
                    System.out.print("Entrez le numero de votre option : ");
                    input = scanner.nextLine();
                    switch (input) {
                      case "1":
                         System.out.print("saisir le numero du fichier ou repertoire : ");
                            String nfich = scanner.nextLine();
                           chemin = gf.recupererCheminParNumero(Integer.valueOf(nfich), hashMap);
                             // Créez un objet File avec le chemin spécifié
                         File fichierOuRepertoire = new File(chemin);
                            // Vérifiez si le fichier ou le répertoire existe
                        if (fichierOuRepertoire.exists()) {
                             // Affichez les détails
                          gf.afficherDetails(fichierOuRepertoire);
                          gf.afficherdate(fichierOuRepertoire);
                            } else {
                          System.out.println("Le fichier ou le répertoire n'existe pas.");
                            }
                        break;
                    case "2": 
                        System.out.print("votre commande doit etre sous la forme suivante : [<NER>] [<commande>] [<nom>].\n y a des cas NER (numero d'element du repertoire) et NOM (nom d'un fichier) ne sont pas obligatoire.\n  les commande : \n copy : pour copier un fichoer \n cut : couper un fichier \n past : coller un fichier \n mkdir : creer un repertoire \n find : faire une recherche d'un fichier dans un repertoire et dans tous ses sous repertoire \n . : le point pour acceder au contenue du repertoire \n .. : les deux point pour remonter d’un cran dans le système de fichiers \n visu : pour visualiser le contenue du fichier \n + : ajouter un texte au fichier \n - : pour supprimer le contenue d'un fichier \n XX : quitter le programme ");
                        //System.exit(0); 
                        break;
                    case "3": 
                        System.out.print(" donner nom du fichier ");
                        String nofich = scanner.nextLine();
                          if(rep.trim().isEmpty()){ 
                             gf.creerFichier(repertoireCourant.toString(), nofich);
                        }else{
                            gf.creerFichier(rep, nofich);
                        }
      
                        
                        //System.exit(0); 
                        break;
                    default:
                    System.out.println("Choix invalide. Veuillez entrer une operation valide.");
                    }
                    
                    break;
                    case "exit":
                    System.out.println("  QUITTER LE PROGRAMME ");
                    System.exit(0); // Quitte le programme
                    break;
                   
                    default:
                    System.out.println("Choix invalide. Veuillez entrer une operation valide.");
            }
        }


        }
}