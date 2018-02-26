package e.sky64.retrofit_practice.Models;

/**
 * Created by UXM on 2018-02-21.
 */

public class Message {

    private int id;
    private int from;
    private int to;
    private String title;
    private String message;
    private String sent;

    public Message(int id, int from, int to, String title, String message, String sent) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.title = title;
        this.message = message;
        this.sent = sent;
    }

    public int getId() {
        return id;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getSent() {
        return sent;
    }
}
