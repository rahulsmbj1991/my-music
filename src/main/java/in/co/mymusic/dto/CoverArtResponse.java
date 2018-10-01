package in.co.mymusic.dto;

import java.util.List;

public class CoverArtResponse {

  private List<CoverArtImage> images;
  private String release;
  private String title;
  private String id;

  public List<CoverArtImage> getImages() {
    return images;
  }

  public CoverArtResponse setImages(List<CoverArtImage> images) {
    this.images = images;
    return this;
  }

  public String getRelease() {
    return release;
  }

  public CoverArtResponse setRelease(String release) {
    this.release = release;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public CoverArtResponse setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getId() {
    return id;
  }

  public CoverArtResponse setId(String id) {
    this.id = id;
    return this;
  }
}
