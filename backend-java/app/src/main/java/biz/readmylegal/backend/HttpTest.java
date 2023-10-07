package biz.readmylegal.backend;

import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.io.IOException;

public class HttpTest {
    //My Void main. Purpose: make simple http server using inside json. 

    private final static String httpBody = 
            "<!DOCTYPE HTML>\n" + //
            "<html>\n" + //
            "\n" + //
            "<head>\n" + //
            "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" + //
            "  <title>Hello World</title>\n" + //
            "</head>\n" + //
            "\n" + //
            "<body>\n" + //
            "  Hello World!\n" + //
            "</body>\n" + //
            "\n" + //
            "</html>";

    public static void test() throws IOException {
        //System.out.println("Second Hello World ");

        int serverPort = 8000; //port
        //is bound to an IP address and port number and listens for incoming TCP connections from clients on this address.
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/api/hello", (exchange -> {
            String respText = httpBody;
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
            exchange.close();
        }));
        server.setExecutor(null);
        server.start();

        
        


    }
    
}
