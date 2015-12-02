/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inlupp3;

import java.util.ArrayList;

/**
 *
 * @author André
 */
public class Kund {
    
    private String namn;
    private String adress;
    private ArrayList<TelefonInfo> telefonnummer;
    
    
    
    public Kund(String namn, String adress){
        this.namn = namn;
        this.adress = adress;
        this.telefonnummer = new ArrayList<TelefonInfo>();

        
        
    }
    
    public String getName(){
        return namn;
    }
    
    public String getAdress(){
        return adress;
    }

    public ArrayList<TelefonInfo> getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(ArrayList<TelefonInfo> telefonnummer) {
        this.telefonnummer = telefonnummer;
    }
    
    
    
 
    
    public String toString(){
        String returnValue =  "Namn:" + namn + " "+"Adress:" + adress ;
        
        if(!telefonnummer.isEmpty()){
           for (int i = 0; i < telefonnummer.size(); i++) {
                    TelefonInfo telefonnr = telefonnummer.get(i);
                    returnValue += "\nTelefonnummer: " + telefonnr.getTelefonnummer() + " "+ telefonnr.getBeskrivning();
                }
            
        }
        
        return returnValue;
        
        
        
    }
    
    public void addNumber(String telefonNummer, String beskrivning){
        
        TelefonInfo telefonummer = new TelefonInfo(telefonNummer, beskrivning);
        
        telefonnummer.add(telefonummer);
    }

    
    
    
    // Returnerar -1 om den är mindre än
    // Returnerar 0 om den är lika med
    // Returnerar 1 om den är större än
    int compareTo(Kund k2) {
        return this.getName().compareTo(k2.getName());
    }
    
 
    
}
