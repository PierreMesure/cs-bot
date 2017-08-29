package pojo.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AiResponse {

    private String speech;
    private String displayText;
    private String data;
    private String context;
    private String source;
}
