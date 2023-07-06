import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
/*w tej wersji komputer wykonuje bardziej skomplikowane i przemyślane ruch:
1.komputer sprawdza czy moze wygrać i jęzeli ma taką możliwość korzysta z niej
2.komputer sprawdza czy gracz nie jest bliski wygranej jak tak to stara go się blokować
3.Jeżeli żaden z warunków nie jest spełniony komputer wykonuje losowy ruch
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("WITAJ W KÓŁKO I KRZYŻYK!!!");
        System.out.print("Podaj rozmiar planszy: ");
        Scanner input = new Scanner(System.in);
        char nowySymbol = 'X';
        int wymiar = input.nextInt();

        // Inicjalizacja planszy
        char[][] plansza = new char[wymiar][wymiar];
        for (char[] wiersz : plansza) {
            Arrays.fill(wiersz, ' ');
        }

        while (true) {
            drukujPlansze(plansza);

            boolean ruchPoprawny;
            if (nowySymbol == 'X') {
                ruchPoprawny = wykonajRuchGracza(nowySymbol, wymiar, plansza);
            } else {
                ruchPoprawny = wykonajRuchKomputera(nowySymbol, wymiar, plansza);
            }

            if (ruchPoprawny) {
                if (sprawdzCzyWygrana(nowySymbol, plansza)) {
                    drukujPlansze(plansza);
                    if (nowySymbol == 'X') {
                        System.out.println("Brawo, " + nowySymbol + "! Wygrałeś!");
                    } else {
                        System.out.println("Komputer (O) wygrał!");
                    }
                    break;
                } else if (czyPlanszaPelna(plansza)) {
                    drukujPlansze(plansza);
                    System.out.println("Remis! Plansza jest pełna.");
                    break;
                }

                nowySymbol = (nowySymbol == 'X') ? 'O' : 'X'; // Zamiana gracza
            }
        }
    }

    public static boolean sprawdzCzyWygrana(char symbol, char[][] plansza) {
        int wymiar = plansza.length;

        // Sprawdzenie wierszy
        for (int wiersz = 0; wiersz < wymiar; wiersz++) {
            boolean wygrana = true;
            for (int kolumna = 0; kolumna < wymiar; kolumna++) {
                if (plansza[wiersz][kolumna] != symbol) {
                    wygrana = false;
                    break;
                }
            }
            if (wygrana) {
                return true;
            }
        }

        // Sprawdzenie kolumn
        for (int kolumna = 0; kolumna < wymiar; kolumna++) {
            boolean wygrana = true;
            for (int wiersz = 0; wiersz < wymiar; wiersz++) {
                if (plansza[wiersz][kolumna] != symbol) {
                    wygrana = false;
                    break;
                }
            }
            if (wygrana) {
                return true;
            }
        }

        // Sprawdzenie przekątnych
        boolean wygranaPrzekatna1 = true;
        boolean wygranaPrzekatna2 = true;
        for (int i = 0; i < wymiar; i++) {
            if (plansza[i][i] != symbol) {
                wygranaPrzekatna1 = false;
            }
            if (plansza[i][wymiar - 1 - i] != symbol) {
                wygranaPrzekatna2 = false;
            }
        }

        return wygranaPrzekatna1 || wygranaPrzekatna2;
    }

    public static boolean wykonajRuchGracza(char symbol, int wymiar, char[][] plansza) {
        System.out.println("Twój symbol to: " + symbol);
        System.out.print("Podaj indeks wiersza: ");
        int wiersz = new Scanner(System.in).nextInt();
        System.out.print("Podaj indeks kolumny: ");
        int kolumna = new Scanner(System.in).nextInt();

        if (wiersz < 0 || wiersz >= wymiar || kolumna < 0 || kolumna >= wymiar) {
            System.out.println("\nPodano nieprawidłowe indeksy. Spróbuj jeszcze raz.\n");
            return false;
        }

        if (plansza[wiersz][kolumna] != ' ') {
            System.out.println("\nTo pole jest już zajęte. Wybierz inne.\n");
            return false;
        }

        plansza[wiersz][kolumna] = symbol;
        return true;
    }

    public static boolean wykonajRuchKomputera(char symbol, int wymiar, char[][] plansza) {
        System.out.println("Ruch komputera (symbol: " + symbol + ")");

        // Sprawdzenie, czy komputer może wygrać
        for (int wiersz = 0; wiersz < wymiar; wiersz++) {
            for (int kolumna = 0; kolumna < wymiar; kolumna++) {
                if (plansza[wiersz][kolumna] == ' ') {
                    plansza[wiersz][kolumna] = symbol;
                    if (sprawdzCzyWygrana(symbol, plansza)) {
                        return true;
                    } else {
                        plansza[wiersz][kolumna] = ' '; // Cofnięcie ruchu
                    }
                }
            }
        }

        // Sprawdzenie, czy gracz może wygrać i zablokowanie go
        char przeciwnySymbol = (symbol == 'X') ? 'O' : 'X';
        for (int wiersz = 0; wiersz < wymiar; wiersz++) {
            for (int kolumna = 0; kolumna < wymiar; kolumna++) {
                if (plansza[wiersz][kolumna] == ' ') {
                    plansza[wiersz][kolumna] = przeciwnySymbol;
                    if (sprawdzCzyWygrana(przeciwnySymbol, plansza)) {
                        plansza[wiersz][kolumna] = symbol; // Blokowanie gracza
                        return true;
                    } else {
                        plansza[wiersz][kolumna] = ' '; // Cofnięcie ruchu
                    }
                }
            }
        }

        // Wykonanie losowego ruchu
        Random random = new Random();
        int losowyWiersz, losowaKolumna;
        do {
            losowyWiersz = random.nextInt(wymiar);
            losowaKolumna = random.nextInt(wymiar);
        } while (plansza[losowyWiersz][losowaKolumna] != ' ');

        plansza[losowyWiersz][losowaKolumna] = symbol;
        return true;
    }

    public static boolean czyPlanszaPelna(char[][] plansza) {
        for (char[] wiersz : plansza) {
            for (char pole : wiersz) {
                if (pole == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static void drukujPlansze(char[][] plansza) {
        int wymiar = plansza.length;

        System.out.print("  ");
        for (int i = 0; i < wymiar; i++) {
            System.out.print(" " + i + ":");
        }
        System.out.println();

        for (int wiersz = 0; wiersz < wymiar; wiersz++) {
            System.out.print(wiersz + ": ");
            for (int kolumna = 0; kolumna < wymiar; kolumna++) {
                System.out.print(plansza[wiersz][kolumna] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
