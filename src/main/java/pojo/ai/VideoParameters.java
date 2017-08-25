package pojo.ai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoParameters extends QueryParameters {

  private String movie;
}