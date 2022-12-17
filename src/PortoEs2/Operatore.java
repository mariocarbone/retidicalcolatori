package PortoEs2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Operatore {

    private static final int sPort = 4000;
    private static final String address = "127.0.0.1";
    //private static int tempoAttesa;

    public Operatore() {

        try {
            ServerSocket op = new ServerSocket(sPort);
            System.out.println("Operatore Avviato");
            System.out.println("____________________________");
            System.out.println();


            while (true) {
                GestisciConnessioni gs = new GestisciConnessioni(op.accept());
                gs.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Operatore op = new Operatore();
    }

    class GestisciConnessioni extends Thread {

        private Socket portoSocket;

        public GestisciConnessioni(Socket porto) {
            this.portoSocket = porto;
        }

        public void run() {

            try {
            BufferedReader in = new BufferedReader(new InputStreamReader(portoSocket.getInputStream()));
            PrintWriter out = new PrintWriter(portoSocket.getOutputStream(), true);
            String msg1 = in.readLine();
            System.out.println("Porto: " + msg1);
            int nContainer = Integer.parseInt(msg1);

            int tempoAttesa = (int) (Math.random() * (500 - 200) + 100) * nContainer;
            System.out.println("Tempo di attesa calcolato: " + tempoAttesa + " ms");

            Thread.sleep(tempoAttesa);

            out.println("OK!");
            System.out.println("____________________________");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}





