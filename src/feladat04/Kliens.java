package feladat04;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Kliens {
    public static void main(String[] args) {
        try{
            Socket kapcsolat = new Socket("localhost", 8080);
            DataInputStream szervertol = new DataInputStream(kapcsolat.getInputStream());
            DataOutputStream szervernek = new DataOutputStream(kapcsolat.getOutputStream());
            Scanner sc = new Scanner(System.in);
            int menu;
            do {
                System.out.println("Válaszzon a menüpontok közül: ");
                System.out.println("1: Legmelegebb ma");
                System.out.println("2: Legmelegebb holnap");
                System.out.println("3: Leghidegebb ma");
                System.out.println("4: Leghidegebb holnap");
                System.out.println("5: Hány darab van");
                System.out.println("6: Kilépés");
                menu = sc.nextInt();
                szervernek.writeInt(menu);
                szervernek.flush();
                System.out.println(szervertol.readUTF());

            }while(menu != 6);
        }catch (IOException e){
            System.err.println(e);
        }

    }
}
