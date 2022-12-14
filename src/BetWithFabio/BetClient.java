package BetWithFabio;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class BetClient {

    public static void main (String[]args) {

        try {
            Socket betServer = new Socket("127.0.0.1", 8001);
            MulticastSocket multicastSocket = new MulticastSocket(8002);
            InetAddress group = InetAddress.getByName("230.0.0.1");
            multicastSocket.joinGroup(group);

            BufferedReader in = new BufferedReader(new InputStreamReader(betServer.getInputStream()));
            PrintWriter out = new PrintWriter(betServer.getOutputStream(), true);

            out.println("<12> <322144112412>");
            Thread.sleep(1000);
            System.out.println(in.readLine());
            byte[] buf = new byte[256];
            DatagramPacket datagramPacket = new DatagramPacket(buf,buf.length);
            multicastSocket.receive(datagramPacket);
            String mess = new String(datagramPacket.getData());
            System.out.println(mess.trim());

        }catch(Exception e){
            System.out.println(e);
        }
    }
}
