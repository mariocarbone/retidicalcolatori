package DodiciLuglio2022;

public class Prodotto {

   /* Il Server dispone di un elenco interno di prodotti, ciascuno caratterizzato da un codice univoco e da un prezzo di vendita. Periodicamente,
    il server sceglie un prodotto e crea un’offerta a tempo limitato. In particolare, una volta scelto un prodotto, il Server stabilisce un numero
    di pezzi disponibili in offerta (numero casuale tra 100 e 1000) ed applica su di essi uno sconto percentuale random, compreso tra il 10 e il 30%,
    sul prezzo di vendita originario. Ogni offerta ha una durata limite di 3 ore. Trascorso questo tempo, oppure se tutti i prodotti disponibili
    in offerta sono stati venduti, il prodotto torna ad essere venduto al prezzo originario.
*/
   public Prodotto(int prezzo){
       this.ContID+=1;
       this.ID = this.ContID;
       this.prezzo = prezzo;
   }


    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public int getID() {
        return ID;
    }


    private int ContID = 100;
    private int prezzo;
    public int ID;





}
