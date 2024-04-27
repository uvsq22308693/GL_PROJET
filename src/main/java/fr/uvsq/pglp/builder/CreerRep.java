package fr.uvsq.pglp.builder;

public class CreerRep implements commande {

    Repertoire repertoire;
    String chemin;
    String nomDossier;
    
     

    public CreerRep(Repertoire repertoire,String chemin ,String nomDossier) {
        this.repertoire = repertoire;
        this.nomDossier = nomDossier;
    
      
    }

    @Override
    public void execute() {
        repertoire.createDirectory(chemin,nomDossier);
    }
    
}
