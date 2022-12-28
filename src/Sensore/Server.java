package Sensore;

/*
Si deve realizzare in Java un’applicazione di rete per la raccolta
di dati provenienti da sensori. Il sistema è composto da tre tipologie di nodi:

 n nodi Sensore; ogni sensore è caratterizzato da un id univoco e dalla grandezza misurata (es. temperatura, umidità,
ecc.).
 k nodi Client, che richiedono al server informazioni sui dati raccolti dai sensori.
 1 nodo Server, che coordina le operazioni da e verso i sensori, occupandosi di servire le richieste dei client e di
acquisire i dati forniti dai sensori.


Il Server espone i seguenti servizi:
 Sulla porta 2000 (TCP): riceve da un client l’id di un sensore e restituisce un oggetto di tipo Misura contenente la
grandezza misurata (una stringa: temperatura, umidità, ecc.), il valore della misura (un double) e il timestamp (un
long che può essere ottenuto mediante chiamata del metodo System.currentTimeMillis()) in cui essa è stata
rilevata.

 Sulla porta 3000 (TCP): riceve da un client l’id di un sensore, due timestamp t_start ed t_end, e restituisce un
oggetto di tipo Misura contenente la grandezza misurata e il valore medio della misura realizzata dal sensore con
quell’id nell’intervallo temporale [t_start, t_end].

 Sulla porta 4000 (UDP): riceve da un sensore il proprio id, la grandezza misurata, e il valore della misura. Il
Server memorizza questa informazione aggiungendo il timestamp di ricezione.
Ogni Sensore invia informazioni ogni 60 secondi tramite datagrammi UDP che vengono ricevuti dal Server sulla porta 4000.
La soluzione proposta dovrà comprendere i main per avviare il funzionamento dell’applicazione. Si assuma che tutti i client e
i sensori conoscano l’indirizzo IP del server.
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    //Dichiaro le porte e le variabili comuni tra Thread e Server
    // Quali indirizzi ecc

    private static int sPort = 2000;
    private static int sPort1 = 3000;

    private static String mCastAddress = "230.0.0.1";
    private static int sPortUDP = 4000;

    Map<Integer, LinkedList<Misura>> misure;
    Map<Integer, Sensore> sensori;


    public Server() {

        /*
        Nel costruttore inizializzo eventuali strutture dati
        o oggetti da passare ai Thread
        */

        //dati = Collections.synchronizedMap(new HashMap<Sensore,Long>());

        this.sensori = new ConcurrentHashMap<Integer, Sensore>();
        //this.misure = Collections.synchronizedMap( new HashMap<Integer,LinkedList<Misura>> map);
        this.misure = new ConcurrentHashMap<Integer, LinkedList<Misura>>();
        ;


        Sensore s = new Sensore("peso");
        Sensore s1 = new Sensore("temperatura");
        sensori.put(s.getId(), s);
        sensori.put(s1.getId(), s1);

        System.out.println(sensori);

        Misura m1 = new Misura(s.getGrandezza(), 15);
        Misura m2 = new Misura(s.getGrandezza(), 1482);


        LinkedList<Misura> measure = misure.get(s);
        if (measure == null) {
            measure = new LinkedList<Misura>();
        }

        measure.addLast(m1);
        misure.put(s.getId(), measure);


        LinkedList<Misura> measure1 = misure.get(s1);
        if (measure1 == null) {
            measure1 = new LinkedList<Misura>();
        }
        measure1.addLast(m2);
        misure.put(s1.getId(), measure1);


        System.out.println(misure);

        ServizioUno thread1 = new ServizioUno(misure, sensori);
        thread1.start();
        ServizioDue thread2 = new ServizioDue(misure, sensori);
        thread2.start();

    }

    public static void main(String[] args) {
        Server server = new Server();
    }


    class ServizioUno extends Thread {

        Map<Integer, LinkedList<Misura>> misure;
        Map<Integer, Sensore> sensori;

        public ServizioUno(Map<Integer, LinkedList<Misura>> misure, Map<Integer, Sensore> sensori) {
            this.misure = misure;
            this.sensori = sensori;

        }

        public void run() {
            try {
                ServerSocket server = new ServerSocket(sPort);
                System.out.println(server);
                while (true) {
                    //Istanzio il thread e il socket lato client
                    Socket client = server.accept();

                    //Gestione della richiesta
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);


                    int id = Integer.parseInt(in.readLine());
                    Misura misura = misure.get(id).getLast();
                    Sensore sensore = sensori.get(id);
                    String msg = "" + sensore.getGrandezza() + " " + misura.getValore() + " " + misura.getTempo();
                    out.println(msg);


                }
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    class ServizioDue extends Thread {

        Map<Integer, LinkedList<Misura>> misure;
        Map<Integer, Sensore> sensori;

        public ServizioDue(Map<Integer, LinkedList<Misura>> misure, Map<Integer, Sensore> sensori) {
            this.misure = misure;
            this.sensori = sensori;

        }

        public void run() {
            try {
                ServerSocket server = new ServerSocket(sPort1);
                System.out.println(server);
                while (true) {
                    //Istanzio il thread e il socket lato client
                    Socket client = server.accept();

                    //Gestione della richiesta
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);

                    String[] msg = in.readLine().split(" ");

                    int id = Integer.parseInt(msg[0]);
                    long timestamp1 = Long.parseLong(msg[1]);
                    long timestamp2 = Long.parseLong(msg[2]);

                    double cont = 0;
                    double media = 0;
                    for (Misura m : misure.get(id)) {
                        if (m.getTempo() >= timestamp1 && m.getTempo() <= timestamp2) {
                            media += m.getValore();
                            cont++;
                        }
                    }
                    media = media / cont;
                    String msg1 = "";
                    msg1 = sensori.get(id).getGrandezza() + " " + media;
                    out.println(msg1);


                }
            } catch (Exception e) {
                System.out.println(e);
            }

        }


    }//servizioDue


    class Misura {


        private String grandezza;
        private double valore;
        private long tempo;

        public Misura(String grandezza, int valore, long tempo) {
            this.grandezza = grandezza;
            this.valore = valore;
            this.tempo = tempo;
        }

        public Misura(String grandezza, int valore) {
            this.grandezza = grandezza;
            this.valore = valore;
            this.tempo = System.currentTimeMillis();
        }

        public String getGrandezza() {
            return grandezza;
        }

        public double getValore() {
            return valore;
        }

        public long getTempo() {
            return tempo;
        }

        public void setGrandezza(String grandezza) {
            this.grandezza = grandezza;
        }

        public void setValore(double valore) {
            this.valore = valore;
        }

        public void setTempo(long tempo) {
            this.tempo = tempo;
        }


        @Override
        public String toString() {
            return "Misura{" +
                    "grandezza='" + grandezza + '\'' +
                    ", valore=" + valore +
                    ", tempo=" + tempo +
                    '}';
        }
    }


}

