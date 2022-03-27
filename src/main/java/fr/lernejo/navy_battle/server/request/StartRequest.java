package fr.lernejo.navy_battle.server.request;

public class StartRequest {
    final StringBuilder id = new StringBuilder();
    final StringBuilder url =new StringBuilder();
    final StringBuilder message = new StringBuilder();

    /*public StartRequest (String id, String url, String message) {
        this.id = new StringBuilder(id);
        this.url = new StringBuilder(url);
        this.message = new StringBuilder(message);
    }*/

    public String getId() {
        return id.toString();
    }

    public String getMessage() {
        return message.toString();
    }

    public String getUrl() {
        return url.toString();
    }

    public void setId(String id) {
        this.id.replace(0, this.id.length(), id);
    }

    public void setMessage(String message) {
        this.message.replace(0, this.message.length(), message);
    }

    public void setUrl(String url) {
        this.url.replace(0, this.url.length(), url);
    }
}
