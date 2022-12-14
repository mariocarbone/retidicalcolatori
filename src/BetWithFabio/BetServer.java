package BetWithFabio;

/*
Provare a ri-implementare il BetServer,
rispettando le specifiche della traccia, senza
impostare il timeout sul ServerSocket
(serv.setSoTimeout(...))
• Il Server usa due thread
• Il primo Thread per accettare le scommesse (entro
il timeout) o rifiutarle (dopo il timeout).
• Il secondo Thread per inviare la lista dei vincitori
subito dopo lo scadere del timeout.

 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Calendar;

public class BetServer {

    static ArrayList<Scommessa> listaScommese;

    public BetServer(int limite, int sPort, int mPort) {

        Calendar oralimite = Calendar.getInstance();
        oralimite.add(Calendar.SECOND, limite);

        String indirizzoServer = "127.0.0.1";
        String indirzzoGruppo = "230.0.0.1";

        RiceviScommessa thread1 = new RiceviScommessa(oralimite, sPort);
        thread1.start();
    }

    public static void main(String[] args) {
        int sPort = 8001;
        int mPort = 8002;
        int limite = 10; //secondi
        listaScommese = new ArrayList<Scommessa>();
        try {
            BetServer bs = new BetServer(limite, sPort, mPort);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    private class RiceviScommessa extends Thread {

        Calendar oraLimite;
        int portaServer;
        ServerSocket ss;

        RiceviScommessa(Calendar oralimite, int portaServer) {
            this.oraLimite = oralimite;
            this.portaServer = portaServer;
            try {
                ss = new ServerSocket(portaServer);
            } catch (Exception e) {
                System.out.println(e);
            }


        }

        public void run() {

            while (true) {
                try {
                    Socket client = ss.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);


                    MulticastSocket multicastSocket = new MulticastSocket(8002);



                    if (Calendar.getInstance().getTimeInMillis() < oraLimite.getTimeInMillis()) {

                        String betString = in.readLine();
                        // numCavallo puntata
                        String[] ricevuto = betString.split(" ");
                        String cavallo = ricevuto[0].substring(1, ricevuto[0].length() - 1);
                        String puntata = ricevuto[1].substring(1, ricevuto[1].length() - 1);
                        String scommettitore = client.getInetAddress().getHostAddress();
                        Scommessa scommessa = new Scommessa(cavallo, puntata, scommettitore);
                        System.out.println("Scommessa ricevuta: " + scommessa);
                        out.println("Scommessa Accettata!");

                        String mess = "SEI UN COGLIONE";
                        byte[] buf = new byte[256];
                        buf=(mess.getBytes());
                        InetAddress group = InetAddress.getByName("230.0.0.1");
                        DatagramPacket datagramPacket = new DatagramPacket(buf,buf.length,group,8002);
                        multicastSocket.send(datagramPacket);
                        System.out.println("Ho inviato un messaggio in multicast");


                    } else {
                        System.out.println("Ora limite superata");
                        out.println("ORA LIMITE SUPERATA");

                    }

                } catch (IOException e) {
                    System.out.println(e);
                }

            }


        }
    }


    private class InviaEsito extends Thread {


    }


}
