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

    Map<Sensore,Set<Misura>> misure;
    Set<Sensore> sensori;


    public Server() {

        /*
        Nel costruttore inizializzo eventuali strutture dati
        o oggetti da passare ai Thread
        */

        //dati = Collections.synchronizedMap(new HashMap<Sensore,Long>());

        this.misure = new ConcurrentHashMap<>();
        this.sensori = Collections.synchronizedSet(new HashSet<Sensore>());

    }

    private void attesaConnessioneClient() {

        try {
            ServerSocket server = new ServerSocket(sPort);
            while (true) {
                //Istanzio il thread e il socket lato client
                Socket client = server.accept();
                new gestoreConnessione(client,sensori,timestamps).start();


            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }




    class gestoreConnessione extends Thread {


        /*
                riceve da un client l’id di un sensore e restituisce un oggetto di tipo Misura contenente la
                grandezza misurata (una stringa: temperatura, umidità, ecc.), il valore della misura (un double) e il timestamp (un
                long che può essere ottenuto mediante chiamata del metodo System.currentTimeMillis()) in cui essa è stata
                rilevata.
          */
        private Socket client;
        private Map<Sensore,Long> dati;

        public gestoreConnessione(Socket client, Map<Integer,Sensore> sensori, ) {
         this.client = client;
         this.dati = dati;
        }

        public void run(){
            try{
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream());
                PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()),true));
                Sensore sensore = new Sensore();
                long timestamp = new long;
                int id = Integer.parseInt(in.readLine());
                for (Sensore s: dati){
                    if s.getId().equals(id){
                        sensore = s;
                        dati.
                    }
                }
                out.println(sensore.getMisura(),sensore.getValore());




            }catch(Exception e){
                System.out.println(e);
            }


        }



    }



    class Misura{



        private String grandezza;
        private int valore;
        private long misura;

        public Misura(String grandezza, int valore, long misura){
            this.grandezza = grandezza;
            this.valore = valore;
            this.misura = misura;
        }

        public String getGrandezza() {
            return grandezza;
        }

        public int getValore() {
            return valore;
        }

        public long getMisura() {
            return misura;
        }

        public void setGrandezza(String grandezza) {
            this.grandezza = grandezza;
        }

        public void setValore(int valore) {
            this.valore = valore;
        }

        public void setMisura(long misura) {
            this.misura = misura;
        }


    }


}
