package pojo.ai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryResult {

  private String action;
  private boolean actionImcomplete;
  private QueryParameters parameters;
  private float score;
}