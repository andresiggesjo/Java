/*
* TSP_GA.java
* Create a tour and evolve a solution
*/

package tsp;

import java.util.ArrayList;

public class TSP_GA {

    public static void main(String[] args) {

        // Create and add our cities
        /*
        for (int i = 0; i < 100; i++) {
            City city = new City();
            TourManager.addCity(city);
        }*/
        City city1 = new City(565,575);
        City city2 = new City(25, 185);
        City city3 = new City(345, 750);
        City city4 = new City(945,685);
        City city5 = new City(845, 655);
        City city6 = new City(880, 660);
        City city7 = new City(25,230);
        City city8 = new City(525, 1000);
        City city9 = new City(580, 1175);
        City city10 = new City(650,1130);
        City city11 = new City(1605, 620);
        City city12 = new City(1220, 580);
        City city13 = new City(1465,200);
        City city14 = new City(1530, 5);
        City city15 = new City(845, 680);
        
        City city16 = new City(725,370);
        City city17 = new City(145, 665);
        City city18 = new City(415, 635);
        City city19 = new City(510,875);
        City city20 = new City(560, 365);
        City city21 = new City(300, 465);
        City city22 = new City(520,585);
        City city23 = new City(480, 415);
        City city24 = new City(835, 625);
        City city25 = new City(975,580);
        City city26 = new City(1215, 245);
        City city27 = new City(1320, 315);
        City city28 = new City(1250,400);
        City city29 = new City(660, 180);
        City city30 = new City(410, 250);
        
        City city31 = new City(420,555);
        City city32 = new City(575, 665);
        City city33 = new City(1150, 1160);
        City city34 = new City(700,580);
        City city35 = new City(685, 595);
        City city36 = new City(685, 610);
        City city37 = new City(770,610);
        City city38 = new City(795, 645);
        City city39 = new City(720, 635);
        City city40 = new City(760,650);
        City city41 = new City(475, 960);
        City city42 = new City(95, 260);
        City city43 = new City(875,920);
        City city44 = new City(700, 500);
        City city45 = new City(555, 815);
        
        City city46 = new City(830,485);
        City city47 = new City(1170, 65);
        City city48 = new City(830, 610);
        City city49 = new City(605,625);
        City city50 = new City(595, 360);
        City city51 = new City(1340, 725);
        City city52 = new City(1740, 245);
        
        TourManager.addCity(city1);
        TourManager.addCity(city2);
        TourManager.addCity(city3);
        TourManager.addCity(city4);
        TourManager.addCity(city5);
        TourManager.addCity(city6);
        TourManager.addCity(city7);
        TourManager.addCity(city8);
        TourManager.addCity(city9);
        TourManager.addCity(city10);
        TourManager.addCity(city11);
        TourManager.addCity(city12);
        TourManager.addCity(city13);
        TourManager.addCity(city14);
        TourManager.addCity(city15);
        TourManager.addCity(city16);
        TourManager.addCity(city17);
        TourManager.addCity(city18);
        TourManager.addCity(city19);
        TourManager.addCity(city20);
        TourManager.addCity(city21);
        TourManager.addCity(city22);
        TourManager.addCity(city23);
        TourManager.addCity(city24);
        TourManager.addCity(city25);
        TourManager.addCity(city26);
        TourManager.addCity(city27);
        TourManager.addCity(city28);
        TourManager.addCity(city29);
        TourManager.addCity(city30);
        TourManager.addCity(city31);
        TourManager.addCity(city32);
        TourManager.addCity(city33);
        TourManager.addCity(city34);
        TourManager.addCity(city35);
        TourManager.addCity(city36);
        TourManager.addCity(city37);
        TourManager.addCity(city38);
        TourManager.addCity(city39);
        TourManager.addCity(city40);
        TourManager.addCity(city41);
        TourManager.addCity(city42);
        TourManager.addCity(city43);
        TourManager.addCity(city44);
        TourManager.addCity(city45);
        TourManager.addCity(city46);
        TourManager.addCity(city47);
        TourManager.addCity(city48);
        TourManager.addCity(city49);
        TourManager.addCity(city50);
        TourManager.addCity(city51);
        TourManager.addCity(city52);
        
        
        
        ArrayList<City> OptimumList = new ArrayList<City>();
        OptimumList.add(city1);
        OptimumList.add(city49);
        OptimumList.add(city32);
        OptimumList.add(city45);
        OptimumList.add(city19);
        OptimumList.add(city41);
        OptimumList.add(city8);
        OptimumList.add(city9);
        OptimumList.add(city10);
        OptimumList.add(city43);
        OptimumList.add(city33);
        OptimumList.add(city51);
        OptimumList.add(city11);
        OptimumList.add(city52);
        OptimumList.add(city14);
        OptimumList.add(city13);
        OptimumList.add(city47);
        OptimumList.add(city26);
        OptimumList.add(city27);
        OptimumList.add(city28);
        OptimumList.add(city12);
        OptimumList.add(city25);
        OptimumList.add(city4);
        OptimumList.add(city6);
        OptimumList.add(city15);
        OptimumList.add(city5);
        OptimumList.add(city24);
        OptimumList.add(city48);
        OptimumList.add(city38);
        OptimumList.add(city37);
        OptimumList.add(city40);
        OptimumList.add(city39);
        OptimumList.add(city36);
        OptimumList.add(city35);
        OptimumList.add(city34);
        OptimumList.add(city44);
        OptimumList.add(city46);
        OptimumList.add(city16);
        OptimumList.add(city29);
        OptimumList.add(city50);
        OptimumList.add(city20);
        OptimumList.add(city23);
        OptimumList.add(city30);
        OptimumList.add(city2);
        OptimumList.add(city7);
        OptimumList.add(city42);
        OptimumList.add(city21);
        OptimumList.add(city17);
        OptimumList.add(city3);
        OptimumList.add(city18);
        OptimumList.add(city31);
        OptimumList.add(city22);

        Tour optimum = new Tour(OptimumList);
        System.out.println(optimum.getDistance());

        

       
        // Initialize population
        Population pop = new Population(250, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());


        // Evolve population for 100 generations
        pop = GA.evolvePopulation(pop);
        for (int i = 0; i < 400; i++) {
            pop = GA.evolvePopulation(pop);
        }

        // Print final results
        System.out.println("Finished");
        System.out.println("Final distance: " + pop.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(pop.getFittest());
    }
}