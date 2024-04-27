package fr.uvsq.pglp.builder;

import java.nio.file.Path;

public class Find implements commande {

    
    Fichier fichier;
    String chemin;
    String nomFichier;
   

    public  Find(Fichier fichier, String chemin) {
        this.fichier = fichier;
        this.chemin = chemin;     
    }

    @Override
    public void execute() {
        fichier.rechercheFichier(chemin);
    }

    
}
