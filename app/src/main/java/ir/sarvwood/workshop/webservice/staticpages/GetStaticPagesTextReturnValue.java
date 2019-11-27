package ir.sarvwood.workshop.webservice.staticpages;

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
public class GetStaticPagesTextReturnValue {

    private String title;
    private String body;
}
