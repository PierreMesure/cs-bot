package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.messenger4j.send.buttons.Button;
import com.github.messenger4j.send.templates.ListTemplate;
import pojo.tmdb.Media;
import pojo.tmdb.Movie;
import pojo.tmdb.TMDBResponse;

import java.util.List;

public class FbMessageService {

    private final static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w160";

    public String createMovieList(TMDBResponse tmdbResponse) {

        List<Media> results = tmdbResponse.getResults();

        ListTemplate.Element.ListBuilder listTemplateBuilder = ListTemplate.newBuilder(ListTemplate.TopElementStyle.COMPACT)
                .addElements();

        for (int i = 0; i < results.size() && i < 3; i++) {
            Movie movie = (Movie) results.get(i);

            List<Button> elementButtons = Button.newListBuilder()
                    .addPostbackButton("Download", movie.getTitle()).toList()
                    .build();

            listTemplateBuilder.addElement(movie.getTitle())
                    .subtitle(movie.getYear())
                    .imageUrl(IMAGE_BASE_URL + movie.getPosterPath())
                    .buttons(elementButtons)
                    .toList();

        }
        List<Button> buttons = Button.newListBuilder()
                .addPostbackButton("View more", "more").toList()
                .build();

        ListTemplate listTemplate = listTemplateBuilder
                .done()
                .buttons(buttons)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String listAsJSON = null;
        try {
            listAsJSON = mapper.writeValueAsString(listTemplate);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return listAsJSON;
    }
}
