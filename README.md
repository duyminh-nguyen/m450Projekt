```markdown
# Testkonzept – MiniShop Inventory & Orders

## 1. Ziel
Ziel dieses Mini-Projekts ist die Entwicklung einer kleinen Backend-Domain-Logik nach dem **Test Driven Development (TDD)**-Ansatz.  
Der Fokus liegt auf:
- klaren, automatisierten **Unit-/Komponententests**
- **Mocking** von Schnittstellen (Test-Doubles)
- automatisierter Ausführung der Tests inkl. **Reports** (Surefire + JaCoCo) über eine **CI-Pipeline**

## 2. Systemübersicht (Scope)
Das System bildet die Kernlogik eines kleinen Shops ab:
- Produkte besitzen SKU, Preis und Lagerbestand
- Bestellungen können platziert und storniert werden
- Lagerbestand wird bei Bestellungen angepasst
- Bei Unterschreitung eines Schwellenwerts kann Nachschub beim Supplier ausgelöst werden

Nicht Teil des Projekts (bewusst out of scope, um klein zu bleiben):
- Datenbank / Persistenz
- REST API / Controller / UI
- Authentifizierung / Payment
- Concurrency/Parallelität

## 3. Architektur
### 3.1 Komponenten / Pakete
**domain**
- `Product` (sku, price, stock)
- `Order` (id, items, status)
- `OrderItem` (sku, quantity)
- `OrderStatus` (PLACED, CONFIRMED, CANCELLED)

**ports (Schnittstellen)**
- `SupplierGateway` (externe Schnittstelle zur Nachbestellung)

**service**
- `InventoryService` (Order platzieren/stornieren, Stock prüfen)
- `RestockService` (prüft Schwellenwert, ruft SupplierGateway)

### 3.2 Zu testende Komponenten
- `InventoryService` (Business Rules rund um Stock & Orders)
- `RestockService` (Schwellwert-Logik + Supplier-Aufruf)

### 3.3 Zu mockende Schnittstellen
- `SupplierGateway` wird in Tests als Mock verwendet (Mockito)

## 4. Teststrategie
### 4.1 Testarten
- **Unit Tests**: Fokus auf einzelne Methoden/Regeln (ohne externe Systeme)
- **Komponententests**: Service-Logik mit mehreren Domain-Objekten

### 4.2 Tools & Frameworks
- **JUnit 5**: Test Framework
- **Mockito**: Mocking Framework für `SupplierGateway`
- **Maven Surefire**: automatisierte Testausführung und Test-Reports
- **JaCoCo**: Coverage Report (HTML)

### 4.3 Testumgebung
- IntelliJ IDEA (lokale Ausführung)
- Maven (`mvn test` / `mvn verify`)
- CI (GitHub Actions) führt bei Push/PR aus:
  - `mvn -B clean verify`
  - Reports als Artefakte: Surefire + JaCoCo

## 5. Zu testende Features (In-Scope)
### 5.1 Inventory / Orders
1. **Produkt anlegen setzt initialen Lagerbestand**
2. **Order platzieren reduziert Lagerbestand (Reservierung)**
3. **Order platzieren scheitert bei ungenügendem Lagerbestand**
4. **Order stornieren stellt Lagerbestand wieder her**

### 5.2 Restocking (mit Mocking)
5. **Restock wird ausgelöst, wenn Stock unter Schwellwert fällt**
   - Erwartung: `SupplierGateway` wird mit korrekter SKU und Menge aufgerufen
6. **Kein Restock, wenn Stock über/gleich Schwellwert**
   - Erwartung: `SupplierGateway` wird nicht aufgerufen

## 6. Testfälle (konkret)
### 6.1 InventoryServiceTest
- `createProduct_setsInitialStock()`
- `placeOrder_reducesStock()`
- `placeOrder_throwsIfInsufficientStock()`
- `cancelOrder_restoresStock()`

### 6.2 RestockServiceTest (Mockito)
- `checkAndRestock_callsSupplierWhenBelowThreshold()`
- `checkAndRestock_doesNotCallSupplierWhenAboveThreshold()`

## 7. TDD-Nachweis (Vorgehen & Belege)
Vorgehen pro Feature:
1. **Red**: Test schreiben (failend)
2. **Green**: minimale Implementierung, damit Test grün wird
3. **Refactor**: Aufräumen ohne Verhaltensänderung

Belege im Repository:
- Commit-Historie mit klaren Messages, z.B.:
  - `test: add failing test for placing order reduces stock`
  - `feat: implement stock reservation for placing order`
  - `refactor: simplify order validation`

## 8. Automatisierung & Reports (CI/CD)
- CI-Pipeline wird bei **Push** und **Pull Request** ausgeführt
- Ergebnisse:
  - Surefire Test-Report: `target/surefire-reports`
  - JaCoCo Coverage Report: `target/site/jacoco/index.html`
- In der Pipeline werden beide Reports als Artefakte hochgeladen und sind pro Run einsehbar.

## 9. Code Review / Pull Requests
- Entwicklung erfolgt über Branches und Pull Requests
- Mindestens **3 Pull Requests** (auch als Einzelperson möglich), z.B.:
  1. Domain + erste Tests
  2. Order Cancel + Tests
  3. Restock + Mockito Tests + CI/Reports
- Pro PR wird mindestens ein kurzer Review-Kommentar dokumentiert.

---
**Ergebnis:** Das Projekt erfüllt die Kriterien zu Testkonzept, Unit-/Komponententests, TDD-Nachweis, Mocking sowie CI mit Reports.
```
