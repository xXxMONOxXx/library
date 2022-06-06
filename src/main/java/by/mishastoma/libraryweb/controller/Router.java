package by.mishastoma.libraryweb.controller;

public class Router {
    private String page;
    private Type type = Type.FORWARD;

    public enum Type {
        FORWARD, REDIRECT
    }

    public Router(){
        this.type = Type.FORWARD;
    }

    public Router(String page, Type type) {
        this.page = page;
        this.type = type;
    }

    public Router(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType(){
        return this.type;
    }
}
