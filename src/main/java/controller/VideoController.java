package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pojo.ai.AiResponse;
import pojo.ai.Query;
import pojo.ai.VideoParameters;
import pojo.tmdb.Media;
import pojo.tmdb.Movie;
import pojo.tmdb.SearchType;
import pojo.tmdb.TMDBResponse;
import pojo.tmdb.TvShow;
import service.APIAIService;
import service.FbMessageService;
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
      TMDBResponse tmdbResponse = tmdbService.search(typeAsEnum,
                                                 ((VideoParameters) query.getResult().getParameters()).getMovie());

      if (tmdbResponse != null) {
        StringBuilder movieList = new StringBuilder();

        if (typeAsEnum == SearchType.movie) {
          for (Media media : tmdbResponse.getResults()) {
            movieList.append(((Movie) media).getTitle()).append("\n");
          }
        }
        else {
          for (Media media : tmdbResponse.getResults()) {
            movieList.append(((TvShow) media).getName()).append("\n");
          }
        }

        FbMessageService fbMessageService = new FbMessageService();

        AiResponse response = new AiResponse("Here are 3 movies matching your search",
                "Here are 3 movies matching your search",
                "{\n" +
                        "\"facebook\": {\n" +
                        "    \"attachment\": {\n" +
                        "      \"type\": \"template\",\n" +
                        "      \"payload\": {\n" +
                        "        \"template_type\": \"list\",\n" +
                        "        \"top_element_style\": \"compact\",\n" +
                        "        \"elements\": [\n" +
                        "          {\n" +
                        "            \"title\": \"Classic T-Shirt Collection\",\n" +
                        "            \"subtitle\": \"See all our colors\",\n" +
                        "            \"image_url\": \"https://image.tmdb.org/t/p/w640/mswBMPecmV7NpKTbMCpYuGzFqfh.jpg\",          \n" +
                        "            \"buttons\": [\n" +
                        "              {\n" +
                        "                \"title\": \"View\",\n" +
                        "                \"type\": \"web_url\",\n" +
                        "                \"url\": \"https://image.tmdb.org/t/p/w640/mswBMPecmV7NpKTbMCpYuGzFqfh.jpg\",\n" +
                        "                \"messenger_extensions\": true,\n" +
                        "                \"webview_height_ratio\": \"tall\",\n" +
                        "                \"fallback_url\": \"https://image.tmdb.org/t/p/w640/mswBMPecmV7NpKTbMCpYuGzFqfh.jpg/\"            \n" +
                        "              }\n" +
                        "            ]\n" +
                        "          },\n" +
                        "          {\n" +
                        "            \"title\": \"Classic White T-Shirt\",\n" +
                        "            \"subtitle\": \"See all our colors\",\n" +
                        "            \"default_action\": {\n" +
                        "              \"type\": \"web_url\",\n" +
                        "              \"url\": \"https://image.tmdb.org/t/p/w640/mswBMPecmV7NpKTbMCpYuGzFqfh.jpg\",\n" +
                        "              \"messenger_extensions\": true,\n" +
                        "              \"webview_height_ratio\": \"tall\",\n" +
                        "              \"fallback_url\": \"https://image.tmdb.org/t/p/w640/mswBMPecmV7NpKTbMCpYuGzFqfh.jpg/\"\n" +
                        "            }\n" +
                        "          },\n" +
                        "          {\n" +
                        "            \"title\": \"Classic Blue T-Shirt\",\n" +
                        "            \"image_url\": \"https://image.tmdb.org/t/p/w640/mswBMPecmV7NpKTbMCpYuGzFqfh.jpg\",\n" +
                        "            \"subtitle\": \"100% Cotton, 200% Comfortable\",\n" +
                        "            \"default_action\": {\n" +
                        "              \"type\": \"web_url\",\n" +
                        "              \"url\": \"https://image.tmdb.org/t/p/w640/mswBMPecmV7NpKTbMCpYuGzFqfh.jpg\",\n" +
                        "              \"messenger_extensions\": true,\n" +
                        "              \"webview_height_ratio\": \"tall\",\n" +
                        "              \"fallback_url\": \"https://image.tmdb.org/t/p/w640/mswBMPecmV7NpKTbMCpYuGzFqfh.jpg/\"\n" +
                        "            },\n" +
                        "            \"buttons\": [\n" +
                        "              {\n" +
                        "                \"title\": \"Shop Now\",\n" +
                        "                \"type\": \"web_url\",\n" +
                        "                \"url\": \"https://image.tmdb.org/t/p/w640/mswBMPecmV7NpKTbMCpYuGzFqfh.jpg\",\n" +
                        "                \"messenger_extensions\": true,\n" +
                        "                \"webview_height_ratio\": \"tall\",\n" +
                        "                \"fallback_url\": \"https://image.tmdb.org/t/p/w640/mswBMPecmV7NpKTbMCpYuGzFqfh.jpg/\"            \n" +
                        "              }\n" +
                        "            ]        \n" +
                        "          }\n" +
                        "        ],\n" +
                        "         \"buttons\": [\n" +
                        "          {\n" +
                        "            \"title\": \"View More\",\n" +
                        "            \"type\": \"postback\",\n" +
                        "            \"payload\": \"payload\"            \n" +
                        "          }\n" +
                        "        ]  \n" +
                        "      }\n" +
                        "    }\n" +
                        "  },\n" +
                        "\"kik\": {\n" +
                        "\"type\": \"\",\n" +
                        "\"body\": \"\"\n" +
                        "},\n" +
                        "\"slack\": {\n" +
                        "\"text\": \"\",\n" +
                        "\"attachments\": []\n" +
                        "},\n" +
                        "\"telegram\": {\n" +
                        "\"text\": \"\"\n" +
                        "}\n" +
                        "}\n", "", "TMDB");
        ObjectMapper mapper = new ObjectMapper();
        String finalResponse = null;
        try {
          finalResponse = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }
        return finalResponse;
      }
      return "Sorry, something went wrong.";
    }
}