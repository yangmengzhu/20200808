package UDP.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Service {
    //作为服务端需要先公开自己的地址,端口需要我们自己选择但是一般建议选择1024之后的
    public static final int PORT=8260;
    public static void main(String[] args) {
        //创建一个socket
        try(DatagramSocket serverSocket=new DatagramSocket(PORT)) {
            //不停地接收请求
            System.out.println("DEBUG:服务端已创建");
            while(true){
                //被动等待客户端发来的请求
                byte[] buf=new byte[1024];//创建一个空的数组接收
                //创建一个DatagramPacket对象，关联接收缓冲区
                DatagramPacket packet=new DatagramPacket(buf,0,buf.length);
                //会调用一个阻塞的方法，不知道什么时候才能返回
                System.out.println("DEBUG:等待客户端发送请求过来");
                serverSocket.receive(packet);//不是立即返回的
                System.out.println("DEBUG:收到了客户端发送请求");
                //需要明确对方是谁、我接受了多少数据
                String host=packet.getAddress().getHostName();
                System.out.println("DEBUG:对方的主机地址"+host);
                int port=packet.getPort();
                System.out.println("DEBUG:对方的端口是"+port);
                int length = packet.getLength();//接受的数据长度
                //字节转换为字符形式
                String request=new String(buf,0,length,"utf-8");

                //进行业务处理并得到准备发送回的响应
                String response=service(host,port,request);
                //通过socket发送响应
                byte[] sendBuf = response.getBytes("utf-8");
                DatagramPacket sendPacket=new DatagramPacket(sendBuf,sendBuf.length,packet.getAddress(),port);
                serverSocket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String service(String host, int port, String request) {
        return request;
    }
}
