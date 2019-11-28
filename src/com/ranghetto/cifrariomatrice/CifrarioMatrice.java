package com.ranghetto.cifrariomatrice;

import java.util.Arrays;

/**
 *
 * @author Matteo Rango
 */
public class CifrarioMatrice {
    
    /*
        +---------------------------+
        | VARIABILI GLOBALI PRIVATE |
        +---------------------------+
    */
    
    private static String   chiaveOrdineAlfabetico,
                            messaggio,
                            messaggioCifrato;
    
    private static int      contatore;
    
    /*  Dichiarazione di queste variabili 'Double' per consetire di 
        arrotondarle per difetto correttamente e successivamente
        fare un cast ad 'Integer' 
    */
    private static double   colonne,
                            righe;
    
    private static char[][] matriceMessaggio;
    
    /*
        +------------------+
        | FUNZIONI PRIVATE |
        +------------------+
    */
    
    private static String ordinaChiaveOrdineAlfabetico(String textToSort){
        char tempArray[] = textToSort.toCharArray(); 
        Arrays.sort(tempArray);
        return new String(tempArray); 
    }
    
    /*
        +--------------------+
        | FUNZIONI PUBBLICHE |
        +--------------------+
    */
    
    public static String codifica(String chiave, String messaggio){
        
        colonne = chiave.length();
        // Math.ceil : arrotondamento per difetto
        righe = Math.ceil(messaggio.length() / colonne);
        
        // OBBLIGATORIO
        contatore = 0;
        messaggioCifrato = "";
        
        matriceMessaggio = new char[(int)colonne][(int)righe];
        
        /*
            Inserimento del messaggio all'interno di una matrice
            di colonne e righe definite sopra, inserendo negli spazi
            vuoti rimanenti il carattere '*'
        */
        for(int i = 0; i<righe; i++){
            for(int j = 0; j<colonne; j++){
                try{
                    matriceMessaggio[j][i] = messaggio.charAt(contatore);
                }catch(StringIndexOutOfBoundsException ex){
                    matriceMessaggio[j][i] = '*';
                }
                contatore++;
            }
        }
        
        // Ordinamento alfabetico della chiave
        chiaveOrdineAlfabetico = ordinaChiaveOrdineAlfabetico(chiave);
        contatore=0; // OBBLIGATORIO
        
        /*
            Trasposizione della matrice secondo la chiave in ordine
            alfabetico e scrittura del messaggio cifrato nella variabile
        */
        for(int i = 0; i<colonne; i++){
            int posizioneCarattereChiaveOrdinata = 
                    chiave.indexOf(chiaveOrdineAlfabetico.charAt(contatore));
            for(int j = 0; j<righe; j++){
                messaggioCifrato += 
                        matriceMessaggio[posizioneCarattereChiaveOrdinata][j];
            }
            contatore++;
        }
        
        return messaggioCifrato;
    }
    
    public static String decodifica(String chiave, String messaggioCifrato){
        
        righe = chiave.length();
        colonne = messaggioCifrato.length() / righe;
        
        chiaveOrdineAlfabetico = ordinaChiaveOrdineAlfabetico(chiave);
        
        matriceMessaggio = new char[(int)colonne][(int)righe];
        
        // OBBLIGATORIO
        contatore=0;
        messaggio = "";
        
        /*
            Inserisco nella matrice il messaggio cifrato
        */
        for(int i = 0; i<righe; i++){
            for(int j = 0; j<colonne; j++){
                matriceMessaggio[j][i] = messaggioCifrato.charAt(contatore);
                contatore++;
            }
        }
        
        contatore=0;// OBBLIGATORIO
        /*
            Leggo la matrice secondo l'ordine della chiave inserita
        */
        for(int i = 0; i<colonne; i++){
            for(int j = 0; j<righe; j++){
                /* 
                    Cerco la posizione della riga corretta 
                    (non la colonna, essendo la matrice trasposta) secondo il 
                    riordinamento della chiave e leggendo quindi l'intera 
                colonna
                */
                int posizioneCarattereChiaveOrdinata = 
                        chiaveOrdineAlfabetico.indexOf(chiave.charAt(j));
                messaggio += 
                        matriceMessaggio[i][posizioneCarattereChiaveOrdinata];
            }
            contatore++;
        }
        
        return messaggio;
        
    }
    
}
