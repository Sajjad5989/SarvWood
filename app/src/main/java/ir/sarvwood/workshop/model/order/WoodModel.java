package ir.sarvwood.workshop.model.order;

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
public class WoodModel implements Serializable {
    private CheckableObject woodType;
    private String color;
    private String pvcColor;
    private CheckableObject pvcThickness;
    //&....
}
