package Sensore;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

public class Client {

    private static int sPort = 2000;
    private static int sPort1 = 3000;

    private static String sAddress = "127.0.0.1";

    public Client(){

    }

    public static void main (String[]args){

        try {
            Socket server = new Socket(sAddress, sPort1);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(server.getOutputStream()), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));

           /* out.println(101);
            String[] msg = in.readLine().split(" ");

            Date ora = new Date(Long.parseLong(msg[2]));
            System.out.println("Unita: "+msg[0]+" Valore: "+msg[1]+" Timestamp: "+ ora);
*/

            //out.println("100"+" "+Calendar.getInstance().getTimeInMillis()+" "+ Calendar.getInstance().getTimeInMillis());
            String msg = "101 1671815820442 1671815820442";
            System.out.println("Invio: "+ msg);
            out.println(msg);
            System.out.println(in.readLine());
        }catch (Exception e){
            System.out.println(e);
        }
    }


}
