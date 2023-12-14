import java.util.*;
 
public class DijkstraAlgorithmus {
    private static List<String> ubahnStationen;
    private static int[][] ubahnKanten;
    private static String[][] ubahnLinien;
    private static boolean[] besucht;
    private static int[] entfernung;
    private static int[] vorgaenger;
    private static PriorityQueue<Integer> warteschlange;
 
    // Funktion zur Initialisierung der U-Bahn-Stationen und -Verbindungen
    public static void startBerechnung(String startStation, String endStation) {
 
 
 
        // Beispiel-U-Bahn-Stationen und -Verbindungen initialisieren
        ubahnStationen = Arrays.asList(
            // 0            1           2                   3               4
            "Oberlaa", "Karlsplatz", "Stephansplatz", "Schwedenplatz", "Praterstern",
            // 5            6               7               8               9 
            "Leopoldau", "Volkstheater", "Schottenring", "Seestadt", "Simmering",
            // 10           11             12               13          14
            "Landstraße", "Westbahnhof", "Ottakring", "Hütteldorf", "Längenfeldgasse",
            // 15               16          17              18
            "Spittelau", "Heiligenstadt", "Floridsdorf", "Siebenhirten"
        );
 
        int anzahlStationen = ubahnStationen.size();
 
        ubahnKanten = new int[anzahlStationen][anzahlStationen]; 
        ubahnLinien = new String[anzahlStationen][anzahlStationen];
 
        // Kanten für U1
        hinzufuegenU_Bahn_Kante(0, 1, 13, "U1"); // Oberlaa <--> Karlsplatz
        hinzufuegenU_Bahn_Kante(1, 2, 3, "U1"); // Karlsplatz <--> Stephansplatz
        hinzufuegenU_Bahn_Kante(2, 3, 2, "U1"); // Stephansplatz <--> Schwedenplatz
        hinzufuegenU_Bahn_Kante(3, 4, 3, "U1"); // Schwedenplatz <--> Praterstern
        hinzufuegenU_Bahn_Kante(4, 5, 15, "U1"); // Praterstern <--> Leopoldau
 
        // Kanten für U2
        hinzufuegenU_Bahn_Kante(1, 6, 9, "U2"); // Karlsplatz <--> Volkstheater
        hinzufuegenU_Bahn_Kante(6, 7, 7, "U2"); // Volkstheater <--> Schottenring
        hinzufuegenU_Bahn_Kante(7, 4, 4, "U2"); // Schottenring <--> Praterstern
        hinzufuegenU_Bahn_Kante(4, 8, 19, "U2"); // Praterstern <--> Seestadt
 
        // Kanten für U3
        hinzufuegenU_Bahn_Kante(9, 10, 10, "U3"); // Simmering <--> Landstraße
        hinzufuegenU_Bahn_Kante(10, 2, 4, "U3"); // Landstraße <--> Stephansplatz
        hinzufuegenU_Bahn_Kante(2, 6, 6, "U3"); // Stephansplatz <--> Volkstheater
        hinzufuegenU_Bahn_Kante(6, 11, 5, "U3"); // Volkstheater <--> Westbahnhof
        hinzufuegenU_Bahn_Kante(11, 12, 7, "U3"); // Westbahnhof <--> Ottakring
 
        // Kanten für U4
        hinzufuegenU_Bahn_Kante(13, 14, 11, "U4"); // Hütteldorf <--> Längenfeldgasse
        hinzufuegenU_Bahn_Kante(14, 1, 8, "U4"); // Längenfeldgasse <--> Karlsplatz
        hinzufuegenU_Bahn_Kante(1, 10, 4, "U4"); // Karlsplatz <--> Landstraße
        hinzufuegenU_Bahn_Kante(10, 3, 2, "U4"); // Landstraße <--> Schwedenplatz
        hinzufuegenU_Bahn_Kante(3, 7, 3, "U4"); // Schwedenplatz <--> Schottenring
        hinzufuegenU_Bahn_Kante(7, 15, 4, "U4"); // Schottenring <--> Spittelau
        hinzufuegenU_Bahn_Kante(15, 16, 2, "U4"); // Spittelau <--> Heiligenstadt
 
        // Kanten für U6
        hinzufuegenU_Bahn_Kante(17, 15, 7, "U6"); // Floridsdorf <--> Spittelau
        hinzufuegenU_Bahn_Kante(15, 11, 12, "U6"); // Spittelau <--> Westbahnhof
        hinzufuegenU_Bahn_Kante(11, 14, 4, "U6"); // Westbahnhof <--> Längenfeldgasse
        hinzufuegenU_Bahn_Kante(14, 18, 12, "U6"); // Längenfeldgasse <--> Siebenhirten
 
 
        // Umwandlung der Start- und Endstationen in korrespondierende Indizes
        int startKnoten = ubahnStationen.indexOf(startStation);
        int endKnoten = ubahnStationen.indexOf(endStation);
 
        if (startKnoten == -1 || endKnoten == -1) {
            System.out.println("Fehler: Start- oder Endstation nicht gefunden.");
            return;
        }
 
        // Initialisierung der Datenstrukturen
        besucht = new boolean[anzahlStationen];
        entfernung = new int[anzahlStationen];
        vorgaenger = new int[anzahlStationen];
        warteschlange = new PriorityQueue<>(Comparator.comparingInt(i -> entfernung[i]));
 
        // Dijkstra-Algorithmus aufrufen
        djkstra(startKnoten, endKnoten);
    }
 
 
    private static void hinzufuegenU_Bahn_Kante(int start, int ende, int gewicht, String linie) {
        ubahnKanten[start][ende] = gewicht;
        ubahnLinien[start][ende] = linie;
        // Gegenrichtung hinzufügen
        ubahnKanten[ende][start] = gewicht;
        ubahnLinien[ende][start] = linie;
    }
 
 
    // Dijkstra-Algorithmus
    private static void djkstra(int startKnoten, int endKnoten) {
        Arrays.fill(entfernung, Integer.MAX_VALUE);
        Arrays.fill(vorgaenger, -1);
 
        entfernung[startKnoten] = 0;
        warteschlange.add(startKnoten);
 
        while (!warteschlange.isEmpty()) {
            int aktuellerKnoten = warteschlange.poll();
 
            for (int nachbar = 0; nachbar < ubahnKanten.length; nachbar++) {
                if (ubahnKanten[aktuellerKnoten][nachbar] > 0) {
                    int alternativeEntfernung = entfernung[aktuellerKnoten] + ubahnKanten[aktuellerKnoten][nachbar];
                    
                    if (alternativeEntfernung < entfernung[nachbar]) {
                        // Update, wenn kürzere Entfernung gefunden wurde
                        entfernung[nachbar] = alternativeEntfernung;
                        vorgaenger[nachbar] = aktuellerKnoten;
                        warteschlange.add(nachbar);
                    }
                }
            }
        }
 
        // Am Ende von Dijkstra die Funktion zum Drucken des Weges aufrufen
        druckeWeg(startKnoten, endKnoten);
    }
 
