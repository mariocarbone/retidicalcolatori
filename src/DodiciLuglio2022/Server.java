package DodiciLuglio2022;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private ConcurrentHashMap<Integer, Offerta> listaOfferte;
    private ConcurrentHashMap<Integer, Prodotto> listaProdotti;
    private String mCastAddress = "230.0.0.1";
    private int range1Port = 6000;
    private int range2Port = 7000;
    private int tcpPort = 5000;
    private int mcastPort = 3000;


    public Server(String hostname, Prodotto[] prodotti){

        this.listaOfferte = new ConcurrentHashMap<Integer,Offerta>();
        this.listaProdotti = new ConcurrentHashMap<Integer,Prodotto>();
        for (Prodotto prodotto:prodotti){
            listaProdotti.put(prodotto.getID(),prodotto);
        }
        gestioneOfferte();

    }

    void gestioneOfferte(){

        /*- Crea un’offerta a tempo limitato e stabilisce una porta casuale nel range [6000-7000] sulla quale apre un server socket
        temporaneo per gestire le richieste di acquisto da parte dei client.

                - Comunica la nuova offerta a tutti i client mediante invio sul gruppo broadcast caratterizzato dall’indirizzo 230.0.0.1 e
        dalla porta 3000. Ad ogni invio, il server comunica la porta TCP del server socket, l’ID del prodotto, il prezzo di vendita,
                la percentuale di sconto e il numero di pezzi disponibili. Per ogni offerta attiva, inoltre, verifica ogni 30 minuti se ci sono
        ancora pezzi disponibili ed, eventualmente, invia un nuovo messaggio di avviso sul gruppo broadcast.*/

        GestioneOfferte gesOfferte = new GestioneOfferte(listaOfferte,listaProdotti, range1Port, range2Port, mCastAddress, mcastPort);
        gesOfferte.run();

    }


    /*

    Si vuole realizzare un’applicazione di rete in Java per gestire delle vendite online con offerte a tempo limitato.
    Il sistema è composto da un Server e da N Client.

    Il Server svolge il ruolo di gestore delle offerte e delle richieste di acquisto. In particolare:



    -Riceve sulla porta TCP dell’offerta un oggetto Richiesta contenente la richiesta di acquisto di un prodotto da parte di un client.
    La richiesta conterrà l’ID del prodotto e il numero di pezzi da acquistate. Ricevuta la richiesta, il server verificherà lo stato dell’offerta
    (es. offerta attiva/scaduta, numero di pezzi in offerta ancora disponibili) e, successivamente, invierà al client l’ID dell’ordine e l’importo totale da pagare.

    Per i pezzi sui quali non è applicabile l’offerta (perché scaduta o senza più pezzi disponibili), verrà considerato il prezzo originale di vendita.
    Una volta completato un acquisto, il cliente ha 7 giorni di tempo per effettuare una richiesta di reso mediante l’invio di una richiesta sulla
    porta TCP 5000 contenente l’ID dell’ordine. Se la richiesta di reso è effettuata entro il tempo consentito, il server ritornerà al client,
    sulla stessa connessione socket, l’importo rimborsato, che sarà pari all’ammontare dell’ordine; altrimenti restituirà zero.

    Il server dovrà essere in grado gestire più offerte in parallelo.

    Si realizzino le classi Server e Client che implementino le funzionalità sopra descritte. Inoltre, si realizzino due main:
    1) il primo main crea e avvia il Server (con hostname offerte.unical.it);
    2) il secondo main crea e avvia un Client che attende l’arrivo di offerte, invia la propria richiesta di acquisto,
    ne attende l’esito ed invia, eventualmente, una richiesta di reso.

     */

    public static void main (String[]args){


        Server server = new Server("offerte.unical.it", Prodotto[] prodotti);


    }

}
