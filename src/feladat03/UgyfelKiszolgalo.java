package feladat03;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class UgyfelKiszolgalo implements Runnable{
    private Socket kapcsolat;
    private ArrayList<Indian> lista;
    public UgyfelKiszolgalo(Socket kapcsolat){
        this.kapcsolat = kapcsolat;
    }

    @Override
    public void run() {
        try {
            DataInputStream ugyfeltol = new DataInputStream(kapcsolat.getInputStream());
            DataOutputStream ugyfelnek = new DataOutputStream(kapcsolat.getOutputStream());
            int menu;
            beolvas();
            do {
                menu = ugyfeltol.readInt();
                System.out.println("ugyfeltol"+ menu);
                switch (menu){
                    case 1:
                        String s = "";
                        for (Indian item :
                                lista) {
                            s += item+"\n";
                        }
                        ugyfelnek.writeUTF(s);
                        ugyfelnek.flush();
                        break;
                }

            }while (menu != -1);
        }catch (IOException e){
            System.err.println(e);
        }
    }

    private void beolvas() {
        lista = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("indianok.txt"));
            String sor = br.readLine();
            while (sor != null){
                lista.add(new Indian(sor));
                sor = br.readLine();
            }
        }catch (FileNotFoundException e){
            System.err.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
