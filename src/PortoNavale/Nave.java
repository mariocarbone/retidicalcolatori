package PortoNavale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Nave {
	
	private static int contID=100;
	private final int ID;
	
	
	int lunghezza;
	int nContainer;
	String indirizzo;
	
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
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		
		for (int i = 0; i < 10; i++) {
			Nave b=new Nave(4*i+10,20+i);
			Socket server=new Socket("127.0.0.1",3000);
			BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintWriter out = new PrintWriter(server.getOutputStream(), true);
			out.println(b.toString());
			System.out.println(in.readLine());
			Thread.sleep(2000);
			System.out.println(in.readLine());
			server.close();
			Thread.sleep(2000);
		}
		
		
	}
	
	public String toString() {
		String nave="";
		return nave+this.ID+" "+this.lunghezza+" "+this.nContainer+" "+this.indirizzo;
	}
	
}
