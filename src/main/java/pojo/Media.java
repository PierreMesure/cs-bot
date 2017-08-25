package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class Media {

  @JsonProperty("id")
  private int id;

  @JsonProperty("overview")
  private String overview;

  @JsonProperty("popularity")
  private float popularity;

  @JsonProperty("vote_average")
  private float voteAverage;

  @JsonProperty("vote_count")
  private int voteCount;

  @JsonProperty("genre_ids")
  private int[] genreIds;

  @JsonProperty("original_language")
  private String originalLanguage;

  @JsonProperty("poster_path")
  private String posterPath;

  @JsonProperty("backdrop_path")
  private String backdropPath;
}