package com.haly.brain.server;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.haly.brain.Brain;
import com.haly.brain.BrainCommand;
import com.haly.brain.BrainEvent;
import com.haly.brain.BrainStatus;
import com.haly.brain.Subject;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;


public class HalyServer implements Server
{
    private final String API_KEY = "AIzaSyBfrJISwuAWXCCEyVs2ME1oXGY--HzPMrc";
    private final int SERVER_PORT = 6969;
    private String registrationID = "APA91bHueQ5cIPs31PyjLo6If_VLnZwCtt5CEuZWLab2NQAZahmIehKuHoGPu02IdKKO2bQIsYw8jvjM-AnokQ1CEJsnMiHp5dWL0RXSn6yTupgiUMSfLYzaxHsgAUuZu2kZtCKARHMC";
    Brain brain;

    public HalyServer(Brain brain) {
        this.brain = brain;
    }

    @Override
    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
            HttpContext context = server.createContext("/haly", new RequestHandler(this.brain, this));
            context.getFilters().add(new ParameterFilter());
            server.setExecutor(null);
            server.start();
        }
        catch (IOException ex) {
            System.err.println("[SERVER] Error starting server.");
        }
    }

    @Override
    public void notifyDevice(String notificationType, String notificationMessage) {
        if (registrationID != null) {
            try {
                Sender sender = new Sender(API_KEY);
                Message message = new Message.Builder().addData("notification", notificationType).addData("message", notificationMessage).build();
                Result result = sender.send(message, registrationID, 5);

                if (result.getMessageId() != null) {
                    String canonicalRegId = result.getCanonicalRegistrationId();
                    if (canonicalRegId != null) {
                        registrationID = canonicalRegId;
                    }
                }
                else {
                    String error = result.getErrorCodeName();
                    if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                        registrationID = null;
                    }
                }
            }
            catch (IOException ex) {
                System.err.println("[SERVER] IOException while sending notification to device!");
            }
        }
    }

    public String getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }

    private String getLocationName() {
        return "";
    }

    static class RequestHandler implements HttpHandler
    {
        Brain brain;
        HalyServer server;

        public RequestHandler(Brain brain, HalyServer server) {
            this.brain = brain;
            this.server = server;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            BrainStatus status = BrainStatus.OK;
            Map<String, Object> params = (Map<String, Object>) exchange.getAttribute("parameters");
            // TODO Handle Longitude and latitude in the requests
            switch ((String) params.get("command")) {
                case "REGISTER_DEVICE":
                    server.setRegistrationID((String) params.get("registrationID"));
                    break;
                case "LOCATE":
                    String location = getLocationName((String) params.get("lon"), (String) params.get("lat"));
                    status = brain.processEvent(new BrainEvent(BrainCommand.valueOf((String) params.get("command")), location));
                    break;
                default:
                    status = brain.processEvent(new BrainEvent(BrainCommand.valueOf((String) params.get("command")), Subject.valueOf((String) params.get("subject"))));
                    break;
            }

            String response;
            if (status == BrainStatus.OK) {
                response = "{\"status\":\"OK\"}";
            }
            else {
                response = "{\"status\":\"ERROR\"}";
            }

            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }

        private String getLocationName(String longitude, String latitude) {
            return "";
        }
    }

}
