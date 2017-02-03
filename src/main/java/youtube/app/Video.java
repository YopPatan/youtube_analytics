package youtube.app;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import youtube.models.Result;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.InputStream;
import java.util.Properties;

@ManagedBean(name = "video", eager = true)
@RequestScoped
public class Video {

    private Properties prop;
    private Result result;

    public Video() throws Exception {
        prop = new Properties();
        InputStream file = getClass().getClassLoader().getResourceAsStream("config.properties");
        prop.load(file); // Lanza Exception
    }

    public Result getResult() throws Exception {
        if (this.result == null) {
            this.result = this.getVideo();
        }
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getVideo() throws Exception {
        URIBuilder uri = new URIBuilder();
        uri.setScheme("https");
        uri.setHost("www.googleapis.com/youtube/v3/videos");
        uri.setParameter("key", prop.getProperty("youtube_key"));
        uri.setParameter("id", "vKoTs_gMRbo");
        uri.setParameter("part", "snippet,contentDetails");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Result> response = restTemplate.getForEntity(uri.build(), Result.class); // Lanza Exception
        result = response.getBody();

        return result;
    }
}
