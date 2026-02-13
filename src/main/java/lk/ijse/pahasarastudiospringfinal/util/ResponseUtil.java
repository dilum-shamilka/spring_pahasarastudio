package lk.ijse.pahasarastudiospringfinal.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUtil {
    private String code;
    private String message;
    private Object data;
}