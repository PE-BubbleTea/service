package ro.unibuc.hello.dto;

public class Greeting {

    private final long id;
    private final String content;

    public Greeting() {
        this.id = 1;
        this.content = "test";
    }

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}