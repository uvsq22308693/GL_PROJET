package fr.uvsq.pglp.builder;

import java.nio.file.Path;

public class RemoteRep implements commande{

    Repertoire repertoire;
    Path chemin;
    String resultat;
   

    public  RemoteRep(Repertoire repertoire, Path chemin) {
        this.repertoire = repertoire;
        this.chemin = chemin;
      
    }
    public RemoteRep(Repertoire repertoire, Path chemin,String resultat){
        this.repertoire=repertoire;
        this.chemin=chemin;
        this.resultat=resultat;
    
    }
    @Override
    public void execute() {
        resultat = repertoire.remonterDossier(chemin);
    }
// affichage de chemin 
    public void afficheg(){
        System.out.println("le chemin apres avoir remonter  est  : " + this.chemin);
    }
   // mise a jour 
    public RemoteRep  modifierchamin(Path chemin){
      return new RemoteRep (this.repertoire,chemin);
    }
    
}
