package service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import pojo.Media;
import pojo.Movie;
import pojo.TvShow;

public class MediaDeserializer extends StdDeserializer<Media> {

private Map<String, Class<? extends Media>> registry = new HashMap<>();

  MediaDeserializer() {
    super(Media.class);
    this.registry.put("title", Movie.class);
    this.registry.put("name", TvShow.class);
  }

  @Override
  public Media deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
    ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
    ObjectNode root = mapper.readTree(jsonParser);
    Class<? extends Media> mediaClass = null;
    Iterator<Entry<String, JsonNode>> elementsIterator = root.fields();
    while (elementsIterator.hasNext()) {
      Entry<String, JsonNode> element=elementsIterator.next();
      String name = element.getKey();
      if (registry.containsKey(name)) {
        mediaClass = registry.get(name);
        break;
      }
    }
    if (mediaClass == null) return null;

    Media media = mapper.treeToValue(root, mediaClass);
    return media;
  }
}