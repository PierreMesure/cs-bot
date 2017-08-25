package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pojo.ai.Query;
import pojo.ai.VideoParameters;
import pojo.tmdb.Media;
import pojo.tmdb.Movie;
import pojo.tmdb.SearchType;
import pojo.tmdb.TMDBResponse;
import pojo.tmdb.TvShow;
import service.APIAIService;
import service.TMDBService;

@Controller
@RequestMapping("video/")
public class VideoController {

    @RequestMapping(value = "search/{type}", method = RequestMethod.POST)
    @ResponseBody
    String search(@PathVariable String type, @RequestBody String body) {
      SearchType typeAsEnum;
      try {
        typeAsEnum = SearchType.valueOf(type);
      } catch (IllegalArgumentException e) {
        return "Wrong URL.";
      }

      APIAIService apiaiService = new APIAIService();
      Query query = apiaiService.deserialize(body);

      TMDBService tmdbService = new TMDBService();
      TMDBResponse response = tmdbService.search(typeAsEnum,
                                                 ((VideoParameters) query.getResult().getParameters()).getMovie());

      if (response != null) {
        StringBuilder movieList = new StringBuilder();

        if (typeAsEnum == SearchType.movie) {
          for (Media media : response.getResults()) {
            movieList.append(((Movie) media).getTitle()).append("<br>");
          }
        }
        else {
          for (Media media : response.getResults()) {
            movieList.append(((TvShow) media).getName()).append("<br>");
          }
        }
        return movieList.toString();
      }
      return "Sorry, something went wrong.";
    }
}