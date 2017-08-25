package pojo.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Getter;

@Getter
public class TMDBResponse {

  @JsonProperty("page")
  private int page;

  @JsonProperty("total_results")
  private int totalResults;

  @JsonProperty("total_pages")
  private int totalPages;

  @JsonProperty("results")
  private List<Media> results;

  @JsonProperty("status_code")
  private int statusCode;

  @JsonProperty("status_message")
  private String statusMessage;
}