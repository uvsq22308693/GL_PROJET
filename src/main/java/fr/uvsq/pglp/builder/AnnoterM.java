package fr.uvsq.pglp.builder;

import java.util.Map;

public class AnnoterM implements commande{

    Fichier fichier;
    String chemin;
    String nomFichier;
   
     String texteAAjouter;
     int num;
     Map<Integer, String> hashMap;

    public  AnnoterM(Fichier fichier, String chemin, int num, Map<Integer, String> hashMap) {
        this.fichier = fichier; 
        this.chemin=chemin;
        this.num=num;
        this.hashMap=hashMap;        
    }

    @Override
    public void execute() {
        fichier.supprimerContenuFichier2(chemin,num,hashMap);
    }
    
}
