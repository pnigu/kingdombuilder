# Kingdom Builder - Konzept

## 1. Spielvision
Du entwickelst ein:
**Rundenbasiertes Global-Strategy-Simulationsspiel**

Der Spieler kontrolliert ein Land und führt es durch:
* Expansion
* Wirtschaftswachstum
* Kriege
* politische Entwicklungen
* Diplomatie
* technologische Entwicklung (später optional)

Das Spiel simuliert eine Welt, die sich unabhängig vom Spieler entwickelt (KI-Staaten).

## 2. Grundprinzipien des Spiels
### 2.1 Simulation statt Skript
Die Welt funktioniert nicht über feste Events, sondern über Systeme:
* Wirtschaft erzeugt Geld
* Bevölkerung erzeugt Steuern
* Armeen erzeugen Kosten
* Politik verändert Effizienz

Alles ist miteinander verbunden.

### 2.2 Rundenbasiert
Jede Runde ist ein „Jahr“ oder „Monat“:
Ablauf:
1. Wirtschaft wird berechnet
2. Ressourcen werden verteilt
3. Städte wachsen
4. Armeen erhalten Versorgung
5. Krieg wird berechnet
6. Diplomatie aktualisiert sich
7. KI trifft Entscheidungen

## 3. Welt-System (Map)
### 3.1 Struktur
Die Welt besteht aus einer Rasterkarte (Tile Map).
Jede Zelle hat:
* Terrain-Typ (Wald, Gebirge, Wasser etc.)
* Besitz (welches Land kontrolliert es)
* Gebäude (Stadt, Farm, Festung etc.)
* Ressourcen (optional)

### 3.2 Bedeutung der Map
Die Map ist NICHT nur Grafik, sondern:
* wirtschaftliche Grundlage
* militärische Position
* strategischer Vorteil

## 4. Länder-System
Ein Land ist die zentrale Spieler- und KI-Entität.
Eigenschaften:
* Name
* Regierung
* Stabilität
* Geld
* Bevölkerung
* Armee
* Städte
* Beziehungen zu anderen Ländern

Verhalten eines Landes: Ein Land „denkt“ nicht direkt, sondern reagiert über Systeme:
* hohe Steuern → Unzufriedenheit steigt
* schlechte Wirtschaft → Armee schrumpft
* Krieg → Stabilität sinkt

## 5. Wirtschafts-System
### Grundidee
Wirtschaft ist ein Kreislauf:
* **Einnahmen:** Steuern aus Bevölkerung, Produktion aus Städten, Handel (später)
* **Ausgaben:** Armee-Unterhalt, Stadtkosten, Verwaltung, Kriegsverluste

### Wichtiges Prinzip
Wenn Ausgaben > Einnahmen → Krise
Folgen: Unruhen, Armee schwächer, Städte wachsen langsamer

## 6. Städte-System
Städte sind die Produktionszentren.
Funktionen einer Stadt:
* erzeugt Bevölkerung
* erzeugt Geld
* baut Einheiten
* produziert Gebäude
* kontrolliert umliegende Tiles

Wachstum: Städte wachsen abhängig von: Nahrung (oder Ressourcen), Stabilität des Landes, Regierungsform.

## 7. Militär-System
### Struktur
Militär besteht aus Einheiten, die zusammen Armeen bilden.
Aufgaben: Gebiet verteidigen, andere Länder angreifen, Städte belagern, Kontrolle über Map sichern.

### Logik
Stärke hängt ab von: Anzahl Einheiten, Terrain-Vorteilen, Versorgung, Moral, Technologie (später).

## 8. Kriegssystem
Krieg ist kein „Instant-Kampf“, sondern ein Prozess:
* **Schritte:** Armee bewegt sich auf Ziel -> Gebiet wird besetzt -> Kampf in Regionen -> Belagerung von Städten -> Kontrolle wechselt.
* **Ergebnis-Faktoren:** Stärke, Position, Versorgung, Stabilität des Gegners.

## 9. Politik-System
Politik bestimmt, wie effizient ein Land funktioniert.
Regierungstypen: Demokratie, Monarchie, Diktatur, Republik.
Auswirkungen: Steuern, Zufriedenheit, Stabilität, Kriegsmoral.
Beispiel:
* Diktatur: hohe Effizienz, aber Rebellion-Risiko
* Demokratie: stabil, aber langsamer in Kriegen

## 10. Diplomatie-System
Länder können miteinander interagieren: Frieden, Krieg, Bündnisse, Handel, Nichtangriffspakte.
Beziehungen: Jedes Land hat einen „Beziehungswert“ zu anderen:
* +100 = Allianz
* 0 = neutral
* -100 = Krieg

## 11. KI-System
Die KI ist kein „Cheat“, sondern folgt Regeln:
* Entscheidungslogik: Wirtschaft prüfen, Bedrohungen analysieren, Expansion planen, Krieg nur bei Vorteil, Stabilität priorisieren.
* KI-Verhalten: schwache Länder expandieren aggressiv, starke Länder stabilisieren sich, kriselnde Länder verteidigen nur.

## 12. Ressourcen-System (optional später)
Ressourcen können die Wirtschaft erweitern: Holz, Eisen, Nahrung, Gold.
Diese beeinflussen: Armee, Gebäude, Wachstum.

## 13. Spielziel
Kein festes Ziel nötig, aber typische Ziele: Welt dominieren, wirtschaftliches Imperium, stabile Zivilisation, diplomatisches Netzwerk.

## 14. System-Architektur (wichtig für später in Java)
Das Spiel ist modular aufgebaut:
Hauptsysteme:
* Map-System
* Wirtschaftssystem
* Politiksystem
* Militärsystem
* Diplomatiesystem
* KI-System
* Turn-System (Runden-Controller)

**Grundregel:** Jedes System arbeitet unabhängig, aber liest dieselben Daten.

## 15. Entwicklungsstrategie
* **Phase 1 (Minimal spielbar):** Map, Länder, Städte, Einkommen
* **Phase 2:** Armee, Krieg
* **Phase 3:** Politik, Diplomatie
* **Phase 4:** KI
* **Phase 5:** Balance + UI

## 16. Wichtigste Designentscheidung
Erst Simulation fertig machen, dann UI!
Denn:
* UI ohne Simulation = sinnlos
* Simulation ohne UI = testbar
