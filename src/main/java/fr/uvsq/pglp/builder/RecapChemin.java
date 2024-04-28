package fr.uvsq.pglp.builder;

import java.nio.file.Path;

public class RecapChemin implements commande {

     Repertoire repertoire;
     private Path chemin;
   

    public  RecapChemin(Repertoire repertoire) {
        this.repertoire = repertoire; 
      
    }
    public RecapChemin(Repertoire repertoire, Path chemin){
        this.repertoire=repertoire;
        this.chemin=chemin;
    
    }
    @Override
    public void execute() {
        chemin = repertoire.obtenirCheminRepertoireCourant();
    }
// affichage de chemin 
    public void afficheg(){
        System.out.println("le chemin courant est  : " + this.chemin);
    }
   // mise a jour 
    public RecapChemin modifierchamin(Path chemin){
      return new RecapChemin(this.repertoire,chemin);
    }

      public Path varchemin ( ){
        return this.chemin;
  
      }
}
