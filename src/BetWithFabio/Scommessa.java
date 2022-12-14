package BetWithFabio;

import java.net.InetAddress;

public class Scommessa {

    private String cavallo;
    private String scommettitore;
    private String puntata;

    public Scommessa(String cavallo, String puntata, String scommettitore){
        this.cavallo = cavallo;
        this.puntata = puntata;
        this.scommettitore = scommettitore;
    }

    public static void main(String[]args){
      //  Scommessa fabio = new Scommessa(1,2040240,"127.0.0.1");
        //System.out.println(fabio);
    }



    public String getCavallo() {
        return cavallo;
    }

    public void setCavallo(String cavallo) {
        this.cavallo = cavallo;
    }

    public String getScommettitore() {
        return scommettitore;
    }

    public void setScommettitore(String scommettitore) {
        this.scommettitore = scommettitore;
    }

    public String getPuntata() {
        return puntata;
    }

    public void setPuntata(String puntata) {
        this.puntata = puntata;
    }

    public String toString(){
        String out = "<Cavallo: "+cavallo+", Puntata: "+puntata+", Scommettitore: "+scommettitore+">";
        return out;
    }

}
