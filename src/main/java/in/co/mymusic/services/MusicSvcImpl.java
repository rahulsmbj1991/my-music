package in.co.mymusic.services;

import in.co.mymusic.dto.ApplicationResponse;
import in.co.mymusic.dto.CoverArtResponse;
import in.co.mymusic.dto.MusicBrainzReleaseGroup;
import in.co.mymusic.dto.MusicBrainzResponse;
import in.co.mymusic.proxies.ArtistDescriptionProxy;
import in.co.mymusic.proxies.CoverArtProxy;
import in.co.mymusic.proxies.MusicBrainzProxy;
import in.co.mymusic.utils.DefaultCreator;
import in.co.mymusic.utils.ResponseParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static in.co.mymusic.utils.Messages.FOUND_MSG;
import static in.co.mymusic.utils.Messages.NOT_FOUND_MSG;

@Service
public class MusicSvcImpl implements MusicSvc {

    @Autowired
    private MusicBrainzProxy musicBrainzProxy;

    @Autowired
    private CoverArtProxy coverArtProxy;

    @Autowired
    private ArtistDescriptionProxy artistDescriptionProxy;

    private Map<String,ApplicationResponse> cachedResponse = new ConcurrentHashMap<>();

    @Override
    public ApplicationResponse musicByMbid(String mbid) {

        if(cachedResponse.containsKey(mbid)){
            return cachedResponse.get(mbid);
        }

        MusicBrainzResponse musicBrainzResponse = musicBrainzProxy.fetchByMbid(mbid);

        ApplicationResponse applicationResponse;
        if (musicBrainzResponse == null) {
            applicationResponse =
                    DefaultCreator.applicationResponse.apply(NOT_FOUND_MSG, HttpStatus.NOT_FOUND.value());
        } else {
            applicationResponse =
                    DefaultCreator.applicationResponse.apply(FOUND_MSG, HttpStatus.OK.value());
            List<CoverArtResponse> coverArtResponses = new ArrayList<>();

            for (MusicBrainzReleaseGroup group : musicBrainzResponse.getReleaseGroups()) {
                CoverArtResponse coverArtResponse = coverArtProxy.fetchByMbid(group.getId());
                if (coverArtResponse != null) {
                    coverArtResponse.setTitle(group.getTitle());
                    coverArtResponse.setId(group.getId());
                    coverArtResponses.add(coverArtResponse);
                }
                if (coverArtResponses.size() > 5) {
                    break;
                }
            }

            String description = musicBrainzResponse.getRelations().stream()
                    .filter(r -> Objects.equals(r.getType(), "\"discogs\""))
                    .map(r -> {
                        String[] urlParams = r.getUrl().getResource().split("/");
                        String id = urlParams[urlParams.length-1];
                        return artistDescriptionProxy.fetchProfileDescription(id);
                    })
                    .collect(Collectors.toList()).get(0);

            applicationResponse
                    .setPayload(ResponseParser.populateMusicInfo(musicBrainzResponse, coverArtResponses, description));
        }
        cachedResponse.putIfAbsent(mbid,applicationResponse);
        return applicationResponse;
    }

}
