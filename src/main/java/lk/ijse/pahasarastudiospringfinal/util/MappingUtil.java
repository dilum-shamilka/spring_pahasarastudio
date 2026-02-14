package lk.ijse.pahasarastudiospringfinal.util;

import lk.ijse.pahasarastudiospringfinal.dto.*;
import lk.ijse.pahasarastudiospringfinal.entity.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MappingUtil {

    // --- User Mappings ---
    public UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId().intValue(),
                user.getUsername(),
                null,
                user.getEmail(),
                user.getRole()
        );
    }

    public User toUserEntity(UserDTO dto) {
        User user = new User();
        if (dto.getUserId() != 0) {
            user.setId((long) dto.getUserId());
        }
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        return user;
    }

    // --- Studio Service Mappings (FIXED) ---
    public StudioServiceDTO toServiceDTO(StudioServiceEntity entity) {
        return new StudioServiceDTO(
                entity.getId(),
                entity.getServiceName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStatus()
        );
    }

    public StudioServiceEntity toServiceEntity(StudioServiceDTO dto) {
        StudioServiceEntity entity = new StudioServiceEntity();

        // Map ID only if it exists (important for updates)
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }

        entity.setServiceName(dto.getServiceName());
        entity.setDescription(dto.getDescription());

        // ✅ CRITICAL FIX: Handle Null Price safely to prevent 500 Error
        entity.setPrice(dto.getPrice() != null ? dto.getPrice() : 0.0);

        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "ACTIVE");
        return entity;
    }
    // Add these methods to your existing MappingUtil.java
    // Inside MappingUtil.java
    // Inside MappingUtil.java

    public PaymentDTO toPaymentDTO(Payment entity) {
        if (entity == null) return null;
        PaymentDTO dto = new PaymentDTO();
        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setTransactionTime(entity.getTransactionTime());

        // Mapping the relationship ID
        if (entity.getInvoice() != null) {
            dto.setInvoiceId(entity.getInvoice().getId());
        }

        return dto;
    }

    public Payment toPaymentEntity(PaymentDTO dto, Invoice invoice) {
        if (dto == null) return null;
        Payment entity = new Payment();

        // Crucial for Update functionality
        if (dto.getId() != null && dto.getId() != 0) {
            entity.setId(dto.getId());
        }

        entity.setAmount(dto.getAmount());
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setTransactionTime(dto.getTransactionTime() != null ?
                dto.getTransactionTime() : LocalDateTime.now());
        entity.setInvoice(invoice);

        return entity;
    }

    public Invoice toInvoiceEntity(InvoiceDTO dto, Booking booking) {
        Invoice entity = new Invoice();
        if (dto.getId() != null) entity.setId(dto.getId());
        entity.setInvoiceNumber(dto.getInvoiceNumber());
        entity.setDate(dto.getDate());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setStatus(dto.getPaymentStatus() != null ? dto.getPaymentStatus() : "UNPAID");
        entity.setBooking(booking);
        return entity;
    }
    // Add these to your existing MappingUtil.java
    // Add/Replace in MappingUtil.java
    // In MappingUtil.java
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

        if (dto.getId() != null && dto.getId() != 0) {
            entity.setId(dto.getId());
        }

        entity.setBookingDate(dto.getBookingDate());
        entity.setLocation(dto.getLocation() != null ? dto.getLocation() : "Studio");
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "PENDING");

        // Establishing the link to the database record
        entity.setClient(client);

        return entity;
    }
    public InventoryDTO toInventoryDTO(Inventory entity) {
        return new InventoryDTO(
                entity.getId(),
                entity.getItemName(),
                entity.getQuantity(),
                entity.getUnit(),
                entity.getReorderLevel()
        );
    }

    public Inventory toInventoryEntity(InventoryDTO dto) {
        Inventory entity = new Inventory();
        if (dto.getId() != null) entity.setId(dto.getId());
        entity.setItemName(dto.getItemName());
        entity.setQuantity(dto.getQuantity() != null ? dto.getQuantity() : 0);
        entity.setUnit(dto.getUnit());
        entity.setReorderLevel(dto.getReorderLevel() != null ? dto.getReorderLevel() : 0);
        entity.setStatus(entity.getQuantity() <= 0 ? "OUT_OF_STOCK" : "IN_STOCK");
        return entity;
    }
    // Add these to your existing MappingUtil.java
    public EquipmentDTO toEquipmentDTO(Equipment entity) {
        return new EquipmentDTO(
                entity.getId(),
                entity.getItemName(),
                null, // Category if you add it to the entity later
                entity.getSerialNumber(),
                entity.getStatus()
        );
    }

    public Equipment toEquipmentEntity(EquipmentDTO dto) {
        Equipment entity = new Equipment();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setItemName(dto.getItemName());
        entity.setSerialNumber(dto.getSerialNumber());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "AVAILABLE");
        // Handle images if provided in DTO, otherwise initialize empty list
        return entity;
    }
    // Replace the Client section in your MappingUtil.java with this:
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

        // Crucial for Updates: Only set ID if provided
        if (dto.getId() != null && dto.getId() != 0) {
            entity.setId(dto.getId());
        }

        // Use Ternary operators to prevent NullPointerExceptions during persistence
        entity.setFullName(dto.getName() != null ? dto.getName() : "");
        entity.setEmail(dto.getEmail() != null ? dto.getEmail() : "");
        entity.setPhoneNumber(dto.getContactNumber() != null ? dto.getContactNumber() : "");
        entity.setAddress(dto.getAddress() != null ? dto.getAddress() : "");

        return entity;
    }
}