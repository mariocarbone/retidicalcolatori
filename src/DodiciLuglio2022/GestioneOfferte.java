package DodiciLuglio2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GestioneOfferte implements Runnable {

    private ConcurrentHashMap<Integer, Offerta> listaOfferte;
    ConcurrentHashMap<Integer, Prodotto> listaProdotti;
    private int mcastPort;
    private String mcastAddress;
    private int range1Port;
    private int range2Port;

    public GestioneOfferte(ConcurrentHashMap<Integer, Offerta> listaOfferte, ConcurrentHashMap<Integer, Prodotto> listaProdotti, int range1Port, int range2Port, String mcastAddress, int mcastPort) {
        this.listaOfferte = listaOfferte;
        this.listaProdotti = listaProdotti;
        this.range1Port = range1Port;
        this.range2Port = range2Port;
        this.mcastAddress = mcastAddress;
        this.mcastPort = mcastPort;
    }

   /* Il Server dispone di un elenco interno di prodotti, ciascuno caratterizzato da un codice univoco e da un prezzo di vendita. Periodicamente,
    il server sceglie un prodotto e crea un’offerta a tempo limitato. In particolare, una volta scelto un prodotto, il Server stabilisce un numero
    di pezzi disponibili in offerta (numero casuale tra 100 e 1000) ed applica su di essi uno sconto percentuale random, compreso tra il 10 e il 30%,
    sul prezzo di vendita originario. Ogni offerta ha una durata limite di 3 ore. Trascorso questo tempo, oppure se tutti i prodotti disponibili
    in offerta sono stati venduti, il prodotto torna ad essere venduto al prezzo originario.

    Il Server svolge il ruolo di gestore delle offerte e delle richieste di acquisto. In particolare:

            - Crea un’offerta a tempo limitato e stabilisce una porta casuale nel range [6000-7000] sulla quale apre un server socket
    temporaneo per gestire le richieste di acquisto da parte dei client.

- Comunica la nuova offerta a tutti i client mediante invio sul gruppo broadcast caratterizzato dall’indirizzo 230.0.0.1 e
    dalla porta 3000. Ad ogni invio, il server comunica la porta TCP del server socket, l’ID del prodotto, il prezzo di vendita,
    la percentuale di sconto e il numero di pezzi disponibili. Per ogni offerta attiva, inoltre, verifica ogni 30 minuti se ci sono
    ancora pezzi disponibili ed, eventualmente, invia un nuovo messaggio di avviso sul gruppo broadcast.

            -Riceve sulla porta TCP dell’offerta un oggetto Richiesta contenente la richiesta di acquisto di un prodotto da parte di un client.
    La richiesta conterrà l’ID del prodotto e il numero di pezzi da acquistate. Ricevuta la richiesta, il server verificherà lo stato dell’offerta
            (es. offerta attiva/scaduta, numero di pezzi in offerta ancora disponibili) e, successivamente, invierà al client l’ID dell’ordine e l’importo totale da pagare.

    Per i pezzi sui quali non è applicabile l’offerta (perché scaduta o senza più pezzi disponibili), verrà considerato il prezzo originale di vendita.
    Una volta completato un acquisto, il cliente ha 7 giorni di tempo per effettuare una richiesta di reso mediante l’invio di una richiesta sulla
    porta TCP 5000 contenente l’ID dell’ordine. Se la richiesta di reso è effettuata entro il tempo consentito, il server ritornerà al client,
    sulla stessa connessione socket, l’importo rimborsato, che sarà pari all’ammontare dell’ordine; altrimenti restituirà zero.
*/

    @Override
    public void run() {
        int porta = (int) (Math.random()) * (range2Port - range1Port) + range1Port;
        System.out.println("Porta TCP Cacolata: "+ porta);

        try {

            ServerSocket server = new ServerSocket(porta);
            MulticastSocket multicastSocket = new MulticastSocket(mcastPort);
            byte[] buf = new byte[256];

            DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(mcastAddress), mcastPort);
            ArrayList<Integer> chiavi = new ArrayList<>();
            for(Integer pKey : listaProdotti.keySet()){
                chiavi.add(pKey);
            }

            int prodRandom = (int) (Math.random()*chiavi.size()+1); //
            System.out.println("Prodotto Casuale scelto: "+prodRandom);

            int pzRandom = (int) ((Math.random() * (1000 - 100)) + 100);
            int percentage = (int) ((Math.random() * (30 - 10)) + 10);
            Prodotto inOfferta = listaProdotti.get(chiavi.get(prodRandom-1));
            long scadenza = new Long(3000 * 60 * 60);
            Offerta pOfferta = new Offerta(inOfferta, pzRandom, percentage, scadenza);
            listaOfferte.put(inOfferta.getID(), pOfferta);

            String merda = "" + porta + " " + inOfferta.getID() + " " + inOfferta.getPrezzo()+ " " + pOfferta.getSconto() + " " + pzRandom;
            buf = merda.getBytes();
            long dataOfferta = Calendar.getInstance().getTimeInMillis();
            multicastSocket.send(packet);
            System.out.println("Multicast inviato: "+merda);
            // invio in multicast la porta TCP del server socket, l’ID del prodotto, il prezzo di vendita,
            // la percentuale di sconto e il numero di pezzi disponibili.

            while (true) {

                Socket client = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);

                String msg = in.readLine();
                String[] richiesta = msg.split(" ");
                int idProd = Integer.parseInt(richiesta[0]);
                int qta = Integer.parseInt(richiesta[1]);

                if (qta > listaOfferte.get(inOfferta).getQuantita() || Calendar.getInstance().getTimeInMillis() > dataOfferta + scadenza) {
                    int idOrdine = (int) ((Math.random()) * 1000);
                    out.println(idOrdine + idProd + " " + (inOfferta.getPrezzo() * qta));
                } else {
                    int idOrdine = (int) ((Math.random()) * 1000);
                    out.println(idOrdine + idProd + " " + (listaOfferte.get(idProd).getPrezzoFinale() * qta));
                }


                    /*Riceve sulla porta TCP dell’offerta un oggetto Richiesta contenente la richiesta di acquisto di un prodotto da parte di un client.
                            La richiesta conterrà l’ID del prodotto e il numero di pezzi da acquistate. Ricevuta la richiesta, il server verificherà lo
                            stato dell’offerta
                            (es. offerta attiva/scaduta, numero di pezzi in offerta ancora disponibili) e, successivamente,
                            invierà al client l’ID dell’ordine e l’importo totale da pagare.
                    */
            }

        } catch (
                Exception e) {
            System.out.println(e);
        }


        //ServerSocket server = new ServerSocket();

    }


}

