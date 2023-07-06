import java.util.Arrays;
import java.util.Scanner;

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

            boolean ruchPoprawny = wykonajRuch(nowySymbol, wymiar, plansza);

            if (ruchPoprawny) {
                if (sprawdzCzyWygrana(nowySymbol, plansza)) {
                    drukujPlansze(plansza);
                    System.out.println("Brawo, " + nowySymbol + "! Wygrałeś!");
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
        boolean wygranaSkos1 = true;
        boolean wygranaSkos2 = true;
        for (int i = 0; i < wymiar; i++) {
            if (plansza[i][i] != symbol) {
                wygranaSkos1 = false;
            }
            if (plansza[i][wymiar - 1 - i] != symbol) {
                wygranaSkos2 = false;
            }
        }
        if (wygranaSkos1 || wygranaSkos2) {
            return true;
        }

        return false;
    }

    public static boolean czyPlanszaPelna(char[][] plansza) {
        for (char[] wiersz : plansza) {
            for (char pole : wiersz) {
                if (pole == ' ') {
                    return false; // Jeżeli jest puste pole, plansza nie jest pełna
                }
            }
        }
        return true; // Plansza jest pełna
    }

    public static boolean wykonajRuch(char symbol, int wymiar, char[][] plansza) {
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

    public static void drukujPlansze(char[][] plansza) {
        int wymiar = plansza.length;

        // Numeracja kolumn
        System.out.print("   ");
        for (int i = 0; i < wymiar; i++) {
            System.out.print(i + "  ");
        }
        System.out.println();

        // Wyświetlanie planszy
        for (int wiersz = 0; wiersz < wymiar; wiersz++) {
            System.out.print(wiersz + " ");
            for (int kolumna = 0; kolumna < wymiar; kolumna++) {
                System.out.print("| " + plansza[wiersz][kolumna] + " ");
            }
            System.out.println("|");

            if (wiersz < wymiar - 1) {
                System.out.print("  ");
                for (int i = 0; i < wymiar; i++) {
                    System.out.print("----");
                }
                System.out.println();
            }
        }
        System.out.println();
    }
}
