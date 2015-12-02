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
public class KundAvtal {
    
    private Kund kund;
    private double andel;
    private Faktura faktura;
    private double antalTimmar;
   
    
    public KundAvtal(Kund kund, double andel, double pris,double antalTimmar) {
        this.kund = kund;
        this.andel = andel;
        this.antalTimmar = antalTimmar;
        this.faktura = new Faktura(pris);
    }

    public double getAntalTimmar() {
        return antalTimmar;
    }

    
    public double getAndel() {
        return andel;
    }

    public Kund getKund() {
        return kund;
    }

    public void setAndel(double andel) {
        this.andel = andel;
    }

    public void setKund(Kund kund) {
        this.kund = kund;
    }

    public Faktura getFaktura() {
        return faktura;
    }

    
    
    
    
}
