package youtube.app;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import youtube.models.Item;
import youtube.models.Result;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@ManagedBean(name = "playlist", eager = true)
@RequestScoped
public class Playlist {

    private Result result;
    private Properties prop;

    public Playlist() throws Exception {
        prop = new Properties();
        InputStream file = getClass().getClassLoader().getResourceAsStream("config.properties");
        prop.load(file); // Lanza Exception

        URIBuilder uri = new URIBuilder();
        uri.setScheme("https");
        uri.setHost("www.googleapis.com/youtube/v3/playlists");
        uri.setParameter("key", prop.getProperty("youtube_key"));
        uri.setParameter("channelId", prop.getProperty("youtube_channelId"));
        uri.setParameter("part", "snippet,contentDetails");
        uri.setParameter("maxResults", "50");
        System.out.println(uri.toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Result> response = restTemplate.getForEntity(uri.build(), Result.class); // Lanza Exception
        result = response.getBody();

        System.out.println("GET All StatusCode = " + response.getStatusCode());
        System.out.println("GET All Headers = " + response.getHeaders());
        System.out.println("GET Body (object list): " + result.getNextPageToken());

        for (Item b1: result.getItems()) {
//            System.out.println("--> " + b1.getId());
            System.out.println("--> " + b1.getSnippet().getTitle());
//            System.out.println("--> " + b1.getContentDetails().getItemCount());
        }

    }

    public Result getResult() {
        return this.result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
