package ir.sarvwood.workshop.webservice.discardoption;

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

public class DiscardOptionReturnValue {
    private int id;
    private String title;
}
