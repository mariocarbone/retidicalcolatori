package DodiciLuglio2022;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class Client {

    public Client(){

    }

    public static void main (String[]args){


        try{
        MulticastSocket ms = new MulticastSocket(3000);
        ms.joinGroup(InetAddress.getByName("230.0.0.1"));// da rivedere
            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            ms.receive(packet);
            String shit = new String(packet.getData());
            shit = shit.trim();
            System.out.println(shit);
            String[] info = shit.split(" ");
            int porta = Integer.parseInt(info[0]);
            int prodId = Integer.parseInt(info[1]);
            int prezzo = Integer.parseInt(info[2]);
            int percentage = Integer.parseInt(info[3]);
            int quantita = Integer.parseInt(info[4]);

            Socket server = new Socket(InetAddress.getByName("localhost"),porta);
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(server.getOutputStream()), true);
            out.println(prodId+" "+(quantita-100));
            System.out.println(in.readLine().trim());


        }catch (Exception e){
            System.out.println(e);
        }
    }

}
