package com.haly.brain;

import com.haly.brain.client.Client;
import com.haly.brain.server.Server;
import com.haly.mouth.Mouth;
import java.util.HashSet;
import java.util.Set;


public class HALy implements Brain
{
    private Mouth mouth;
    private Server server;
    private Client client;
    private User userInView;
    private Set<User> authorizationSet;

    public HALy(Mouth mouth, Server server, Client client) {
        this.mouth = mouth;
        this.server = server;
        this.client = client;
        authorizationSet = new HashSet<>();
        authorizationSet.add(new User(0, "Jane-0", null));
    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public synchronized BrainStatus processEvent(BrainEvent event) {
        System.out.println("[BRAIN] Got event with command " + event.getCommand() + " and subject " + event.getSubject() + "!");


        BrainCommand bc = event.getCommand();
        switch (bc) {
            case SEE:
                if (event.getUser() != null) {
                    if (userInView == null || (userInView != null && !userInView.equals(event.getUser()))) {
                        userInView = event.getUser();
                    }
                }
                break;
            case NOT_SEE:
                userInView = null;
                break;
            case OPEN:
                processOpenCloseCommands(event);
                break;
            case CLOSE:
                processOpenCloseCommands(event);
                break;
            case FIND:
                if (checkUserInView()) {
                    if (event.getSubject() == Subject.MOTHER) {
                        server.notifyDevice(bc.toString(), "Where are you?!");
                    }
                    else if (event.getSubject() == null) {
                        mouth.speak("Mother is at " + event.getExtra());
                    }
                }
                break;
            case HELP:
                if (checkUserInView()) {
                    server.notifyDevice(bc.toString(), "Help meeeee...!!");
                }
                break;
            case CALL:
                if (event.getSubject() == Subject.POLICE) {
                    mouth.speak("I am calling the cops!");
                }
                break;
            case SHUTDOWN:
                if (checkUserInView() && event.getSubject() == Subject.SYSTEM) {
                    mouth.speak("I am going to sleep! Goodbye, " + userInView.getName());
                    System.exit(0);
                }
            default:
                break;
        }
        return BrainStatus.OK;
    }

    /**
     * Check if user is in front of camera.
     *
     * @return
     */
    private boolean checkUserInView() {
        if (userInView == null) {
            mouth.speak("Get in front of camera!");
        }
        else if (userInView != null && authorizationSet.contains(userInView)) {
            return true;
        }
        else if (userInView != null && !authorizationSet.contains(userInView)) {
            mouth.speak("You are not authorized to execute this command!");
        }
        return false;
    }

    private void processOpenCloseCommands(BrainEvent event) {
        Subject sbj = event.getSubject();
        switch (sbj) {
            case WINDOW:
            case LIGHTS:
            case DOOR:
                if (checkUserInView()) {
                    mouth.speak("I will " + event.getCommand() + " " + event.getSubject());
                }
                break;
            default:
                mouth.speak("I can not execute this command!");
        }
    }
}
