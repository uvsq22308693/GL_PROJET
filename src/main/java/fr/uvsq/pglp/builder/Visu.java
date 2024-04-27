package fr.uvsq.pglp.builder;
import java.nio.file.Path;
public class Visu implements commande{
    Fichier fichier;
    String chemin;
    String nomFichier;
   

    public  Visu(Fichier fichier, String chemin) {
        this.fichier = fichier;
        this.chemin = chemin;
      
        
    }

    @Override
    public void execute() {
        fichier.lireEtAfficherContenu(chemin);
    }

    
    
}
