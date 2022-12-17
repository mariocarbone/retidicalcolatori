package PortoEs2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Nave {


    private static int contID=100;
    private final int ID;


    int lunghezza;
    int nContainer;
    String indirizzo;

    private static final int sPort = 3000;
    private static final int mPort = 5000;
    private static final String address = "127.0.0.1";
    private static final String mAddress = "230.0.0.1";


    public Nave(int lunghezza, int nContainer){
        this.lunghezza=lunghezza;
        this.nContainer=nContainer;
        this.ID=contID++;
        this.indirizzo="";
    }

    public Nave(int ID, int lunghezza, int nContainer, String indirizzo) {
        this.lunghezza=lunghezza;
        this.nContainer=nContainer;
        this.ID=ID;
        this.indirizzo=indirizzo;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getId() {
        return ID;
    }

    public int getLunghezza() {
        return lunghezza;
    }

    public void setLunghezza(int lunghezza) {
        this.lunghezza = lunghezza;
    }

    public int getnContainer() {
        return nContainer;
    }

    public void setnContainer(int nContainer) {
        this.nContainer = nContainer;
    }


    public static void main(String[] args) {

        //Calcolo randomicamente proprietà della nave
        int random1 = (int) (Math.random()*80-1)+1;
        int random2 = (int) (Math.random()*100-1)+1;
        Nave b=new Nave(random1,random2);

        try {
            Socket porto = new Socket(address,sPort);
            System.out.println("Nave avviato");
            System.out.println("Nave generata: Lunghezza: "+b.getLunghezza()+" NContainer: "+b.getnContainer() );
            System.out.println("____________________________");
            System.out.println();

            BufferedReader in = new BufferedReader(new InputStreamReader(porto.getInputStream()));
            PrintWriter out = new PrintWriter(porto.getOutputStream(), true);

            System.out.println("Connessi al porto " + porto.getInetAddress().getHostAddress());

            String msg1 = b.toString();
            out.println(msg1);
            //System.out.println("Nave (ID:"+b.getId()+") : "+msg1);

            String msg2 = in.readLine();
            System.out.println("Porto: "+msg2);

            in.close();
            out.close();
            porto.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public String toString() {
        String nave="";
        return nave+this.ID+" "+this.lunghezza+" "+this.nContainer+" "+this.indirizzo;
    }

    }



