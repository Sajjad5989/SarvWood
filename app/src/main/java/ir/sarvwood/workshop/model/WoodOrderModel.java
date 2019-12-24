package ir.sarvwood.workshop.model;


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

public class WoodOrderModel {

    private float pieceLength;
    private float pieceWidth;
    private int pieceCount;
    private int pvcLenghtNo;
    private int pvcWidthNo;

    private int persianCutLenghtNo;
    private int persianCutWidthNo;
    private int grooveLenghtNo;
    private int grooveWidthNo;
    private String desc;

}
