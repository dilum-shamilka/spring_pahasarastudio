package lk.ijse.pahasarastudiospringfinal.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
}