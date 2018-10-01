package in.co.mymusic.proxies;

import com.fasterxml.jackson.databind.JsonNode;
import in.co.mymusic.utils.ResponseParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ArtistDescriptionProxy {

    @Autowired
    private ApiRequester apiRequester;


    @Value("${profiledescription.host.url}")
    private String hostUrl;


    public String fetchProfileDescription(String id) {
        String url = hostUrl + id;
        System.out.println("hitting Profile description discog url " + url);
        String profileResponse = null;
        try {
            ResponseEntity<JsonNode> responseEntity =
                    apiRequester.trigger(url, null, JsonNode.class, HttpMethod.GET);
            profileResponse = ResponseParser.resourceUrl.apply(responseEntity.getBody());
        } catch (Exception e) {
            System.out.println("error while fetching profile response for");
        }
        return profileResponse;
    }
}
