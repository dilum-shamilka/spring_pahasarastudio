package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoFrameDTO {

    private Long id;
    private String frameName;
    private String material;
    private String size;
    private double unitPrice;
    private int qtyOnHand;
}