    // Funktion zum Drucken des Weges
    private static void druckeWeg(int startKnoten, int endKnoten) {
        LinkedList<Integer> weg = new LinkedList<>();
        LinkedList<String> linien = new LinkedList<>(); // Neue Liste für die U-Bahn-Linien
        int aktuellerKnoten = endKnoten;
        int letzteStation = 0;
        int endEntfernung = 0;
 
        while (aktuellerKnoten != -1) {
            weg.addFirst(aktuellerKnoten);
            aktuellerKnoten = vorgaenger[aktuellerKnoten];
        }
 
        System.out.println("Kürzester Weg von " + ubahnStationen.get(startKnoten) + " nach " + ubahnStationen.get(endKnoten) + ":");
        for (int i = 0; i < weg.size(); i++) {
            if (i < weg.size() - 1) {
                int aktuelleStation = weg.get(i);
                int naechsteStation = weg.get(i + 1);
                int druckentfvar = 0;
 
 
 
                //System.out.println(entfernung[weg.get(i)]);
 
 
                // U-Bahn-Linie für die aktuelle Kante hinzufügen
                String linie = ubahnLinien[aktuelleStation][naechsteStation];
                linien.add(linie);
 
                // Ausgabe der Linie vor der ersten Station
                if (i == 0) {
                    String totallinie = linie;
                    System.out.print(ubahnStationen.get(weg.get(i)) + " -- " + linie + " -- ");
                    letzteStation = aktuelleStation;
 
               } else {
 
 
                    if (!linie.equals(ubahnLinien[weg.get(i - 1)][weg.get(i)]) ) {
 
 
                        druckentfvar = entfernung[aktuelleStation] - entfernung[letzteStation];
 
                        System.out.print(druckentfvar + " min --> " + ubahnStationen.get(weg.get(i)) + " -- " + linie + " -- ");
                        letzteStation = aktuelleStation;
 
                        
                        endEntfernung = entfernung[endKnoten] - entfernung[aktuelleStation];
                        
                        
 
 
                    }
                }
            }
        }
 
        if( ubahnStationen.get(letzteStation) == ubahnStationen.get(startKnoten)) {
            endEntfernung = entfernung[endKnoten];
        }
 
        System.out.println(endEntfernung + " min --> " + ubahnStationen.get(endKnoten));
        System.out.println("\nGesamte Entfernung: " + entfernung[endKnoten] + " min");
 
 
        System.out.println("Voller Weg von " + ubahnStationen.get(startKnoten) + " nach " + ubahnStationen.get(endKnoten) + ":");
        for (int i = 0; i < weg.size(); i++) {
            System.out.print(ubahnStationen.get(weg.get(i)));
            if (i < weg.size() - 1) {
                System.out.print(" --> ");
            }
        }
 
        System.out.println("");
        System.out.println("");
 
    }
 
}