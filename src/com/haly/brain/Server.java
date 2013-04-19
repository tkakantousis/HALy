package com.haly.brain;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;


public class Server
{
    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(6969), 0);
            server.createContext("/haly", new RequestHandler());
            server.setExecutor(null);
            server.start();
        }
        catch (IOException ex) {
            System.err.println("[SERVER] Error starting server.");
        }
    }

    static class RequestHandler implements HttpHandler
    {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "I'm HAL's Jabeja!";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
