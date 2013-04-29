package com.haly.brain;

public class BrainEvent
{
    private BrainCommand command;
    private Subject subject;
    private User user;
    private String extra;

    public BrainEvent(BrainCommand command, String extra) {
        this.command = command;
        this.extra = extra;
    }

    public BrainEvent(BrainCommand command, Subject subject) {
        this.command = command;
        this.subject = subject;
    }

    public BrainEvent(BrainCommand command, Subject subject, User user) {
        this.command = command;
        this.subject = subject;
        this.user = user;
    }

    public BrainEvent(String command) {
        String[] msg = command.split(" ");

        if (msg.length != 3) {
            this.command = BrainCommand.NOP;
            this.subject = Subject.NONE;
        }
        else {
            switch (msg[1].toLowerCase()) {
                case "open":
                    this.command = BrainCommand.OPEN;
                    break;
                case "close":
                    this.command = BrainCommand.CLOSE;
                    break;
                case "help":
                    this.command = BrainCommand.HELP;
                    break;
                case "call":
                    this.command = BrainCommand.CALL;
                    break;
                case "locate":
                    this.command = BrainCommand.LOCATE;
                    break;
                case "shutdown":
                    this.command = BrainCommand.SHUTDOWN;
                    break;
                default:
                    this.command = BrainCommand.NOP;
                    break;
            }

            switch (msg[2].toLowerCase()) {
                case "window":
                    this.subject = Subject.WINDOW;
                    break;
                case "door":
                    this.subject = Subject.DOOR;
                    break;
                case "lights":
                    this.subject = Subject.LIGHTS;
                    break;
                case "police":
                    this.subject = Subject.POLICE;
                    break;
                case "device":
                    this.subject = Subject.DEVICE;
                    break;
                case "user":
                    this.subject = Subject.USER;
                    break;
                case "system":
                    this.subject = Subject.SYSTEM;
                    break;
                default:
                    this.subject = Subject.NONE;
                    break;
            }
        }
    }

    public BrainCommand getCommand() {
        return command;
    }

    public void setCommand(BrainCommand command) {
        this.command = command;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
