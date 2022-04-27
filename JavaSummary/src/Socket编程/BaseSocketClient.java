package Socket编程;

//import org.apache.poi.util.IOUtils;

//import jdk.internal.module.ModuleInfoExtender;

import java.io.*;

import java.net.Socket;

/**
 * The very basic socket client that only send one single message.
 */

public class BaseSocketClient {
    private String serverHost;

    private int serverPort;

    private Socket socket;

    private OutputStream outputStream;

    private InputStream inputStream;
    private static final int MAX_BUFFER_SIZE = 1024;


    public BaseSocketClient(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
    }



    public void connectServer() throws IOException {
        this.socket = new Socket(this.serverHost, this.serverPort);
        this.outputStream = socket.getOutputStream();
        // why the output stream?
    }

    //从只发送和接收一次消息的socket基础代码开始
    public void sendSingle(String message) throws IOException {
        try {
            this.outputStream.write(message.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
        this.outputStream.close();
        this.socket.close();
    }

    //socket连接支持全双工的双向通信（底层是tcp），下面的例子中，服务端在收到客户端的消息后，将返回给客户端一个回执
    public void sendMessage(String message) throws IOException {
        this.socket = new Socket(this.serverHost, this.serverPort);
        this.outputStream = socket.getOutputStream();
        this.outputStream.write(message.getBytes("UTF-8"));
        this.socket.shutdownOutput(); // 告诉服务器，所有的发送动作已经结束，之后只能接收
        this.inputStream = socket.getInputStream();

        DataInputStream in = new DataInputStream(inputStream);
        System.out.println("服务器响应： " + in.readUTF());
//        String receipt = IOUtils.toByteArray(inputStream).toString();
//        System.out.println("got receipt: " + receipt);
        this.inputStream.close();
        this.socket.close();
        //注意这里我们在服务端接受到消息以及客户端发送消息后，分别调用了shutdownInput()和shutdownOutput()而不是直接close对应的stream，这是因为在关闭任何一个stream，都会直接导致socket的关闭，也就无法进行后面回执的发送了。
        //
        //
        //但是注意，调用shutdownInput()和shutdownOutput()之后，对应的流也会被关闭，不能再次向socket发送/写入了。
        //

    }

    public static void main(String[] args) {
        BaseSocketClient bc = new BaseSocketClient("127.0.0.1", 9799);
        try {
            bc.connectServer();
//            bc.sendSingle("Hi from mark.");
            bc.sendMessage("double transfer messge");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
