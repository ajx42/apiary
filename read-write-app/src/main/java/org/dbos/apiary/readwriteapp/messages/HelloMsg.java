package org.dbos.apiary.readwriteapp.messages;

public class HelloMsg {

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private String name;

}
