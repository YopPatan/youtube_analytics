package youtube.app;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import youtube.models.Item;
import youtube.models.Result;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@ManagedBean(name = "playlistItem", eager = true)
@RequestScoped
public class PlaylistItem {

    private Result result;
    private Properties prop;

    @ManagedProperty(value="#{param.playlistItemId}")
    private String playlistItemId;

    public PlaylistItem() throws Exception {
        prop = new Properties();
        InputStream file = getClass().getClassLoader().getResourceAsStream("config.properties");
        prop.load(file); // Lanza Exception

        System.out.println("Crea Playlist Item");
/*        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Result> response = restTemplate.getForEntity(URL_API_PLAYLISTS_ITEM, Result.class, 12L);
        result = response.getBody();*/

/*        for (Item b1: result.getItems()) {
            System.out.println("--> " + b1.getId());
            System.out.println("--> " + b1.getSnippet().getTitle());
        }*/
        //System.out.println("ITEM: " + this.playlistItemId);

    }

/*    public String getTitle() {
        return "TITULO TEST";
    }*/

    public Result getResult() {
//        System.out.println("PARAM: " + this.playlistItemId);
        System.out.println("ITEM: " + this.playlistItemId);

        return this.result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getPlaylistItemId() {
        return playlistItemId;
    }

    public void setPlaylistItemId(String playlistItemId) {
//        System.out.println("Configura id");

        URIBuilder uri = new URIBuilder();
        uri.setScheme("https");
        uri.setHost("www.googleapis.com/youtube/v3/playlistItems");
        uri.setParameter("key", prop.getProperty("youtube_key"));
        uri.setParameter("playlistId", playlistItemId);
        uri.setParameter("part", "snippet");
        uri.setParameter("maxResults", "50");
        //System.out.println("URL " + uri.toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Result> response = restTemplate.getForEntity(uri.toString(), Result.class, 12L);
        result = response.getBody();

        this.playlistItemId = playlistItemId;
    }
}
