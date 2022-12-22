package PortoNavale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;

public class Porto {

    int[][] banchina;
    static ServerSocket sv;

    public static void main(String[] args) {

        LinkedList<Nave> coda = new LinkedList<Nave>();

        Porto porto = new Porto(coda, 10, 10);

        System.out.println("\tBanchina");

        for (int i = 0; i < porto.banchina.length; i++) {
            porto.banchina[i][0] = i;
            System.out.print(Arrays.toString(porto.banchina[i]));
        }

        System.out.println();

        System.out.println("\n      server avviato");



        try {
            sv = new ServerSocket(3000);
            while (true) {
                Socket client = sv.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                out.println("connessione stabilita");
                String pacchetto = in.readLine() + " " + client.getInetAddress().getHostAddress();
                String[] nome = pacchetto.split(" ");
                // id lunghezza container address
                Nave nave = new Nave(Integer.parseInt(nome[0]), Integer.parseInt(nome[1]), Integer.parseInt(nome[2]),
                        nome[3]);
                System.out.println(nave);
                boolean inserito = false;
                int attracco = -1;
                if (nave.getLunghezza() <= 40) {
                    for (int i = 0; i < porto.banchina.length / 2; i++) {
                        if (porto.banchina[i][1] == 0 && inserito == false) {
                            porto.banchina[i][1] = nave.getId();
                            inserito = true;
                            out.println("attracchi alla banchina :" + i);
                            attracco = i;
                            client.close();
                        }
                    }
                } else if (nave.getLunghezza() > 40) {
                    for (int i = porto.banchina.length / 2; i < porto.banchina.length; i++) {
                        if (porto.banchina[i][1] == 0 && inserito == false) {
                            porto.banchina[i][1] = nave.getId();
                            inserito = true;
                            out.println("attracchi alla banchina :" + i);
                            attracco = i;
                            client.close();
                        }
                    }
                }
                if (inserito == false) {
                    coda.add(nave);
                    out.println("attracchi alla banchina sto cazzo");
                    attracco = -1;
                    System.out.println();
                    System.out.println("coda");
                    for (Nave nv : coda) {
                        System.out.println(nv.getId());
                    }
                    client.close();
                }
                System.out.println();
                System.out.println("banchina");
                for (int i = 0; i < porto.banchina.length; i++) {
                    porto.banchina[i][0] = i;
                    System.out.print(Arrays.toString(porto.banchina[i]));
                }

                // contatto l'operatore
                if (attracco >= 0) {
                    Socket operatore = new Socket("localhost", 4000);
                    Operatore.opera();
                    PrintWriter opOut = new PrintWriter(operatore.getOutputStream(), true);
                    opOut.println(attracco + " " + nave.getId());
                    System.out.println("comunico che: " + nave.getId() + " attracca su " + attracco);
                }

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public Porto(LinkedList<Nave> coda, int nBanchina, int nOperatore) {
        this.banchina = new int[nBanchina][2];
    }
}
