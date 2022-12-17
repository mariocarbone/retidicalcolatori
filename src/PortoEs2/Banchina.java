package PortoEs2;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Banchina{

    private int dimensione;
    private Nave[] navi;
    private int[] operatori;



    public Banchina(int dimensione){
        this.dimensione = dimensione;
        this.navi = new Nave[dimensione];
        this.operatori = new int[dimensione];
    }

    public void main (String[]args){

    }

    synchronized void addNave(int banchina, Nave nave, int operatore){
        this.navi[banchina] = nave;
        this.operatori[banchina] = operatore;
    }

    synchronized void rimuoviNave(int banchina){
        this.navi[banchina] = null;
        this.operatori[banchina] = -1;
    }




}
