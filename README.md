# System kontroli wind w budynku

## Algorytm doboru windy
Gdy na danym piętrze zostaje kliknięty przycisk, aby skorzystać z windy na miejsce zostaje wysłana winda według poniższych kryteriów:
1. Istnieje winda, która obsługuje już identyczne wezwanie - brak akcji
2. Winda z najmniejszą liczbą zakolejkowanych wezwań
3. Spośród takich wind ta, której ostatnie wezwanie kończy się najbliżej piętra, na którym wciśnięto przycisk

## Algorytm prioretyzacji wezwań danej windy
Zaimplementowany przeze mnie algorytm ma na celu zoptymalizowanie obsługi wezwań windy poprzez ograniczenie do minimum koniecznych zmian kierunku poruszania się windy.

Wezwania przydzielone danej windzie obsługiwane będą w następującej kolejności:   

### 1. Wezwania które można obsłużyć bez spowodowania zmiany kierunku poruszania się windy
> Np. winda jest na 2 piętrze i porusza się w górę, a ktoś na 5 piętrze zamierza jechać do góry

Tę sytuację można rozpoznać po tym, że piętro na którym winda (jadąca w górę) została wezwana znajduje się powyżej jej obecnego piętra.

Takie wezwania będą obsługiwane (dla windy jadącej do góry) od najniższych do najwyższych pięter.
> Np. wspomniana wyżej winda została wezwana na piętra 8 i 4 (aby pojechać do góry) - zatrzyma się najpierw na 4, a następnie na 8

Analogicznie winda jadąca w dół będzie preferowała najpierw piętra wyższe (ale tylko spośród tych, które są poniżej jej obecnego piętra).

W tej sytuacji naciśnięcie przycisku wewnątrz windy działa analogicznie jak wezwanie windy z zewnątrz w odpowiednią stronę, gdyż obie te sytuacje nie zmieniają przyszłego kierunku windy.
> Np. nasza winda została wezwana na piętro 8 (aby pojechać do góry), a w jej wnętrzu naciśnięto piętro 6 - oba te wezwania są równoważne i decyduje kolejność pięter.
<br>

### 2. Wezwania które można obsłużyć z jedną zmianą kierunku poruszania się windy
> Np. winda porusza się w górę, a ktoś na dowolnym piętrze zamierza jechać w dół

Takie wezwania będą obsługiwane (dla windy jadącej do góry) od najwyższych do najniższych pięter.
> Np. wspomniana wyżej winda została wezwana na piętra 8 i 4 (aby pojechać w dół) - zatrzyma się najpierw na 8, a następnie na 4

Analogicznie winda jadąca w dół będzie preferowała najpierw piętra niższe.

<br>

### 3. Wezwania do których obsłużenia potrzeba aż dwóch zmian kierunku poruszania się windy
> Np. winda jest na 8 piętrze i porusza się w górę, a ktoś na 2 piętrze zamierza jechać do góry

Jest to jedyna sytuacja, która pozostaje po wykluczeniu dwóch powyższych. Dotyczy zawsze wezwań w tym samym kierunku poruszania się, co obecny. Kolejność pięter jest analogiczna do sytuacji 1, tj.: winda jedzie w górę - najpierw niższe piętra; winda jedzie w dół - najpierw wyższe.
