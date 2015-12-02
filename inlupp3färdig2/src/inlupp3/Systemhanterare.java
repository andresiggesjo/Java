/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inlupp3;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;

/**
 *
 * @author André
 */
public class Systemhanterare {

    ArrayList<Kund> kundLista;
    Scanner input;
    ArrayList<Uppdrag> uppdragsLista;

    public Systemhanterare() {
        kundLista = new ArrayList<>();
        input = new Scanner(System.in);
        uppdragsLista = new ArrayList<>();
    }

    public String inLäsningString(String prompt) {

        System.out.println(prompt);
        String inläsning = input.nextLine();

        if (inläsning.trim().isEmpty()) {
            return "Du måste fylla i något.\n" + prompt;

        }

        return inläsning;

    }

    public int inLäsningInt(String prompt) {

        System.out.println(prompt);
        int inläsning = input.nextInt();

        return inläsning;
    }

    public void registreraKund() {

        String namn;
        String adress;
        Boolean namnTaget = false;

        namn = inLäsningString("Kundens namn:");
        adress = inLäsningString("Kundens adress:");

        for (int i = 0; i < kundLista.size(); i++) {
            Kund kund = kundLista.get(i);
            if (kund.getName().equalsIgnoreCase(namn)) {
                namnTaget = true;

            }

        }

        if (namnTaget) {
            System.out.println("Det finns redan en kund med det namnet.");

        } else {
            Kund tillagdKund = new Kund(namn, adress);
            kundLista.add(tillagdKund);
            System.out.println("Kund tillagd.");
        }

    }

    public void raderaKund() {

        boolean hittad = false;
        boolean betald = false;
        String namn;
        Kund minKund = null;

        System.out.println("--------------------------------------------------");
        namn = inLäsningString("Namn på kund som skall tas bort:");
        for (int i = 0; i < kundLista.size(); i++) {
            Kund kund = kundLista.get(i);
            if (kund.getName().equalsIgnoreCase(namn)) {
                minKund = kund;
                hittad = true;

                for (int j = 0; j < uppdragsLista.size(); j++) {
                    Uppdrag uppdrag = uppdragsLista.get(j);

                    if (uppdrag.contains(minKund)) {
                        KundAvtal kundavtal;
                        kundavtal = uppdrag.getAvtalbyKund(minKund);
                        if (kundavtal != null && (kundavtal.getFaktura().isfakturerad() == true)) {
                            betald = true;
                            kundLista.remove(kund);

                        }

                    }

                }

            }
        }
        if (!betald) {
            System.out.println("Kunden har obetalda fakturor.");
        } else {
            System.out.println("Kunden är borttagen.");
        }
        if (!hittad) {
            System.out.println("Det finns ingen kund med det angivna namnet");
        }
        System.out.println("--------------------------------------------------");
    }

    public void läggTillTelefonnummer() {

        boolean hittad = false;
        String namn;
        String telefonnummer;
        String beskrivning;

        namn = inLäsningString("Namn på den kunden du vill lägga till telefonnummer på: ");
        for (Kund kundlista : kundLista) {

            if (namn.equalsIgnoreCase(kundlista.getName())) {
                telefonnummer = inLäsningString("Skriv in nummret du vill lägga till: ");
                beskrivning = inLäsningString("Lägg till en beskrivning på nummret: ");
                kundlista.addNumber(telefonnummer, beskrivning);

            }
        }
        if (!hittad) {
            System.out.println("Det finns ingen kund med det angivna namnet");
        }

    }

