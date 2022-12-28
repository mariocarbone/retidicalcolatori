package DodiciLuglio2022;

public class Offerta {

    private Prodotto prodotto;
    private int quantita;
    private int sconto;
    private long durata;

    public Offerta(Prodotto prodotto, int quantita, int sconto, long durata){
        this.prodotto = prodotto;
        this.quantita=quantita;
        this.sconto=sconto;
        this.durata=durata;
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

    public long getDurata() {
        return durata;
    }

    public void setDurata(long durata) {
        this.durata = durata;
    }



}
