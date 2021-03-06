package youtube.app;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import youtube.models.Result;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.InputStream;
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
    }

    public Result getResult() throws Exception {
        if (this.result == null) {
            this.result = this.getPlaylistItem();
        }
        return this.result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getPlaylistItemId() {
        return playlistItemId;
    }

    public void setPlaylistItemId(String playlistItemId) {
        this.playlistItemId = playlistItemId;
    }

    public Result getPlaylistItem() throws Exception {
        URIBuilder uri = new URIBuilder();
        uri.setScheme("https");
        uri.setHost("www.googleapis.com/youtube/v3/playlistItems");
        uri.setParameter("key", prop.getProperty("youtube_key"));
        uri.setParameter("playlistId", playlistItemId);
        uri.setParameter("part", "snippet");
        uri.setParameter("maxResults", "50");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Result> response = restTemplate.getForEntity(uri.toString(), Result.class, 12L);
        result = response.getBody();

        return result;
    }
}
