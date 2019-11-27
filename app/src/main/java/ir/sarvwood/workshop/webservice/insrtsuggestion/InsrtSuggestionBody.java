package ir.sarvwood.workshop.webservice.insrtsuggestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InsrtSuggestionBody {

    private int type;
    private int reporterId;
    private String comments;
}
