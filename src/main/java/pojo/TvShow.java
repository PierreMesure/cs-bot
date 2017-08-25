package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import lombok.Getter;

@Getter
public class TvShow extends Media {

  @JsonProperty("name")
  private String name;

  @JsonProperty("original_name")
  private String originalName;

  @JsonProperty("first_air_date")
  private Date firstAirDate;

  @JsonProperty("origin_country")
  private String[] originCountry;
}