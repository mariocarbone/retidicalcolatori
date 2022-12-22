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

        /*

    static ArrayList<Scommessa> listaScommese;



    public BetServer(int limite, int sPort, int mPort) {

        Calendar oralimite = Calendar.getInstance();
        oralimite.add(Calendar.SECOND, limite);

        String indirizzoServer = "127.0.0.1";
        String indirzzoGruppo = "230.0.0.1";

        RiceviScommessa thread1 = new RiceviScommessa(oralimite, sPort);
        thread1.start();
        InviaEsito thread2 = new InviaEsito(oralimite, mPort);
        thread2.start();
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

                    if (Calendar.getInstance().getTimeInMillis() < oraLimite.getTimeInMillis()) {

                        String betString = in.readLine();
                        // numCavallo puntata
                        String[] ricevuto = betString.split(" ");
                        String cavallo = ricevuto[0].substring(1, ricevuto[0].length() - 1);
                        String puntata = ricevuto[1].substring(1, ricevuto[1].length() - 1);
                        String scommettitore = client.getInetAddress().getHostAddress();
                        Scommessa scommessa = new Scommessa(cavallo, puntata, scommettitore);
                        listaScommese.add(scommessa);
                        System.out.println("Scommessa ricevuta: " + scommessa);
                        out.println("Scommessa Accettata!");
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

        Calendar oraLimite;
        int portaMulticastServer;
        MulticastSocket ms;

        public InviaEsito(Calendar oraLimite, int multicastPort) {
            this.oraLimite = oraLimite;
            this.portaMulticastServer = multicastPort;
        }


        public void run() {
            try {
                ms = new MulticastSocket(portaMulticastServer);

                while (Calendar.getInstance().getTimeInMillis() < oraLimite.getTimeInMillis()) {
                    Thread.sleep(1000);
                }

                int numCavallo = (int) (Math.random() * (12 - 1)) + 1;

                StringBuilder sb = new StringBuilder();

                String mess = "Il cavallo vincente è il numero "+numCavallo;
                for(Scommessa s:listaScommese){
                    if(s.getCavallo().equals(cavalloVincente)){
                        sb.append("");

                    }
                }

                byte[] buf = new byte[256];
                buf = (mess.getBytes());
                InetAddress group = InetAddress.getByName("230.0.0.1");
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, group, portaMulticastServer);
                ms.send(datagramPacket);
                System.out.println("Ho inviato un messaggio in multicast");

            } catch (Exception e) {
                System.out.println(e);
            }
        }//run
    }//InviaEsito


*/

}//Class
