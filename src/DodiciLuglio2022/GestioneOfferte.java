package DodiciLuglio2022;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GestioneOfferte implements Runnable{

    private ConcurrentHashMap<Integer, Offerta> listaOfferte;
    ConcurrentHashMap<Integer, Prodotto> listaProdotti;
    private int mcastPort;
    private String mcastAddress;

    public GestioneOfferte(ConcurrentHashMap<Integer, Offerta> listaOfferte, ConcurrentHashMap<Integer,
            Prodotto> listaProdotti, int range1Port, int range2Port, String mcastAddress,int mcastPort) {
        this.listaOfferte=listaOfferte;
        this.listaProdotti=listaProdotti;
        gestisciOfferte(range1Port, range2Port, mcastAddress, mcastPort);

    }

    public void gestisciOfferte(int range1Port, int range2Port, String mcastAddress, int mcastPort){

        int porta = (int) (Math.random())*(range2Port-range1Port)+range1Port;

        try{

            ServerSocket server = new ServerSocket(porta);
            MulticastSocket multicastSocket = new MulticastSocket(mcastPort);
            byte[] buf = new byte[256];

            DatagramPacket packet = new DatagramPacket(buf,buf.length, InetAddress.getByName(mcastAddress),mcastPort);
            multicastSocket.send(packet);
            int random = (int) (Math.random()*(listaProdotti.size())+1);
            Prodotto inOfferta = listaProdotti.get(random);

            }


            /*Ad ogni invio, il server comunica la porta TCP del server socket, l’ID del prodotto, il prezzo di vendita,
                    la percentuale di sconto e il numero di pezzi disponibili.
*/

        }catch (Exception e){
            System.out.println(e);
        }

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

        ServerSocket server = new ServerSocket();

    }


}

