package com.haly.brain.server;

import com.haly.brain.Brain;
import com.haly.brain.BrainEvent;
import com.haly.brain.BrainStatus;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;


public class HalyServer implements Server
{
    Brain brain;

    public HalyServer(Brain brain) {
        this.brain = brain;
    }

    @Override
    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(6969), 0);
            server.createContext("/haly", new RequestHandler(this.brain));
            server.setExecutor(null);
            server.start();
        }
        catch (IOException ex) {
            System.err.println("[SERVER] Error starting server.");
        }
    }

    static class RequestHandler implements HttpHandler
    {
        Brain brain;

        public RequestHandler(Brain brain) {
            this.brain = brain;
        }

        @Override
        public void handle(HttpExchange exch) throws IOException {
            BrainStatus status = brain.processEvent(requestToBrainEvent(exch));
            
            String response;
            if (status == BrainStatus.OK) {
                response = "{\"status\":\"OK\"}";
            }
            else {
                response = "{\"status\":\"ERROR\"}";
            }
            
            exch.sendResponseHeaders(200, response.length());
            try (OutputStream os = exch.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
        
        private BrainEvent requestToBrainEvent(HttpExchange request) {
            BufferedReader in = null;
            String command = "";
            try {
                in = new BufferedReader(new InputStreamReader(request.getRequestBody(), "utf-8"));
                command = in.readLine().replace("+", " ");
            }
            catch (UnsupportedEncodingException ex) {
                System.err.println("[SERVER] Unsupported encoding found while parsing request!");
            }
            catch (IOException ex) {
                System.err.println("[SERVER] IO Exception thrown while parsing request!");
            }
            finally {
                try {
                    in.close();
                }
                catch (IOException ex) {
                    System.err.println("[SERVER] IO Exception thrown while parsing request!");
                }
            }
            return new BrainEvent(command);
        }
    }
}
