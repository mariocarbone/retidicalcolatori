package DodiciLuglio2022;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Server {

    private ConcurrentHashMap<Integer, Offerta> listaOfferte;
    private ConcurrentHashMap<Integer, Prodotto> listaProdotti;
    private String mCastAddress = "230.0.0.1";
    private int range1Port = 6000;
    private int range2Port = 7000;
    private int tcpPort = 5000;
    private int mcastPort = 3000;

    public Server(String hostname) {
        this.listaOfferte = new ConcurrentHashMap<Integer, Offerta>();
        this.listaProdotti = new ConcurrentHashMap<Integer, Prodotto>();
        creaProdotti(10);
        creaOfferte(5);


    }

    void creaProdotti(int nProdotti) {
        for (int i = 0; i < nProdotti; i++) {
            Prodotto prodotto = new Prodotto(i * 599);
            listaProdotti.put(prodotto.getID(), prodotto);
        }
    }

    void creaOfferte(int nOfferte){

        ArrayList<Integer> chiavi = new ArrayList<>();
        for (Integer pKey : listaProdotti.keySet()) {
            chiavi.add(pKey);
        }

        for (int i = 0; i < nOfferte; i++) {
            int prodRandom = (int) (Math.random() * chiavi.size() + 1); //random nelle chiavi
            gestisciOfferta(chiavi.get(prodRandom - 1));
            chiavi.remove(prodRandom);
        }
    }

    void gestisciOfferta(int idProd) {

        System.out.println("Prodotto Casuale Scelto: " + idProd);

        int pzRandom = (int) ((Math.random() * (1000 - 100)) + 100); //pezzi random in offerta
        int percentage = (int) ((Math.random() * (30 - 10)) + 10); //percentuale random di sconto
        long dataOfferta = Calendar.getInstance().getTimeInMillis();
        long scadenza = dataOfferta + new Long(3000 * 60 * 60); //scadenza offerta 3h

        Offerta pOfferta = new Offerta(listaProdotti.get(idProd), pzRandom, percentage, scadenza);
        listaOfferte.put(idProd, pOfferta);

        int porta =  ThreadLocalRandom.current().nextInt(range1Port,range2Port);

        //int porta = (int) (Math.random()) * (range2Port - range1Port) + range1Port;

        System.out.println("Creazione Offerte: Porta TCP Cacolata: " + porta);

        try {

            MulticastSocket multicastSocket = new MulticastSocket(mcastPort);

            String msg = "" + porta + " " + listaProdotti.get(idProd).getID() + " " + listaProdotti.get(idProd).getPrezzo() + " " + pOfferta.getSconto() + " " + pzRandom;

            byte[] buf = new byte[256];
            buf = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(mCastAddress), mcastPort);
            multicastSocket.send(packet);
            System.out.println("Multicast inviato: " + msg);

            ServerSocket socketOfferta = new ServerSocket(porta);
            System.out.println(socketOfferta);

            GestioneOfferte gesOfferte = new GestioneOfferte(listaProdotti.get(idProd).getID(), socketOfferta, listaOfferte, listaProdotti);//avvio il thread
            gesOfferte.run();
            System.out.println("Ho avviato il Thread");


        } catch (Exception e) {
            System.out.println(e);
        }

    }


    public static void main(String[] args) {

        Server server = new Server("offerte.unical.it");

    }

}

