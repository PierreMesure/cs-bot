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

import pojo.ai.QueryParameters;
import pojo.ai.VideoParameters;

public class QueryParametersDeserializer extends StdDeserializer<QueryParameters> {

  private Map<String, Class<? extends QueryParameters>> registry = new HashMap<>();

  QueryParametersDeserializer() {
    super(QueryParameters.class);
    this.registry.put("movie", VideoParameters.class);
  }

  @Override
  public QueryParameters deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
    ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
    ObjectNode root = mapper.readTree(jsonParser);
    Class<? extends QueryParameters> parameterClass = null;
    Iterator<Map.Entry<String, JsonNode>> elementsIterator = root.fields();
    while (elementsIterator.hasNext()) {
      Map.Entry<String, JsonNode> element=elementsIterator.next();
      String name = element.getKey();
      if (registry.containsKey(name)) {
        parameterClass = registry.get(name);
        break;
      }
    }
    if (parameterClass == null) return null;

    return mapper.treeToValue(root, parameterClass);
  }
}