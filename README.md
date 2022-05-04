# PC2T-projekt

## Zadání

[Zadání](zadani.pdf)

Předpokládejme databázi studentů univerzity, kde každý student má svoje identifikační číslo, jméno,
příjmení a datum narození. Každý student může dostat libovolný počet známek (standardní
známkování 1 až 5), z nějž je počítán studijní průměr. Existují tři skupiny studentů:


a) Studenti technického oboru, kteří dokážou říci, zda byl rok jejich narození rokem přestupným.
b) Studenti humanitního oboru, kteří dokážou říci, v jakém znamení zvěrokruhu se narodili.
c) Studenti kombinovaného studia, kteří dokážou obojí výše zmíněné.


Při přijetí na univerzitu, je každý student zařazen do jedné z výše uvedených skupin. V průběhu studia
není možné studenta přesunout do jiné skupiny.
Vytvořte v programovacím jazyce JAVA ve vývojovém prostředí Eclipse databázový program, který
umožní uživateli následující:


a) Přidávat nové studenty - uživatel vždy provede výběr skupiny, do které chce studenta přiřadit, zadá
jeho jméno a příjmení a rok narození. Následně je studentovi přiděleno identifikační číslo odvozené
dle celkového pořadí přijímaných studentů.

b) Zadat studentovi novou známku – uživatel vybere studenta podle jeho ID a zadá požadovanou
známku.

c) Propuštění studenta z univerzity – uživatel zadá ID studenta, který je odstraněn z databáze.

d) Nalezení jednotlivých studentů dle jejich ID a výpis ostatních informací (jméno, příjmení, rok
narození, studijní průměr).

e) Pro vybraného studenta (dle ID) spustit jeho dovednost (viz rozdělení studentů dle oborů).

f) Abecedně řazený výpis všech studentů (dle příjmení) v jednotlivých skupinách (ID, jméno, příjmení,
rok narození, studijní průměr).

g) Výpis obecného studijního průměru v technickém a humanitním oboru (společný průměr všech
studentů v daném oboru).

h) Výpis celkového počtu studentů v jednotlivých skupinách.

i) Načtení všech údajů ze souboru.

j) Uložení všech údajů do souboru.

k) Uložení informací o studentech do SQL databáze

l) Načtení informací o studentech z SQL databáze

Pozn. SQL databáze je pouze doplňková vlastnost programu, tj. program musí být schopen pracovat
i bez přítomnosti SQL databáze.

Program musí dále obsahovat následující:
- Efektivní využití základních vlastností OOP.
- Alespoň jednu abstraktní třídu nebo rozhraní
- Alespoň jednu dynamickou datovou strukturu