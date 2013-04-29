package com.haly.brain;

import com.haly.brain.client.Client;
import com.haly.mouth.Mouth;
import java.util.HashSet;
import java.util.Set;

public class HALy implements Brain {

    Mouth mouth;
    Client client;
    User userInView;
    Set<User> authorizationSet;

    public HALy(Mouth mouth, Client client) {
        this.mouth = mouth;
        this.client = client;
        authorizationSet = new HashSet<>();
        authorizationSet.add(new User(0, "Unknown-0", null));
    }

    @Override
    public synchronized BrainStatus processEvent(BrainEvent event) {
        System.out.println("[BRAIN] Got event with command " + event.getCommand() + " and subject " + event.getSubject() + "!");
        //If we found a new User, add him to the list
        BrainCommand bm = event.getCommand();
        switch (bm) {
            case SEE:
                if (event.getUser() != null) {
                    if (userInView == null || (userInView != null && !userInView.equals(event.getUser()))) {
                        userInView = event.getUser();
                        System.out.println("Got new user with name:" + userInView.getName());
                    }
                }
                break;
            case NOT_SEE:
                //If noone is in front of the camera, current user is set to null
                userInView = null;
                break;
            case OPEN:
                processOpenCloseCommands(event);
                break;
            case CLOSE:
                processOpenCloseCommands(event);
                break;
            case LOCATE:
                Subject sbj1 = event.getSubject();
                switch (sbj1) {
                    case USER:
                        //JSONObject js =  client.hsSendNotification(DeviceNotification.HELP);
                        //System.out.println(js.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(0).getString("long_name"));
                        //System.out.println(js.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(1).getString("long_name"));

                        break;
                }
                break;
            case HELP:
                client.hsSendNotification(DeviceNotification.HELP);
                break;
            case CALL:
                Subject sbj2 = event.getSubject();
                switch (sbj2) {
                    case POLICE:
                        break;
                }
                break;
            case SHUTDOWN:
                doCommand(event);
                System.exit(0);
            default:
                break;
        }

        return BrainStatus.OK;
    }

    private void doCommand(BrainEvent event) {
        mouth.speak("I will " + event.getCommand() + " " + event.getSubject());
    }

    /**
     * Check if user is in front of camera.
     *
     * @return
     */
    private boolean checkUserInView() {
        if (userInView == null) {
            mouth.speak("Get in front of camera");
            return false;
        } else if (userInView != null && authorizationSet.contains(userInView)) {
            System.out.println(userInView.getName() + " is authorized");
            return true;
        }
        return false;
    }

    private void processOpenCloseCommands(BrainEvent event) {
        Subject sbj = event.getSubject();
        switch (sbj) {
            case WINDOW:
                if (checkUserInView()) {
                    doCommand(event);
                }
                break;
            case LIGHTS:
                if (checkUserInView()) {
                    doCommand(event);
                }
                break;
            case OVEN:
                if (checkUserInView()) {
                    doCommand(event);
                }
                break;
            case BOILER:
                if (checkUserInView()) {
                    doCommand(event);
                }
                break;
            case TENT:
                if (checkUserInView()) {
                    doCommand(event);
                }
                break;
            case MUSIC:
                if (checkUserInView()) {
                    doCommand(event);
                }
                break;
            case FRIDGE:
                if (checkUserInView()) {
                    doCommand(event);
                }
                break;
            case VENTILATOR:
                if (checkUserInView()) {
                    doCommand(event);
                }
                break;
            default:
                mouth.speak("I can not execute this command");
        }
    }
}
