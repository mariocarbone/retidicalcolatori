package PortoEs2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Porto {

    private static final int naviPort = 3000;
    private static final int opPort = 4000;
    private static final int mPort = 5000;

    private static final String address = "127.0.0.1";
    private static final String mAddress = "230.0.0.1";

    private static final int dimensione = 10;

    public Banchina banchina;


    public Porto() {
        GestisciNavi navi = new GestisciNavi();
        banchina = new Banchina(dimensione);
        navi.start();
    }


    public static void main(String[] args) {
        new Porto();
    }

    class GestisciNavi extends Thread {

        public GestisciNavi() {
        }

        public void run() {
            try {
                ServerSocket sv = new ServerSocket(naviPort);

                System.out.println("Porto avviato");
                int cont = 0;
                while (true) {

                    Socket naveSocket = sv.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(naveSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(naveSocket.getOutputStream(), true);

                    System.out.println(cont + ") Nave " + naveSocket.getInetAddress().getHostAddress() + " connessa");
                    String pacchetto = in.readLine() + " " + naveSocket.getInetAddress().getHostAddress();
                    String[] naveS = pacchetto.split(" ");
                    // id lunghezza container address
                    Nave nave = new Nave(Integer.parseInt(naveS[0]), Integer.parseInt(naveS[1]),
                            Integer.parseInt(naveS[2]), naveSocket.getInetAddress().getHostAddress());
                    System.out.println("Nave " + nave.getId() + ": Lunghezza:" + nave.getLunghezza()
                            + " nContainer:" + nave.getnContainer() + " Indirizzo:" + nave.getIndirizzo());

                    String msg2 = "Attracca alla banchina n X";
                    out.println(msg2);
                    //System.out.println("Porto: " + msg2);

                    //Contatto l'operatore
                    //Socket operatore = new Socket(address,opPort);
                    GestisciOperatori op = new GestisciOperatori(nave.nContainer);
                    System.out.println("Operatore per nave " + nave.getId() + " avviato");
                    op.start();

                    cont++;
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }//run

    }//Thread


    public class GestisciOperatori extends Thread {

        private int nContainer;
        public GestisciOperatori(int nContainer) {
            this.nContainer=nContainer;
        }

        public void run() {
            try {
                Socket operatore = new Socket(address, opPort);

                BufferedReader in = new BufferedReader(new InputStreamReader(operatore.getInputStream()));
                PrintWriter out = new PrintWriter(operatore.getOutputStream(), true);
                System.out.println("Operatore avviato: " + this.getId());
                System.out.println("____________________________");
                System.out.println();

                String msg = ""+nContainer;
                out.println(msg);
                //System.out.println("Porto: " + msg);

                String msg2 = in.readLine();
                System.out.println("Operatore: " + this.getId() + " " + msg2);
            } catch (Exception e) {
                System.out.println(e);
            }
        }//run

    }


    public void gestisciBanchina(Nave n) {

    }


}
//class
