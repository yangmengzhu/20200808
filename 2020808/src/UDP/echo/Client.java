package UDP.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static final String SERVER_PORT="127.0.0.1";
    public static void main(String[] args) {
        Scanner stdinScanner=new Scanner(System.in);
        try(DatagramSocket clientSocket=new DatagramSocket()){
            while(stdinScanner.hasNextLine()){
                String request=stdinScanner.nextLine();
                if(request.equalsIgnoreCase("quit") ){
                    break;
                }
                //准备发送
                byte[] sendBuf = request.getBytes("utf-8");
                DatagramPacket sendPacket=new DatagramPacket(sendBuf,sendBuf.length, InetAddress.getByName(SERVER_PORT),Service.PORT );
                clientSocket.send(sendPacket);
                //等待响应
                byte[] receiveBuf=new byte[1024];
                DatagramPacket receivePacket=new DatagramPacket(receiveBuf,0,receiveBuf.length);
                clientSocket.receive(receivePacket);
                String response=new String(receiveBuf,0,receivePacket.getLength(),"utf-8"); 
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