    public void läggTillUppdrag() {

        boolean hittad = false;
        String beskrivning;
        String datum;
        int antalTimmar;
        int andel;
        final double totalAndel = 100;
        String namn;
        double kvarvarandeAndel;
        

        beskrivning = inLäsningString("Lägg till en uppdragsbeskrivning: ");
        datum = inLäsningString("Lägg till ett datum(YYYY-MM-DD): ");
        antalTimmar = inLäsningInt("Lägg till antal timmar uppdraget tar: ");
        input.nextLine();
        

        namn = inLäsningString("Lägg till en person till uppdraget.");
        andel = inLäsningInt("Lägg till personens andel");
        if (andel > 100) {
            System.out.println("Andelen kan inte vara mer än 100%");
        }
        input.nextLine();
        for (Kund kundlista : kundLista) {

            if (namn.equalsIgnoreCase(kundlista.getName())) {
                hittad = true;
                Uppdrag tillagtUppdrag = new Uppdrag(datum, beskrivning, antalTimmar);
                uppdragsLista.add(tillagtUppdrag);
                tillagtUppdrag.addKundToUppdrag(kundlista, andel);
                kvarvarandeAndel = totalAndel - andel;
                if (kvarvarandeAndel > 0) {
                    System.out.println(kvarvarandeAndel + "% andel av uppdrag finns kvar");
                    System.out.println("Andelen är under 100%, lägg till fler kunder.");
                    namn = inLäsningString("Lägg till en person till uppdraget: ");
                    andel = inLäsningInt("Lägg till personens andel");
                    for (Kund kundlist : kundLista) {
                    if (namn.equalsIgnoreCase(kundlist.getName())) {
                        tillagtUppdrag.addKundToUppdrag(kundlist, andel);
                    }
                    if (totalAndel > 100) {
                        System.out.println("Andelen kan inte vara över 100%");
                    }
                }
                if (kvarvarandeAndel + andel > 100) {
                    System.out.println("Andel är över 100");
                }

                

                }
            }
            
        }
        if (!hittad) {
            System.out.println("Det finns ingen kund med det angivna namnet");
        }
    }

    public void skrivUtFakturor() {
        // Mitt mål är att skriva ut alla fakturor som ligger i kundavtal som ligger i uppdrag  från kundens nam
        // 1. Hitta kunden i kundlistan. 
        // 2. Kunden inte hittas , avsluta här
        // 3. Annars Leta genom alla uppdrag som har den kunden.
        // 4. Varje uppdrag som har den kunden,
        boolean isFound = false;

        String namn = inLäsningString("Kundens namn:");
        Kund minKund = null;
        // 1
        for (int i = 0; i < kundLista.size(); i++) {
            Kund kund = kundLista.get(i);
            if (kund.getName().equalsIgnoreCase(namn)) {
                minKund = kund; // 1.kund hittad
                isFound = true;

            }
        }

        // 2
        if (!isFound) {
            System.out.println("Det finns ingen kund med det namnet.");
        } // 3
        else {
            System.out.println("---------- Faktura för alla uppdrag som ännu inte fakturerats för " + namn + " ------------\n");
            double totaltPris = 0;
            int totaltTimmar = 0;
            for (int i = 0; i < uppdragsLista.size(); i++) {
                Uppdrag uppdrag = uppdragsLista.get(i);
                // 4
                if (uppdrag.contains(minKund)) {
                    KundAvtal kundavtal;
                    kundavtal = uppdrag.getAvtalbyKund(minKund);
                    if (kundavtal != null && (kundavtal.getFaktura().isfakturerad() == false)) {
                        System.out.println("Uppdrag: " + uppdrag.getBeskrivning() + " " + kundavtal.getFaktura().toString());
                        totaltPris += kundavtal.getFaktura().getPris();
                        totaltTimmar += kundavtal.getAntalTimmar();
                        kundavtal.getFaktura().setfakturerad(true);
                    }
                }

            }
            System.out.print("\n");
            System.out.println("Totalt antal timmar: " + totaltTimmar);
            System.out.println("Total kostnad: " + totaltPris);
            System.out.println("----------------------\n");

        }

    }

    public void listaKunder() {

        System.out.println("---------- Alla Kunder ------------\n");
        // Sorterar kundListan , genom att jämföra två kunder åt gånger via functionen compare
        Collections.sort(kundLista, new Comparator<Kund>() {
            public int compare(Kund k1, Kund k2) {
                return k1.compareTo(k2); // använd compareTo i Kundobjketet
            }
        });
        for (int i = 0; i < kundLista.size(); i++) {
            Kund kund = kundLista.get(i);
            System.out.println("--------- Kund " + i + "---------");
            System.out.println(kund.toString());
            System.out.println("----------------------\n");

        }
        System.out.println("----------------------\n");

    }

