package lk.ijse.pahasarastudiospringfinal.util;

import lk.ijse.pahasarastudiospringfinal.dto.*;
import lk.ijse.pahasarastudiospringfinal.entity.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MappingUtil {

    // ==================== USER MAPPINGS ====================
    public UserDTO toUserDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole() != null ? user.getRole().name() : "USER");
        dto.setPassword(null); // Never send hashed password to frontend
        return dto;
    }

    public User toUserEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        if (dto.getUserId() != null) user.setUserId(dto.getUserId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // raw password, Service will hash
        user.setRole(dto.getRole() != null ? User.Role.valueOf(dto.getRole()) : User.Role.USER);
        return user;
    }

    // ==================== EMAIL MAPPINGS ====================
    public EmailDTO toEmailDTO(Email entity) {
        if (entity == null) return null;

        EmailDTO dto = new EmailDTO();
        dto.setId(entity.getId());
        dto.setToEmail(entity.getToEmail());
        dto.setSubject(entity.getSubject());
        dto.setMessage(entity.getMessage());
        return dto;
    }

    public Email toEmailEntity(EmailDTO dto) {
        if (dto == null) return null;

        Email entity = new Email();
        if (dto.getId() != null) entity.setId(dto.getId());
        entity.setToEmail(dto.getToEmail());
        entity.setSubject(dto.getSubject());
        entity.setMessage(dto.getMessage());
        return entity;
    }
    // ==================== CLIENT MAPPINGS ====================
    public ClientDTO toClientDTO(Client entity) {
        if (entity == null) return null;

        ClientDTO dto = new ClientDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setContactNumber(entity.getPhoneNumber());
        dto.setAddress(entity.getAddress());
        return dto;
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

        BookingDTO dto = new BookingDTO();
        dto.setId(entity.getId());
        dto.setBookingDate(entity.getBookingDate());
        dto.setLocation(entity.getLocation());
        dto.setStatus(entity.getStatus());
        dto.setClientEmail(entity.getClient() != null ? entity.getClient().getEmail() : null);
        return dto;
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

        InventoryDTO dto = new InventoryDTO();
        dto.setId(entity.getId());
        dto.setItemName(entity.getItemName());
        dto.setQuantity(entity.getQuantity());
        dto.setUnit(entity.getUnit());
        dto.setReorderLevel(entity.getReorderLevel());
        dto.setStatus(entity.getStatus());
        return dto;
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

        EquipmentDTO dto = new EquipmentDTO();
        dto.setId(entity.getId());
        dto.setItemName(entity.getItemName());
        dto.setSerialNumber(entity.getSerialNumber());
        dto.setStatus(entity.getStatus());
        return dto;
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

        StudioServiceDTO dto = new StudioServiceDTO();
        dto.setId(entity.getId());
        dto.setServiceName(entity.getServiceName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setStatus(entity.getStatus());
        return dto;
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

        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(entity.getId());
        dto.setInvoiceNumber(entity.getInvoiceNumber());
        dto.setDate(entity.getDate());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setPaymentStatus(entity.getStatus());
        dto.setBookingId(entity.getBooking() != null ? entity.getBooking().getId() : null);
        return dto;
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
        if (entity == null) return null;

        PhotographerDTO dto = new PhotographerDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setSalary(entity.getSalary());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public Photographer toPhotographerEntity(PhotographerDTO dto) {
        if (dto == null) return null;

        Photographer entity = new Photographer();
        if (dto.getId() != null) entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setSalary(dto.getSalary());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "UNPAID");
        return entity;
    }

    // ==================== COST MANAGEMENT MAPPINGS ====================
    public CostDTO toCostDTO(CostManagement entity) {
        if (entity == null) return null;

        CostDTO dto = new CostDTO();
        dto.setCostId(entity.getCostId());
        dto.setStaffSalary(entity.getStaffSalary());
        dto.setEquipmentCost(entity.getEquipmentCost());
        dto.setMonthlyShootsCost(entity.getMonthlyShootsCost());
        dto.setVehicleOilCost(entity.getVehicleOilCost());
        dto.setInventoryCost(entity.getInventoryCost());
        dto.setEditCost(entity.getEditCost());
        dto.setPrintCost(entity.getPrintCost());
        dto.setDate(entity.getDate());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public CostManagement toCostEntity(CostDTO dto) {
        if (dto == null) return null;

        CostManagement entity = new CostManagement();
        if (dto.getCostId() != null) entity.setCostId(dto.getCostId());
        entity.setStaffSalary(dto.getStaffSalary() != null ? dto.getStaffSalary() : 0.0);
        entity.setEquipmentCost(dto.getEquipmentCost() != null ? dto.getEquipmentCost() : 0.0);
        entity.setMonthlyShootsCost(dto.getMonthlyShootsCost() != null ? dto.getMonthlyShootsCost() : 0.0);
        entity.setVehicleOilCost(dto.getVehicleOilCost() != null ? dto.getVehicleOilCost() : 0.0);
        entity.setInventoryCost(dto.getInventoryCost() != null ? dto.getInventoryCost() : 0.0);
        entity.setEditCost(dto.getEditCost() != null ? dto.getEditCost() : 0.0);
        entity.setPrintCost(dto.getPrintCost() != null ? dto.getPrintCost() : 0.0);
        entity.setDate(dto.getDate());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public List<CostDTO> toCostDTOList(List<CostManagement> entities) {
        return entities.stream().map(this::toCostDTO).collect(Collectors.toList());
    }
    // ==================== ALBUM MAPPINGS ====================

    public AlbumDTO toAlbumDTO(Album entity) {

        if (entity == null) return null;

        AlbumDTO dto = new AlbumDTO();
        dto.setId(entity.getId());
        dto.setAlbumName(entity.getAlbumName());
        dto.setAlbumType(entity.getAlbumType());
        dto.setPrice(entity.getPrice());
        dto.setStatus(entity.getStatus());

        if (entity.getClient() != null) {
            dto.setClientId(entity.getClient().getId());
            dto.setClientName(entity.getClient().getFullName());
        }

        return dto;
    }

    public Album toAlbumEntity(AlbumDTO dto, Client client) {

        if (dto == null) return null;

        Album entity = new Album();
        if (dto.getId() != null) entity.setId(dto.getId());

        entity.setAlbumName(dto.getAlbumName());
        entity.setAlbumType(dto.getAlbumType());
        entity.setPrice(dto.getPrice());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "PENDING");
        entity.setClient(client);

        return entity;
    }

}
