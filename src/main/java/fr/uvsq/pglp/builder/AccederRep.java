package fr.uvsq.pglp.builder;

import java.nio.file.Path;

public class AccederRep implements commande{

    Repertoire repertoire;
    Path chemin;


  
   

    public  AccederRep( Repertoire repertoire, Path chemin) {

        this.repertoire = repertoire;
        this.chemin = chemin;

    
          
    }

    @Override
    public void execute() {

        repertoire.naviguerEtAfficherContenu(chemin);
    }
    
}
