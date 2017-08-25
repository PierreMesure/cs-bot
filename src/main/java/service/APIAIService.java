package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

import pojo.ai.Query;
import pojo.ai.QueryParameters;

public class APIAIService {

  public Query deserialize(String jsonString) {
    QueryParametersDeserializer deserializer = new QueryParametersDeserializer();

    SimpleModule module = new SimpleModule();
    module.addDeserializer(QueryParameters.class, deserializer);

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(module);

    try {
      return mapper.readValue(jsonString, Query.class);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}