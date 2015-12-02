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
public class Uppdrag {
    
    
    private String datum;
    private String beskrivning;
    private int antalTimmar;
    private ArrayList<KundAvtal> kundAndelList;
    private final int kostnad = 9999;
    private final double totalAndel = 100;
    
    
    public Uppdrag(String datum, String beskrivning, int antalTimmar){
        
        this.datum = datum;
        this.beskrivning = beskrivning;
        this.antalTimmar = antalTimmar;
        this.kundAndelList = new ArrayList<KundAvtal>();   
        
        
    }
    
    public void addKundToUppdrag(Kund kund,double andel){
        double pris = (antalTimmar * kostnad) * (andel/100);
        double antalTim = this.antalTimmar * (andel/100);
        KundAvtal kundAvtal = new KundAvtal(kund, andel,pris,antalTim);
        
        this.kundAndelList.add(kundAvtal);
    }
      public String toString(){
        String returnValue =  "Datum:  " +datum + " ,Beskrivning: " + beskrivning + " ,Antal Timmar: " + antalTimmar + " ,Kunder:\n" ;
        
        for (int i = 0; i < kundAndelList.size(); i++) {
            
           KundAvtal kundandel = kundAndelList.get(i); // Andelen finns här
           Kund kund = kundandel.getKund();
           returnValue += "Namn: " + kund.getName() + " Andel: " + kundandel.getAndel() + "\n";
           
        }
       
        
        return returnValue;
    }

    boolean contains(Kund minKund) {
        
       for (int i = 0; i < kundAndelList.size(); i++) {
            
           KundAvtal kundandel = kundAndelList.get(i); 
           Kund kund = kundandel.getKund();
           if(minKund.getName().equalsIgnoreCase(kund.getName())) {
               return true;
               
               
           }
           
        }
       return false;
    }

    KundAvtal getAvtalbyKund(Kund minKund) {
        for (int i = 0; i < kundAndelList.size(); i++) {
            
           KundAvtal kundavtal = kundAndelList.get(i); 
           Kund kund = kundavtal.getKund();
           if(minKund.getName().equalsIgnoreCase(kund.getName())) {
               return kundavtal;
               
               
           }
           
        }
        return null;
    }

    public String getBeskrivning() {
        return beskrivning;
    }

    public int getAntalTimmar() {
        return antalTimmar;
    }

    public String getDatum() {
        return datum;
    }

    public int getKostnad() {
        return kostnad;
    }

    public void setAntalTimmar(int antalTimmar) {
        this.antalTimmar = antalTimmar;
    }

    public void setBeskrivning(String beskrivning) {
        this.beskrivning = beskrivning;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    
    
    
    
}
