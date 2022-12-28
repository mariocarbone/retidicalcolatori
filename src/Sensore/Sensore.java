package Sensore;

public class Sensore {

    private static int contID = 100;
    private final int ID;

    private String grandezza;

    public Sensore(String grandezza) {
        this.ID = contID++;
        this.grandezza = grandezza;
    }

    public int getId() {
        return ID;
    }

    public String getGrandezza() {
        return grandezza;
    }

    public String toString(){
        String str = ""+this.ID+" "+this.grandezza;
        return str;
    }


    class MandaSensore extends Thread{
        
    }



}
