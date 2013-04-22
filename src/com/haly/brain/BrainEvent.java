package com.haly.brain;

public class BrainEvent
{
    private BrainCommand command;
    private Subject subject;
    
    public BrainEvent(BrainCommand command, Subject subject) {
        this.command = command;
        this.subject = subject;
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
}
