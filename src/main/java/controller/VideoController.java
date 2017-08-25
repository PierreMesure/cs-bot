package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pojo.Media;
import pojo.Movie;
import pojo.SearchType;
import pojo.TMDBResponse;
import pojo.TvShow;
import service.TMDBService;

@Controller
@RequestMapping("video/")
public class VideoController {

    @RequestMapping("search/{type}")
    @ResponseBody
    String search(@PathVariable String type, String query) {
      SearchType typeAsEnum;
      try {
        typeAsEnum = SearchType.valueOf(type);
      } catch (IllegalArgumentException e) {
        return "Wrong URL.";
      }

      TMDBService tmdbService = new TMDBService();
      TMDBResponse response = tmdbService.search(typeAsEnum, query);

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