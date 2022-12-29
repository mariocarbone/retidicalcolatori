package DodiciLuglio2022;

public class Offerta {

    private Prodotto prodotto;
    private int quantita;
    private int sconto;
    private long scadenza;

    public Offerta(Prodotto prodotto, int quantita, int sconto, long scadenza){
        this.prodotto = prodotto;
        this.quantita=quantita;
        this.sconto=sconto;
        this.scadenza=scadenza;
    }


    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public int getPrezzoFinale(){
        return this.prodotto.getPrezzo()-(sconto/100*this.prodotto.getPrezzo());
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public int getSconto() {
        return sconto;
    }

    public void setSconto(int sconto) {
        this.sconto = sconto;
    }

    public long getScadenza() {
        return scadenza;
    }

    public void setScadenza(long scadenza) {
        this.scadenza = scadenza;
    }



}
