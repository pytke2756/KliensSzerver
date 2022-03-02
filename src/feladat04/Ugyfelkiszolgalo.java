package feladat04;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Ugyfelkiszolgalo implements Runnable{
    private HashMap<String, Idojaras> elorejelzesek;
    private Socket kapcsolat;

    public Ugyfelkiszolgalo(Socket kapcsolat){
        elorejelzesek = new HashMap<>();
        this.kapcsolat = kapcsolat;
    }
    @Override
    public void run() {
        try {
            Beolvas();
            System.out.println("beolvas megvan");
            DataInputStream ugyfeltol = new DataInputStream(kapcsolat.getInputStream());
            DataOutputStream ugyfelnek = new DataOutputStream(kapcsolat.getOutputStream());

            while (true){
                int menu;
                do {
                    menu = ugyfeltol.readInt();
                    switch (menu){
                        case 1:
                            ugyfelnek.writeUTF(legmelegebbMa());
                            ugyfelnek.flush();
                            break;
                        case 2:
                            ugyfelnek.writeUTF(legmelegebbHolnap());
                            break;
                        case 3:
                            ugyfelnek.writeUTF(leghidegebbMa());
                            break;
                        case 4:
                            ugyfelnek.writeUTF(leghidegebbHolnap());
                            break;
                        case 5:
                            ugyfelnek.writeUTF(osszesAdat());
                            break;
                        case 6:
                            ugyfelnek.writeUTF("Ön a kilépést választotta");
                            break;
                    }
                }while (menu != 6);
            }

        }catch (IOException ex){
            System.err.println(ex);
        }
    }

    private String osszesAdat() {
        return String.valueOf(elorejelzesek.size()) + " adat van összesen a file-ban";
    }

    private String leghidegebbHolnap() {
        Object elso = elorejelzesek.keySet().toArray()[0];
        int leghidegebb = elorejelzesek.get(elso).getHolnapi().getMin();

        for (Map.Entry<String, Idojaras> entry: elorejelzesek.entrySet()){
            if(leghidegebb > entry.getValue().getHolnapi().getMin()){
                leghidegebb = entry.getValue().getHolnapi().getMin();
            }
        }
        return String.format("%d lesz a leghidegebb holnap.", leghidegebb);

    }

    private String leghidegebbMa() {
        Object elso = elorejelzesek.keySet().toArray()[0];
        int leghidegebb = elorejelzesek.get(elso).getMai().getMin();

        for (Map.Entry<String, Idojaras> entry: elorejelzesek.entrySet()){
            if(leghidegebb > entry.getValue().getMai().getMin()){
                leghidegebb = entry.getValue().getMai().getMin();
            }
        }
        return String.format("%d volt a leghidegebb ma.", leghidegebb);
    }

    private String legmelegebbHolnap() {
        int legmelegebb = 0;
        for (Map.Entry<String, Idojaras> entry: elorejelzesek.entrySet()){
            if(legmelegebb < entry.getValue().getHolnapi().getMax()){
                legmelegebb = entry.getValue().getHolnapi().getMax();
            }
        }
        return String.format("%d lesz a legmelegebb holnap.", legmelegebb);
    }

    private String legmelegebbMa() {
        int legmelegebb = 0;
        for (Map.Entry<String, Idojaras> entry: elorejelzesek.entrySet()){
            if(legmelegebb < entry.getValue().getMai().getMax()){
                legmelegebb = entry.getValue().getMai().getMax();
            }
        }
        return String.format("%d volt a legmelegebb ma.", legmelegebb);
    }


    public void Beolvas(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("weather.txt"));
            br.readLine();
            String sor = br.readLine();

            while (sor != null){
                Idojaras i = new Idojaras(sor);
                String megye = i.getMegye();
                elorejelzesek.put(megye, i);
                sor = br.readLine();
            }
            for (Map.Entry<String, Idojaras> entry: elorejelzesek.entrySet()){
                System.out.println(entry.getValue());
            }
            br.close();

        }catch (FileNotFoundException e){
            System.err.println(e);
        }catch (IOException e){
            System.err.println(e);
        }
    }
}
