package pojo.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import lombok.Getter;

@Getter
public class Movie extends Media {

  @JsonProperty("title")
  private String title;

  @JsonProperty("original_title")
  private String originalTitle;

  @JsonProperty("release_date")
  private Date releaseDate;

  @JsonProperty("video")
  private boolean video;

  @JsonProperty("adult")
  private boolean adult;
}