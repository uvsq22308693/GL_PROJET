package fr.uvsq.pglp.builder;

import java.util.Map;
import java.nio.file.Path;

public class Annoter implements commande {
    
    Fichier fichier;
    String chemin;
     String nomFichier;
   
     String texteAAjouter;
     int num;
     Map<Integer, String> hashMap;

    public  Annoter(Fichier fichier, String nom,String text, int num, Map<Integer, String> hashMap) {
        this.fichier = fichier; 
        this.nomFichier = nom;
        this.texteAAjouter=text;
        this.num=num;
        this.hashMap=hashMap;
        
    }

    @Override
    public void execute() {
        fichier.ajouterTexteAuFichier2(nomFichier, texteAAjouter, num, hashMap);
    }
    
}
