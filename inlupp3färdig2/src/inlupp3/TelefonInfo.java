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
public class TelefonInfo {
    
    private String telefonnummer;
    private String beskrivning;


    public TelefonInfo(String telefonnummer, String beskrivning) {
        this.telefonnummer = telefonnummer;
        this.beskrivning = beskrivning;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public String getBeskrivning() {
        return beskrivning;
    }
    
    
}
