package lk.ijse.pahasarastudiospringfinal.util;

import lk.ijse.pahasarastudiospringfinal.dto.*;
import lk.ijse.pahasarastudiospringfinal.entity.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MappingUtil {

    // ==================== USER MAPPINGS ====================
    public UserDTO toUserDTO(User user) {
        if(user == null) return null;
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                null, // never expose password
                user.getEmail(),
                user.getRole() != null ? user.getRole() : "USER"
        );
    }

    public User toUserEntity(UserDTO dto, String encodedPassword) {
        if(dto == null) return null;
        User user = new User();
        if(dto.getUserId() != null) user.setId(dto.getUserId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole() != null ? dto.getRole() : "USER");
        if(encodedPassword != null && !encodedPassword.isEmpty()) {
            user.setPassword(encodedPassword);
        }
        return user;
    }

    // ==================== CLIENT MAPPINGS ====================
    public ClientDTO toClientDTO(Client entity) {
        if (entity == null) return null;
        return new ClientDTO(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getAddress()
        );
    }

    public Client toClientEntity(ClientDTO dto) {
        if (dto == null) return null;
        Client entity = new Client();
        if (dto.getId() != null) entity.setId(dto.getId());
        entity.setFullName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getContactNumber());
        entity.setAddress(dto.getAddress());
        return entity;
    }

    // ==================== BOOKING MAPPINGS ====================
    public BookingDTO toBookingDTO(Booking entity) {
        if (entity == null) return null;
        return new BookingDTO(
                entity.getId(),
                entity.getBookingDate(),
                entity.getLocation(),
                entity.getStatus(),
                entity.getClient() != null ? entity.getClient().getEmail() : null
        );
    }

    public Booking toBookingEntity(BookingDTO dto, Client client) {
        if (dto == null) return null;
        Booking entity = new Booking();
        if (dto.getId() != null && dto.getId() != 0) entity.setId(dto.getId());
        entity.setBookingDate(dto.getBookingDate());
        entity.setLocation(dto.getLocation() != null ? dto.getLocation() : "Studio");
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "PENDING");
        entity.setClient(client);
        return entity;
    }

    // ==================== INVENTORY MAPPINGS ====================
    public InventoryDTO toInventoryDTO(Inventory entity) {
        if (entity == null) return null;
        return new InventoryDTO(
                entity.getId(),
                entity.getItemName(),
                entity.getQuantity(),
                entity.getUnit(),
                entity.getReorderLevel(),
                entity.getStatus()
        );
    }

    public Inventory toInventoryEntity(InventoryDTO dto) {
        if (dto == null) return null;
        Inventory entity = new Inventory();
        if (dto.getId() != null) entity.setId(dto.getId());
        entity.setItemName(dto.getItemName());
        entity.setQuantity(dto.getQuantity() != null ? dto.getQuantity() : 0);
        entity.setUnit(dto.getUnit());
        entity.setReorderLevel(dto.getReorderLevel() != null ? dto.getReorderLevel() : 0);
        if (entity.getQuantity() == 0) entity.setStatus("OUT_OF_STOCK");
        else if (entity.getQuantity() <= entity.getReorderLevel()) entity.setStatus("LOW_STOCK");
        else entity.setStatus("IN_STOCK");
        return entity;
    }

    // ==================== EQUIPMENT MAPPINGS ====================
    public EquipmentDTO toEquipmentDTO(Equipment entity) {
        if (entity == null) return null;
        return new EquipmentDTO(
                entity.getId(),
                entity.getItemName(),
                null,
                entity.getSerialNumber(),
                entity.getStatus()
        );
    }

    public Equipment toEquipmentEntity(EquipmentDTO dto) {
        if (dto == null) return null;
        Equipment entity = new Equipment();
        if (dto.getId() != null) entity.setId(dto.getId());
        entity.setItemName(dto.getItemName());
        entity.setSerialNumber(dto.getSerialNumber());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "AVAILABLE");
        return entity;
    }

    // ==================== STUDIO SERVICE MAPPINGS ====================
    public StudioServiceDTO toServiceDTO(StudioServiceEntity entity) {
        if (entity == null) return null;
        return new StudioServiceDTO(
                entity.getId(),
                entity.getServiceName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStatus()
        );
    }

    public StudioServiceEntity toServiceEntity(StudioServiceDTO dto) {
        if (dto == null) return null;
        StudioServiceEntity entity = new StudioServiceEntity();
        if (dto.getId() != null) entity.setId(dto.getId());
        entity.setServiceName(dto.getServiceName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice() != null ? dto.getPrice() : 0.0);
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "ACTIVE");
        return entity;
    }

    // ==================== PAYMENT MAPPINGS ====================
    public PaymentDTO toPaymentDTO(Payment entity) {
        if (entity == null) return null;
        PaymentDTO dto = new PaymentDTO();
        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setTransactionTime(entity.getTransactionTime());
        if (entity.getInvoice() != null) dto.setInvoiceId(entity.getInvoice().getId());
        return dto;
    }

    public Payment toPaymentEntity(PaymentDTO dto, Invoice invoice) {
        if (dto == null) return null;
        Payment entity = new Payment();
        if (dto.getId() != null && dto.getId() != 0) entity.setId(dto.getId());
        entity.setAmount(dto.getAmount());
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setTransactionTime(dto.getTransactionTime() != null ? dto.getTransactionTime() : LocalDateTime.now());
        entity.setInvoice(invoice);
        return entity;
    }

    // ==================== INVOICE MAPPINGS ====================
    public InvoiceDTO toInvoiceDTO(Invoice entity) {
        if (entity == null) return null;
        return new InvoiceDTO(
                entity.getId(),
                entity.getInvoiceNumber(),
                entity.getDate(),
                entity.getTotalAmount(),
                entity.getStatus(),
                entity.getBooking() != null ? entity.getBooking().getId() : null
        );
    }

    public Invoice toInvoiceEntity(InvoiceDTO dto, Booking booking) {
        if (dto == null) return null;
        Invoice entity = new Invoice();
        if (dto.getId() != null) entity.setId(dto.getId());
        entity.setInvoiceNumber(dto.getInvoiceNumber());
        entity.setDate(dto.getDate());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setStatus(dto.getPaymentStatus() != null ? dto.getPaymentStatus() : "UNPAID");
        entity.setBooking(booking);
        return entity;
    }

    // ==================== PHOTOGRAPHER MAPPINGS ====================
    public PhotographerDTO toPhotographerDTO(Photographer entity) {
        if(entity == null) return null;
        return new PhotographerDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getSalary(),
                entity.getStatus()
        );
    }

    public Photographer toPhotographerEntity(PhotographerDTO dto) {
        if(dto == null) return null;
        Photographer entity = new Photographer();
        if(dto.getId() != null) entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setSalary(dto.getSalary());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "UNPAID");
        return entity;
    }
}