    public void listaUppdrag() {
        System.out.println("---------- Alla Kunder ------------\n");
        for (int i = 0; i < uppdragsLista.size(); i++) {
            Uppdrag uppdrag = uppdragsLista.get(i);
            System.out.println("--------- Uppdrag " + i + "---------");
            System.out.println(uppdrag.toString());
            System.out.println("----------------------\n");

        }

        System.out.println("----------------------\n");

    }

    public void listaUppdragHosKund() {

        // Mitt mål är att lista ut alla uppdrag från kundens nam
        // 1. Hitta kunden i kundlistan. 
        // 2. Kunden inte hittas , avsluta här
        // 3. Annars Leta genom alla uppdrag som har den kunden.
        // 4. Varje uppdrag som har den kunden, skriv ut
        boolean isFound = false;
        String namn = inLäsningString("Kundens namn:");
        Kund minKund = null;
        // 1
        for (int i = 0; i < kundLista.size(); i++) {
            Kund kund = kundLista.get(i);
            if (kund.getName().equalsIgnoreCase(namn)) {
                minKund = kund; // 1.kund hittad
                isFound = true;

            }
        }

        // 2
        if (!isFound) {
            System.out.println("Det finns ingen kund med det namnet.");
        } // 3
        else {
            System.out.println("---------- Alla uppdrag för " + namn + " ------------\n");
            for (int i = 0; i < uppdragsLista.size(); i++) {
                Uppdrag uppdrag = uppdragsLista.get(i);
                // 4
                if (uppdrag.contains(minKund)) {
                    System.out.println("--------- Uppdrag " + i + "---------");
                    System.out.println(uppdrag.toString());
                    System.out.println("----------------------\n");
                }

            }
            System.out.println("----------------------\n");

        }

    }

    public void listaVärdefullaKunder() {

    }

