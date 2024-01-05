# Implementierung Dijkstra Algorithmus U-Bahn-Netz Wien

## Beschreibung
Dieses Projekt enthält eine Java-Implementierung des Dijkstra-Algorithmus, der auf das U-Bahn-Netz von Wien angewendet wird. Das Ziel ist es, den kürzesten Weg zwischen zwei U-Bahn-Stationen zu berechnen.

![image](https://github.com/Koroneiki/dijkstra-ubahn_wien/assets/101889814/9369af5c-80b4-4d36-8f20-aafc31a37e07)

Zur Vereinfachung wurden lediglich Stationen berücksichtigt die Umsteigestationen oder Start- bzw. Endstationen sind. Dabei bleibt die Grundstruktur des Netzes erhalten, da lediglich Zwischenstationen nicht berücksichtigt werden.

Dabei entsteht eine Gesamtanzahl von 19 Stationen.

## Realisierung

Der Programmcode wurde wesentlich in 3 Bereichen unterteilt:
- Initialisierung
- Dijkstra-Algorithmus
- Konsolendruck

### 1. Initalisierung
  - Initalisierung der Stationen (Knoten) mit Arraylist
     
  - Initalisierung der Verbindungen (Kanten) mit Matrix
     ```
         // Kanten für U1 (start, ende, gewicht, linie)
        hinzufuegenU_Bahn_Kante(0, 1, 13, "U1"); // Oberlaa <--> Karlsplatz
     ```
     Zwei parallele Matrizen werden hierbei durch die Hilfsfunktion für Gewicht und Liniennummer ergänzt.

  - Initalisierung von Datenstrukturen und Umwandlungen durch Methode startBerechnung

### 2. Dijkstra-Algorithmus
  - Initialisierung von Arrays und Startknoten
  - Haupt-Schleife: Solange die Warteschlange nicht leer ist, wird der Algorithmus fortgesetzt. Der aktuelle Knoten wird aus der Warteschlange genommen.
  - Nachbarknoten überprüfen: Der Algorithmus überprüft die Nachbarknoten des aktuellen Knotens und berechnet alternative Entfernungen.
  - Entfernungen aktualisieren: Falls eine kürzere Entfernung gefunden wird, wird die Entfernung und der Vorgängerknoten aktualisiert, und der Nachbarknoten wird zur Warteschlange hinzugefügt.

### 3. Konsolendruck
  - Der Weg und die Linien werden vom Endknoten zum Startknoten in Listen gespeichert

  Iterierung über die Liste mittels for-Schleife zur Identifikation von Umstiegsknoten (Vergleich der Linien)
  ```
  if (!ubahnLinien[weg.get(i)][weg.get(i + 1)].equals(ubahnLinien[weg.get(i - 1)][weg.get(i)]) )
  ```
  Die Umstiegsknoten werden mitsamt ihren Liniendaten sowie der Zeitangabe (Gewicht) gedruckt.

  Ebenfalls wird zusätzlich der gesamte Weg mit allen Stationen gedruckt, indem über die Weg-Liste iteriert wird.

  Finale Ausgabe:
  ```
  Kürzester Weg von Seestadt nach Hütteldorf:
  Seestadt -- U2 -- 19 min --> Praterstern -- U1 -- 8 min --> Karlsplatz -- U4 -- 19 min --> Hütteldorf

  Gesamte Entfernung: 46 min

  Voller Weg von Seestadt nach Hütteldorf:
  Seestadt --> Praterstern --> Schwedenplatz --> Stephansplatz --> Karlsplatz --> Längenfeldgasse --> Hütteldorf
  ```



## Verwendung

Durch das Ausführen von DijkstraAlgorithmus.java wird die Hauptmethode gestartet und der Benutzer kann aus den 19 Stationen, welche in die Konsole gedruckt werden, auswählen.
