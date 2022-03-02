package feladat02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class UgyfelKiszolgalo implements Runnable{
    Socket kapcsolat;
    public UgyfelKiszolgalo(Socket kapcsolat){
        this.kapcsolat = kapcsolat;
    }

    @Override
    public void run() {
        try {
            DataInputStream ugyfeltol = new DataInputStream(kapcsolat.getInputStream());
            DataOutputStream ugyfelnek = new DataOutputStream(kapcsolat.getOutputStream());

            while (true){
                int a = ugyfeltol.readInt();
                int b = ugyfeltol.readInt();
                int menu;
                do {
                    menu = ugyfeltol.readInt();
                    switch (menu){
                        case 1:
                            ugyfelnek.writeUTF(kerulet(a,b));
                            break;
                        case 2:
                            ugyfelnek.writeUTF(terulet(a,b));
                            break;
                        case 3:
                            ugyfelnek.writeUTF(negyzete(a,b));
                            break;
                        case 4:
                            ugyfelnek.writeUTF(atloMerete(a,b));
                            break;
                        case 5:
                            ugyfelnek.writeUTF("Ön a kilépést választotta");
                            break;
                    }
                }while (menu != 5);
            }

        }catch (IOException ex){
            System.err.println(ex);
        }
    }

    private String atloMerete(int a, int b) {
        double aNegyzet = Math.pow(a,2);
        double bNegyzet = Math.pow(b,2);
        double c = Math.sqrt((aNegyzet+bNegyzet));

        return String.format("{%f}", c);
    }

    private String negyzete(int a, int b) {
        if (a == b ){
            return "Igen";
        }
        else{
            return "Nem";
        }
    }

    private String terulet(int a, int b) {
        return "A téglalap területe: " + (a * b);
    }

    private String kerulet(int a, int b) {
        return "A téglalap kerülete: " +(2 * (a + b));
    }
}
