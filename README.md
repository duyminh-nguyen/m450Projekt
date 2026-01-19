# Testkonzept – MiniShop (M450)

## 1. Ziel
Ziel dieses Projekts ist die Entwicklung einer kleinen Backend-Logik nach dem
**Test Driven Development (TDD)**-Ansatz.  
Tests stehen im Vordergrund, werden automatisiert ausgeführt und Schnittstellen
werden mit **Mocking** getestet.

## 2. System & Scope
Das System bildet die Kernlogik eines einfachen Shops ab:
- Produkte mit SKU, Preis und Lagerbestand
- Bestellungen reduzieren und erhöhen den Lagerbestand
- Automatische Nachbestellung bei Unterschreiten eines Schwellwerts

Nicht Bestandteil des Projekts:
- Datenbank / Persistenz
- REST API oder UI
- Authentifizierung / Payment

## 3. Architektur & zu testende Komponenten
**Pakete:**
- `domain`: `Product`
- `service`: `InventoryService`, `RestockService`
- `ports`: `SupplierGateway`

**Zu testende Komponenten:**
- `InventoryService` (Order- und Lagerlogik)
- `RestockService` (Nachbestelllogik)

**Gemockte Schnittstelle:**
- `SupplierGateway` (Mockito)

## 4. Teststrategie
- **Unit Tests** für Domain- und Service-Logik
- **Komponententests** für Services mit mehreren Objekten
- Tests werden zuerst geschrieben (**Red–Green–Refactor**)

**Frameworks & Tools:**
- JUnit 5
- Mockito
- Maven Surefire
- JaCoCo

## 5. Testfälle (Auswahl)
- Produkt setzt initialen Lagerbestand
- Order reduziert Lagerbestand
- Order schlägt fehl bei zu wenig Lagerbestand
- Order-Storno stellt Lagerbestand wieder her
- Restock ruft Supplier bei tiefem Lagerbestand auf
- Kein Restock bei ausreichendem Lagerbestand

## 6. Automatisierung & Reports
- Tests laufen lokal mit `mvn clean verify`
- CI-Pipeline mit GitHub Actions bei jedem Push
- Test- und Coverage-Reports werden automatisch erzeugt:
  - Surefire: `target/surefire-reports`
  - JaCoCo: `target/site/jacoco/index.html`

## 7. TDD-Nachweis
Die Commit-Historie zeigt, dass Tests vor der Implementierung geschrieben wurden
(z.B. `test:` vor `feat:` Commits).

