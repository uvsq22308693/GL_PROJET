package fr.uvsq.pglp.builder;

import java.nio.file.Path;

public class Fichiercreer implements commande {

    Fichier fichier;
    String chemin;
    String nomFichier;
  

    public  Fichiercreer(Fichier fichier, String chemin, String nomFichier) {
        this.fichier = fichier;
        this.chemin = chemin;
        this.nomFichier = nomFichier;
        
    }

    @Override
    public void execute() {
        fichier.creerFichier(chemin, nomFichier);
    }


    
}
