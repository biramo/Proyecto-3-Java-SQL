package App;

import Services.ServiceMenu;
import model.AccesoAdministrador;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        if (AccesoAdministrador.validador(sc)){
            new ServiceMenu().initService(sc);
        }

    }
}
