package feladat02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Kliens {
    public static void main(String[] args) {
        try {
            Socket kapcsolat = new Socket("localhost",8080);
            DataInputStream szervertol = new DataInputStream(kapcsolat.getInputStream());
            DataOutputStream szervernek = new DataOutputStream(kapcsolat.getOutputStream());
            Scanner sc = new Scanner(System.in);
            while (true){
                System.out.print("Kérem az a oldalt: ");
                int a = sc.nextInt();
                szervernek.writeInt(a);
                szervernek.flush();
                System.out.print("Kérem a b oldalt: ");
                int b = sc.nextInt();
                szervernek.writeInt(b);
                szervernek.flush();

                int menu;
                do {
                    System.out.println("Válasszon az alábbi lehetőségek közül: ");
                    System.out.println("1: Kerület\n2: Terület\n3: Négyzet-e\n4: Átló mérete\n5: Kilépés");
                    menu = sc.nextInt();
                    szervernek.writeInt(menu);
                    szervernek.flush();
                    System.out.println(szervertol.readUTF());
                }while (menu != 5);
            }


        }catch (IOException ex){
            System.err.println(ex);
        }
    }
}
