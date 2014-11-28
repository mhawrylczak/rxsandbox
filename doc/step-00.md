Wprowadzenie
============
W czasie workshopu będziemy przekształcać przykładowy mikro-service, używający naszych commonsów i pluginów. 
Aplikacja pobiera dane z innego  serwisu restowego, przekształca je i udostępnia jako nowy zasób. 
W kilku krokach synchroniczna aplikacja zostanie zamieniona na asynchroniczną z wykorzystaniem RxJava.


Zasoby
---
Seriws udostpenia jeden zasób offer. 
Dostępne są 2 metody:
- filtrowana lista ofert 
```
GET /offers?q=<SEARCH STRING>
```
- pojedyncza ofery
```
GET /offers/<ID>
```

Główne klasy
---
- __Offer__ - Typ zasobu.
- __OfferResource__ - jersey resource
- __MobiusClient__ - jersey client do używanej usługi


Konfiguracja
--------
Przed uruchomieniem należy stoworzyć plik app.properties na podstawie app.properties.template.
```
mobiusKeyUser=<Mobius user key>
mobiusKeyPassword=<Mobius key password>
mobiusUrl=<Mobius URL>
```

Uruchomienie
-----
Serwis można uruchomić  z użyciem IDE, za pomocą main z klasy:
```
pl.allegro.atm.workshop.rx.Server
```
albo używając gradle
```
gradlew run
```

Testy
-----
- __OfferResourceIT__ - test integracyjny obu metod. Wywołanie z gradle:

```
gradlew test
```


Zadanie
----
Uruchomić aplikacje i testy.
Wywołać restowe metody.
```
curl http://localhost:8080/offers/q=mobius
```
```
curl http://localhost:8080/offers/<ID>
```

Po wykonaniu przejsć do zadadanie nr 1.
```
checkout -f step-01
```

Llista przykładów
======
1. Tworzenie Observable
2. Observer
3. Subjec & transformations
4. Subjec & transformations c.d. 
5. Combinig observables
6. jersey rx client 