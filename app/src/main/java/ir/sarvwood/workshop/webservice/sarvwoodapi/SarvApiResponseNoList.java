package ir.sarvwood.workshop.webservice.sarvwoodapi;

import java.io.Serializable;
import java.util.List;

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
public class SarvApiResponseNoList implements Serializable {
    private int code;
    private String message;
    private String status;


}
