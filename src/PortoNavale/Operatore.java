package PortoNavale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Operatore {

	public static void main(String[] args) throws IOException, InterruptedException {
		Socket porto=new Socket("127.0.0.1",4000);
		BufferedReader br=new BufferedReader(new InputStreamReader(porto.getInputStream()));
		System.out.println(br.readLine());
		Thread.sleep(52000);
		PrintWriter out=new PrintWriter(new OutputStreamWriter(porto.getOutputStream()),true);
		out.println("ok!");
		porto.close();
	}
	
	public static void opera() throws IOException, InterruptedException {
		Socket porto=new Socket("127.0.0.1",4000);
		BufferedReader br=new BufferedReader(new InputStreamReader(porto.getInputStream()));
		System.out.println(br.readLine());
		Thread.sleep(52000);
		PrintWriter out=new PrintWriter(new OutputStreamWriter(porto.getOutputStream()),true);
		out.println("ok!");
		porto.close();
	}
}
