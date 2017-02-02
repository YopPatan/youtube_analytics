package youtube.models;

public class Result {

    private String nextPageToken;
    private Item[] items;

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public Item[] getItems() {
        return this.items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

}
