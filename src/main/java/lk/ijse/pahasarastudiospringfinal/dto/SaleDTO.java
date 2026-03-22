package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private Long id;
    private String frameName;
    private String size;
    private Integer qty;
    private Double total;
    private String saleTime; // Formatted as String for JSON
}