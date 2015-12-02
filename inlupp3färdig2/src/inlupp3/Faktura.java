/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inlupp3;

/**
 *
 * @author Ando
 */
public class Faktura {
   
    
    private double pris;
    private boolean fakturerad = false;
    
    public Faktura(double pris) {
        this.pris = pris;
        
    }
    
    
    public String toString(){
        
        return "Pris: "+pris;
        
        
    }

    public double getPris() {
        return pris;
    }

    public void setPris(double pris) {
        this.pris = pris;
    }
    
    

    public void setfakturerad(boolean ärFakturerad) {
        this.fakturerad = ärFakturerad;
    }

    public boolean isfakturerad() {
        return fakturerad;
    }

    
    
    
    
    
}
