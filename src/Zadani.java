/**
 * Zadani projektu 
 * <p>
 * Projekt byl zadan v ramci predmetu BPC-PC2T v letnim semestru skolniho roku 2021/2022 - FEKT VUT Brno.
 * </p>
 */

/*
Predpokladejme databazi studentu univerzity, kde kazdy student ma svoje identifikacni cislo, jmeno,
prijmeni a datum narozeni. Kazdy student muze dostat libovolny pocet znamek (standardni
znamkovani 1 az 5), z nejz je pocitan studijni prumer. Existuji tri skupiny studentu:

a) Studenti technickeho oboru, kteri dokazou rici, zda byl rok jejich narozeni rokem prestupnym.
b) Studenti humanitniho oboru, kteri dokazou rici, v jakem znameni zverokruhu se narodili.
c) Studenti kombinovaneho studia, kteri dokazou oboji vyse zminene.


Pri prijeti na univerzitu, je kazdy student zarazen do jedne z vyse uvedenych skupin. V prubehu studia
neni mozne studenta presunout do jine skupiny.
Vytvorte v programovacim jazyce JAVA ve vyvojovem prostredi Eclipse databazovy program, ktery
umozni uzivateli nasledujici:

a) Pridavat nove studenty - uzivatel vzdy provede vyber skupiny, do ktere chce studenta priradit, zada
jeho jmeno a prijmeni a rok narozeni. Nasledne je studentovi prideleno identifikacni cislo odvozene
dle celkoveho poradi prijimanych studentu.

b) Zadat studentovi novou znamku – uzivatel vybere studenta podle jeho ID a zada pozadovanou
znamku.

c) Propusteni studenta z univerzity – uzivatel zada ID studenta, ktery je odstranen z databaze.

d) Nalezeni jednotlivych studentu dle jejich ID a vypis ostatnich informaci (jmeno, prijmeni, rok
narozeni, studijni prumer).

e) Pro vybraneho studenta (dle ID) spustit jeho dovednost (viz rozdeleni studentu dle oboru).

f) Abecedne razeny vypis vsech studentu (dle prijmeni) v jednotlivych skupinach (ID, jmeno, prijmeni,
rok narozeni, studijni prumer).

g) Vypis obecneho studijniho prumeru v technickem a humanitnim oboru (spolecny prumer vsech
studentu v danem oboru).

h) Vypis celkoveho poctu studentu v jednotlivych skupinach.

i) Nacteni vsech udaju ze souboru.

j) Ulozeni vsech udaju do souboru.

k) Ulozeni informaci o studentech do SQL databaze

l) Nacteni informaci o studentech z SQL databaze

Pozn. SQL databaze je pouze doplnkova vlastnost programu, tj. program musi byt schopen pracovat
i bez pritomnosti SQL databaze.
Program musi dale obsahovat nasledujici:

- Efektivni vyuziti zakladnich vlastnosti OOP.
- Alespon jednu abstraktni tridu nebo rozhrani
- Alespon jednu dynamickou datovou strukturu

 */
