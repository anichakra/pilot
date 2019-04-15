package me.anichakra.poc.pilot.aws.sns.domain;


public class Notification {

    private
    String subject;
    private String body;

    public Notification() {
    }

    public Notification(String subject, String body) {
        this.setSubject(subject);
        this.setBody(body);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "subject='" + getSubject() + '\'' +
                ", body='" + getBody() + '\'' +
                '}';
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
