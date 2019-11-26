package ir.sarvwood.workshop.webservice.sarvwoodapi;

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
public class ResponseData<T> implements Serializable {

    private Integer code;
    private String message;
    private String status;
    private T data;
}
