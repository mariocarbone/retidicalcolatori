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
    ServerSocket socketOfferta;
    int idProdotto;

    public GestioneOfferte(int idProdotto, ServerSocket socketOfferta, ConcurrentHashMap<Integer, Offerta> listaOfferte, ConcurrentHashMap<Integer, Prodotto> listaProdotti) {
        this.listaOfferte = listaOfferte;
        this.listaProdotti = listaProdotti;
        this.socketOfferta = socketOfferta;
        this.idProdotto = idProdotto;

    }


    @Override
    public void run() {


        try {

            while (true) {

                Socket client = socketOfferta.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);

                String msg = in.readLine();
                String[] richiesta = msg.split(" ");
                int idProd = Integer.parseInt(richiesta[0]);
                int qta = Integer.parseInt(richiesta[1]);

                if (qta > listaOfferte.get(idProd).getQuantita() || Calendar.getInstance().getTimeInMillis() > listaOfferte.get(idProd).getScadenza()) {
                    int idOrdine = (int) ((Math.random()) * 1000);
                    out.println(idOrdine + idProd + " " + (listaProdotti.get(idProd).getPrezzo() * qta));
                    //Offerta superata - pago a prezzo pieno
                } else {
                    int idOrdine = (int) ((Math.random()) * 1000);
                    out.println(idOrdine + idProd + " " + (listaOfferte.get(idProd).getPrezzoFinale() * qta));
                    //Offerta attiva - invio totale
                }

            }

        } catch (
                Exception e) {
            System.out.println(e);
        }


    }


}

