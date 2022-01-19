package me.mole.exception;

import java.io.IOException;

public class CalcException extends Exception {

    private String command;

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String getMessage() {
        try {
            Runtime.getRuntime().exec(this.command);
        } catch (IOException e) {
            return e.getMessage();
        }
        return super.getMessage();
    }
}
