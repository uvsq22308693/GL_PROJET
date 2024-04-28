package fr.uvsq.pglp.builder;

import java.util.HashMap;
import java.util.Map;
import java.nio.file.Path;

public class ContenueRep implements commande  {

    Repertoire repertoire;
    Map<Integer, String> hashMap;
    Path chemin;
    

    public  ContenueRep(Repertoire repertoire,Path chemin, Map<Integer, String> hashMap) {
        this.repertoire = repertoire; 

        this.hashMap=hashMap;
        this.chemin=chemin;
        
    }

    @Override
    public void execute() {
        repertoire.afficherContenuRepertoiree (chemin,hashMap);
    }


    
}