    public void listaGemensammaTelefonnummer() {

        // ArrayList<String> foundNr = new ArrayList<>();
        // IF kund1.getNr == kund2.getNr
        HashMap<String, ArrayList<Kund>> foundList = new HashMap<String, ArrayList<Kund>>();

        // För varje kund
        for (int i = 0; i < kundLista.size(); i++) {
            Kund kund = kundLista.get(i);

            // Itterera genom alla kunder
            for (int j = 0; j < kundLista.size(); j++) {
                Kund kund2 = kundLista.get(j);

                // Inte sig själv
                if (!kund.equals(kund2)) {

                    // Hämta alla number för båda kunderna
                    ArrayList<TelefonInfo> k1TelefonNr;
                    ArrayList<TelefonInfo> k2TelefonNr;
                    k1TelefonNr = kund.getTelefonnummer();
                    k2TelefonNr = kund2.getTelefonnummer();

                    // För varje number av kund1's nr
                    for (int k = 0; k < k1TelefonNr.size(); k++) {
                        TelefonInfo t1 = k1TelefonNr.get(k);

                        // itterera genom alla number för kund2
                        for (int l = 0; l < k2TelefonNr.size(); l++) {
                            TelefonInfo t2 = k2TelefonNr.get(l);

                            // Om numren liknar varandra
                            if (t1.getTelefonnummer().equalsIgnoreCase(t2.getTelefonnummer())) {

                                // Har vi redan upptäckt det?
                                if (foundList.containsKey(t1.getTelefonnummer())) {
                                    ArrayList<Kund> kunder = foundList.get(t1.getTelefonnummer());
                                    boolean exist = false;

                                    // För varje kund i listan
                                    for (int m = 0; m < kunder.size(); m++) {
                                        Kund kundc = kunder.get(m);

                                        // Har vi redan lagt till kunden?
                                        if (kund.equals(kundc)) {
                                            exist = true;
                                        }

                                    }

                                    // Ny upptcäkt
                                    if (!exist) {
                                        kunder.add(kund);
                                        foundList.put(t1.getTelefonnummer(), kunder);
                                    }

                                } // Lägg in numret i hashpmap
                                else {
                                    // Ny upptäckt
                                    ArrayList<Kund> kunder = new ArrayList<>();
                                    kunder.add(kund);
                                    foundList.put(t1.getTelefonnummer(), kunder);
                                }

                            }

                        }

                    }
                }

            }

        }

        Iterator it = foundList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println("------------------------");
            System.out.println("Number :" + pairs.getKey());

            ArrayList<Kund> kunder = (ArrayList<Kund>) pairs.getValue();
            boolean exist = false;
            for (int m = 0; m < kunder.size(); m++) {
                Kund kundd = kunder.get(m);
                System.out.println("Kund :" + kundd.getName());

            }
            System.out.println("------------------------\n");
            it.remove(); // avoids a ConcurrentModificationException
        }

    }

    public void avsluta() {
        System.exit(0);
    }

    public void run() {
        boolean isRunning = true;

        Kund kund1 = new Kund("Anders", "Ormängsgatan 30");
        kund1.addNumber("00000000000", "Mobil");
        kund1.addNumber("073999349342", "Mobil");
        Kund kund2 = new Kund("Mange", "Aprikosgatan 52");
        kund2.addNumber("073999349342", "Mobil");
        kund2.addNumber("083472742334", "Hem");
        Kund kund3 = new Kund("Jonte", "Friherregatan 121");
        kund3.addNumber("073999349342", "Mobil");
        Kund kund4 = new Kund("Ragge", "Natt och dags gränd 6");
        kund4.addNumber("073599329342", "Mobil");
        kund4.addNumber("073799349342", "Mobil");
        kund4.addNumber("00000000000", "Mobil");

        kundLista.add(kund1);
        kundLista.add(kund2);
        kundLista.add(kund3);
        kundLista.add(kund4);

        Uppdrag uppdrag1 = new Uppdrag("2014-07-13", "Aina", 100);
        uppdrag1.addKundToUppdrag(kund1, 42);
        uppdrag1.addKundToUppdrag(kund2, 50);
        uppdrag1.addKundToUppdrag(kund3, 8);
        Uppdrag uppdrag2 = new Uppdrag("2014-01-13", "Aina2", 100);
        uppdrag2.addKundToUppdrag(kund2, 100);
        Uppdrag uppdrag3 = new Uppdrag("2014-05-13", "Aina3", 12);
        uppdrag3.addKundToUppdrag(kund3, 100);
        Uppdrag uppdrag4 = new Uppdrag("2014-03-13", "Aina4", 47);
        uppdrag4.addKundToUppdrag(kund4, 100);

        uppdragsLista.add(uppdrag1);
        uppdragsLista.add(uppdrag2);
        uppdragsLista.add(uppdrag3);
        uppdragsLista.add(uppdrag4);

        System.out.println("Välkommen till programmet!");

        while (isRunning) {

            int val = 0;

            System.out.println("[1] Registrera ny kund.");
            System.out.println("[2] Ta bort kund ur registret.");
            System.out.println("[3] Lägg till telefonnummer till en kund.");
            System.out.println("[4] Lägg till nytt uppdrag.");
            System.out.println("[5] Skriv ut fakturor för uppdrag som inte än fakturerats.");
            System.out.println("[6] Lista samtliga kunder.");
            System.out.println("[7] Lista samtliga uppdrag.");
            System.out.println("[8] Lista samtliga uppdrag för en specifik kund.");
            System.out.println("[9] Lista de mest värdefulla kunderna.");
            System.out.println("[10] Lista gemensamma telefonnummer(?)");
            System.out.println("[11] Avsluta.");

            val = input.nextInt();
            input.nextLine();

            switch (val) {
                case 1:
                    registreraKund();
                    break;
                case 2:
                    raderaKund();
                    break;
                case 3:
                    läggTillTelefonnummer();
                    break;
                case 4:
                    läggTillUppdrag();
                    break;
                case 5:
                    skrivUtFakturor();
                    break;
                case 6:
                    listaKunder();
                    break;
                case 7:
                    listaUppdrag();
                    break;
                case 8:
                    listaUppdragHosKund();
                    break;
                case 9:
                    listaVärdefullaKunder();
                    break;
                case 10:
                    listaGemensammaTelefonnummer();
                    break;
                case 11:
                    avsluta();
                    isRunning = false;
                    break;
            }

        }

    }

}
