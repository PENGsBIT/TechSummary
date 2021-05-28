package Socket编程;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The very basic socket server that only listen one single message.
 */
public class BaseSocketServer {
    private ServerSocket server;
    private Socket socket;
    private int port;
    private InputStream inputStream;
    private OutputStream outputStream;
    private static final int MAX_BUFFER_SIZE = 1024;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public BaseSocketServer(int port) {
        this.port = port;
    }
    //从只发送和接收一次消息的socket基础代码开始
    public void runServerSingle() throws IOException {
        this.server = new ServerSocket(this.port);

        System.out.println("base socket server started.");
        // the code will block here till the request come.
        this.socket = server.accept();

        this.inputStream = this.socket.getInputStream();

        byte[] readBytes = new byte[MAX_BUFFER_SIZE];

        int msgLen;
        StringBuilder clientMessage = new StringBuilder();

        while ((msgLen = inputStream.read(readBytes)) != -1) {
            clientMessage.append(new String(readBytes,0,msgLen,"UTF-8"));
        }
        //注意这里的IO操作实现，我们使用了一个大小为MAX_BUFFER_SIZE的byte数组作为缓冲区，然后从输入流中取出字节放置到缓冲区，
        // 再从缓冲区中取出字节构建到字符串中去，这在输入流文件很大时非常有用，事实上，后面要讲到的NIO也是基于这种思路实现的。
        System.out.println("get message from client: " + clientMessage);

        inputStream.close();
        socket.close();
        server.close();
    }

    //socket连接支持全双工的双向通信（底层是tcp），下面的例子中，服务端在收到客户端的消息后，将返回给客户端一个回执
    public void runServer() throws IOException {
        this.server = new ServerSocket(port);
        this.socket = server.accept();
        this.inputStream = socket.getInputStream();

        byte[] readBytes = new byte[MAX_BUFFER_SIZE];

        int msgLen;
        StringBuilder stringBuilder = new StringBuilder();

        while ((msgLen = inputStream.read(readBytes)) != -1) {
            stringBuilder.append(new String(readBytes,0,msgLen,"UTF-8"));
        }

        System.out.println("received message: " + stringBuilder);

        this.socket.shutdownInput(); // 告诉客户端接收已经完毕，之后只能发送

        // write the receipt.

        this.outputStream = this.socket.getOutputStream();
        String receipt = "We received your message: " + stringBuilder;
        outputStream.write(receipt.getBytes("UTF-8"));

        this.outputStream.close();
        this.socket.close();
    }


    public static void main(String[] args) {
        BaseSocketServer bs = new BaseSocketServer(9799);
        try {
            //从只发送和接收一次消息的socket基础代码开始
            //bs.runServerSingle();
            //socket连接支持全双工的双向通信（底层是tcp），下面的例子中，服务端在收到客户端的消息后，将返回给客户端一个回执。
            bs.runServer();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}
