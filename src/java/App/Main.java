package App;

import Menu.MenuPrincipal;
import model.AccesoAdministrador;
import java.util.Scanner;

public class Main {
    static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        AccesoAdministrador.validador(sc);

        if (AccesoAdministrador.validador(sc)){
            MenuPrincipal.sPrincipal(sc);
        }

    }
}
