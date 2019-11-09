package ir.sarvwood.workshop.model;

import java.io.Serializable;

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
public class ResponseData< T > implements Serializable {
    private int code;
    private String message;
    private String status;
    private T data;

}
