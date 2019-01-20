# Maszyna wnioskująca :link:

## Treść zadania :page_with_curl:

Napisać program, który przyjmuje: - formuły koniunkcyjno-implikacyjne bez negacji w rachunku predykatów pierwszego rzędu, - tezę do udowodnienia. Następnie, sprowadza formuły do postaci klauzul, dalej do formuł koniunkcyjno-implikacyjnych i przeprowadza wnioskowanie wstecz dowodzące zadanej tezy. Program może nie obsługiwać funkcji, czyli może nie akceptować formuł postaci PRED(F(x)). Wynik wnioskowania zostaje zademonstrowany graficznie.

## Opis projektu ✏

Projekt zakłada napisanie programu odczytującego formuły koniunkcyjno-implikacyjne z pliku tekstowego oraz dający w rezultacie swojego działania tekstowo-graficzną reprezentację wynikowego grafu powstałego w ramach procedury wnioskowania wstecz przeprowadzonego na podstawie odczytanych formuł koniunkcyjno-implikacyjnych. Formuły koniunkcyjno-implikacyjne mają być zapisane za pomocą specjalnej notacji tekstowej pozwalającej reprezentować m.in. symbol implikacji (=>) oraz symbol koniunkcji (^).

Częścią składową programu jest parser umożliwiający odzwierciedlenie tekstowej reprezentacji koniunkcyjno-implikacyjnych w ramach modelu obiektowego. Każda poprawnie zdefiniowana formuła ma zostać zmapowana na obiekt klasy Clause. Obiekty klasy Clause stanowić mają bazę wiedzy wykorzystywaną w procesie wnioskowani wstecz.
Ważną częścią jest stworzenie obiektowej reprezentacji poszczególnych części formuły koniunkcyjno-implikacyjnej. Należy tu zacząć od typów podstawowych takich jak model.Constant, model.Variable. Predykat jest reprezentowany przez klasę model.Predicate zawierającą w sobie obiekty typu model.Constant i model.Variable. Nazwę predykatu traktujemy jako stałą (model.Constant).
Reprezentacji obiektowej wymagają również operatory – AND i NOT (operator implikacji traktujemy oddzielnie). Ich implementacja zakłada możliwość pobierania pierwszego operandu operacji oraz pozostałych operandów operatora. Operator implikacji jest zdefiniowany w postaci klasy Clause, zakłada on jako pierwszy operand obiekt klasy model.Predicate, drugi operand może być operacją lub predykatem.

Implementacja algorytmu wnioskowania wstecz zakłada wykorzystanie struktury grafu. Wykorzystywany jest mechanizm rekurencji. Kolejne stany algorytmu w których dla określonego (zaprzeczonego) predykatu poszukujemy formuły koniunkcyjno-implikacyjnej z unifikowalną z nim konkluzją reprezentujemy jako węzeł w grafie. W kolejnym kroku algorytmu analizujemy ogon zdania, które zostało poddane unifikacji. W sytuacji, gdy ogon zdania był w postaci koniunkcji predykatów to też reprezentujemy go początkowo jako jeden węzeł, który później będzie rozbijany na następne węzły grafu poprzez kolejne wydzielanie pierwszego operandu z koniunkcji predykatów. Z każdym węzłem jest związany zestaw niewykorzystanych jeszcze obiektów typu Clause (baza wiedzy), sumaryczny zestaw podstawień użyty do uzyskania danego węzła oraz id obiektu Clause w wyniku unifikacji którego powstał ten węzeł.

Program na wyjściu drukuje wynikowy graf powstały w ramach przebiegu algorytmu. Istnieje konieczność zapewnienia możliwości znakowej reprezentacji poszczególnych węzłów grafu wynikowego. Wynik programu ma być wyświetlony w jak najprostszej i czytelnej dla użytkownika końcowego formie.

## Uruchomienie ▶

Program przyjmuje dane od użytkownika w formie paramterów uruchomieniowych cztery parametry.

- Baza wiedzy w postaci formuł koniunkcyjno-implikacyjnych. Baza wiedzy jest przekazywana w formie pliku, do którego ścieżkę przekazujemy programowi parametrem `-knowledgeBase`. Kolejne formuły pisane jedna pod drugą. Implikacja oznaczona poprzez symbol **=>**.
- Użyte w bazie wiedzy stałe (w tym nazwy predykatów). Stałe są przekazywane w formie pliku, do którego ścieżkę przekazujemy programowi parametrem `-const` lub `-constants`. Kolejne stałe są pisane w pliku jedna pod drugą.
- Użyte w bazie wiedzy zmienne. Zmienne są przekazywane w formie pliku, do którego ścieżkę przekazujemy programowi parametrem `-var` lub `-variables`. Kolejne zmienne są pisane w pliku jedna pod drugą.
- Teza do udowodnienia przez algorytm.Teza jest przekazywana w formie pliku, do którego ścieżkę przekazujemy programowi parametrem `-arg` lub `-argument`. Waże by stałe oraz nazwa predykatu użye w tezie były również uwzględnione w plikach ze stałymi.

Przykład:

```sh
$ java -jar ./MaszynaWnioskujaca.jar -knowledgeBase knowledge_base.txt -var variables.txt -const constants.txt -arg argument.txt
```

## Milestones :mount_fuji:

- Opracowanie koncepcji obiektowej reprezentacji formuł koniunkcyjno-implikacyjnych
- Opracowanie koncepcji implementacji algorytmu wnioskowania wstecz
- Stworzenie diagramu klas prezentującego powiązania pomiędzy poszczególnymi klasami i interface’ami
- Uzgodnienie konwencji zapisu tekstowego formuł koniunkcyjno-implikacyjnych (szczególnie operatorów implikacji i and)
- Implementacja obiektowej reprezentacji formuł koniunkcyjno-implikacyjnych
- Implementacja klas i metod wchodzących w skład opracowanego algorytmu
- Implementacja parsera
- Implementacja konsolowego interface użytkownika programu
- Implementacja konsolowego interface użytkownika programu
- Wyświetlanie wyniku działania algorytmu w postaci graficznej
