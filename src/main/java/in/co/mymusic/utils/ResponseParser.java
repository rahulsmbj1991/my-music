package in.co.mymusic.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import in.co.mymusic.dto.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Component
public class ResponseParser {

    private static ObjectMapper mapper = new ObjectMapper();

    public static Function<JsonNode, MusicBrainUrl> musicBrainzUrl = (jsonNode) -> {
        MusicBrainUrl musicBrainUrl;
        try {
            ObjectReader reader = mapper.readerFor(new TypeReference<MusicBrainUrl>() {
            });
            musicBrainUrl = reader.readValue(jsonNode);
        } catch (IOException e) {
            e.printStackTrace();
            musicBrainUrl = null;
        }
        return musicBrainUrl;
    };


    private static Function<JsonNode, List<MusicBrainzReleaseGroup>> musicBrainzReleaseGroups = (jsonNode) -> {
        List<MusicBrainzReleaseGroup> groups = new ArrayList<>();

        jsonNode.forEach(node -> {
            MusicBrainzReleaseGroup group = new MusicBrainzReleaseGroup();
            node.fields().forEachRemaining(n -> {
                switch (n.getKey()) {
                    case "title":
                        group.setTitle(n.getValue().toString());
                        break;
                    case "id":
                        group.setId(n.getValue().textValue());
                        break;
                }
            });
            if (group.getId() != null) {
                groups.add(group);
            }

        });
        return groups;
    };

    private static Function<JsonNode, List<MusicBrainzRelation>> musicBrainzRelations =
            (jsonNode) -> {
                List<MusicBrainzRelation> relations = new ArrayList<>();

                jsonNode.forEach(node -> {
                    MusicBrainzRelation relation = new MusicBrainzRelation();
                    node.fields().forEachRemaining(n -> {
                        switch (n.getKey()) {
                            case "url":
                                relation.setUrl(musicBrainzUrl.apply(n.getValue()));
                                break;
                            case "type":
                                relation.setType(n.getValue().toString());
                                break;
                        }
                    });
                    if (relation.getType() != null) {
                        relations.add(relation);
                    }

                });

                return relations;
            };

    public static Function<JsonNode, String> resourceUrl =
            (jsonNode) -> {
                final String[] resourceUrl = new String[1];
                jsonNode.fields().forEachRemaining(n -> {
                        switch (n.getKey()) {
                            case "profile":
                                resourceUrl[0] = n.getValue().toString();
                                break;
                        }
                    });


                return resourceUrl[0];
            };

    public static Function<JsonNode, MusicBrainzResponse> musicBrainz = (jsonNode) -> {

        MusicBrainzResponse musicBrainzResponse = new MusicBrainzResponse();

        jsonNode.fields().forEachRemaining(node -> {

            switch (node.getKey()) {
                case "type":
                    musicBrainzResponse.setType(node.getValue().toString());
                    break;
                case "sort_name":
                    musicBrainzResponse.setSortName(node.getValue().toString());
                    break;
                case "ipis":
                    break;
                case "end_area":
                    break;
                case "release-groups":
                    musicBrainzResponse.setReleaseGroups(musicBrainzReleaseGroups.apply(node.getValue()));
                    break;
                case "country":
                    musicBrainzResponse.setCountry(node.getValue().toString());
                    break;
                case "type-id":
                    musicBrainzResponse.setTypeId(node.getValue().toString());
                    break;
                case "gender-id":
                    musicBrainzResponse.setGenderId(node.getValue().toString());
                    break;
                case "area":
                    break;
                case "disambiguation":
                    musicBrainzResponse.setDisambiguation(node.getValue().toString());
                    break;
                case "id":
                    musicBrainzResponse.setId(node.getValue().toString());
                    break;
                case "name":
                    musicBrainzResponse.setName(node.getValue().toString());
                    break;
                case "gender":
                    musicBrainzResponse.setGender(node.getValue().toString());
                    break;
                case "begin_area":
                    break;
                case "isnis":
                    break;
                case "relations":
                    musicBrainzResponse.setRelations(musicBrainzRelations.apply(node.getValue()));
                    break;
                case "life-span":
                    break;
            }
        });

        return musicBrainzResponse;
    };

    private static Function<JsonNode, List<CoverArtImage>> coverArtImage = (jsonNode) -> {
        List<CoverArtImage> images = new ArrayList<>();
        try {
            ObjectReader reader = mapper.readerFor(new TypeReference<CoverArtImage>() {
            });
            images.add(reader.readValue(jsonNode.get(0)));
        } catch (IOException e) {
            e.printStackTrace();
            images = Collections.emptyList();
        }
        return images;
    };

    public static Function<JsonNode, CoverArtResponse> coverArt = (jsonNode) -> {

        CoverArtResponse coverArtResponse = new CoverArtResponse();

        jsonNode.fields().forEachRemaining(node -> {

            switch (node.getKey()) {
                case "release":
                    coverArtResponse.setRelease(node.getValue().toString());
                    break;

                case "images":
                    coverArtResponse.setImages(coverArtImage.apply(node.getValue()));
                    break;
            }
        });

        return coverArtResponse;
    };

    private static List<AlbumInfo> populateAlbumInfo(List<CoverArtResponse> coverArtResponses) {
        List<AlbumInfo> albumInfos;

        if (!coverArtResponses.isEmpty()) {
            albumInfos = new ArrayList<>();
            coverArtResponses.forEach(coverArtResponse -> {
                AlbumInfo albumInfo = new AlbumInfo();
                albumInfo.setImage(coverArtResponse.getImages().get(0).getImage())
                        .setTitle(coverArtResponse.getTitle())
                        .setId(coverArtResponse.getId());
                albumInfos.add(albumInfo);
            });
        } else {
            albumInfos = Collections.emptyList();
        }

        return albumInfos;
    }

    public static MusicInfo populateMusicInfo(MusicBrainzResponse musicBrainzResponse,
                                              List<CoverArtResponse> coverArtResponses,
                                              String description) {
        MusicInfo musicInfo = new MusicInfo();
        musicInfo.setMbid(musicBrainzResponse.getId())
                .setAlbums(populateAlbumInfo(coverArtResponses))
                .setDescription(description);

        return musicInfo;
    }
}
