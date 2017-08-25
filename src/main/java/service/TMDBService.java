package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import lombok.NoArgsConstructor;
import pojo.tmdb.Media;
import pojo.tmdb.SearchType;
import pojo.tmdb.TMDBResponse;

@NoArgsConstructor
public class TMDBService {

  private final static String API_KEY = "f1b1f2c8ad1cc3667d7f3d76359d68d4";
  private final static String BASE_URL = "https://api.themoviedb.org/3";

  public TMDBResponse search(SearchType type, String query) {

    MediaDeserializer deserializer = new MediaDeserializer();

    SimpleModule module = new SimpleModule();
    module.addDeserializer(Media.class, deserializer);

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(module);

    String urlString = BASE_URL + "/search/%s?api_key=%s&language=en-US&page=1&query=%s";

    URL url = null;
    try {
      url = new URL(String.format(urlString, type, API_KEY, query.replaceAll(" ", "%20")));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    try {
      TMDBResponse response = mapper.readValue(url, TMDBResponse.class);

      if (response == null) {
        System.out.println("Error: Response is null");
      }
      else {
        System.out.println("Successfully retrieved movies for query \"" + query
                           + "\". " + response.getTotalResults() + " results found.");
      }

      return response;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}