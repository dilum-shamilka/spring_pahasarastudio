package lk.ijse.pahasarastudiospringfinal.service.impl;

import lk.ijse.pahasarastudiospringfinal.dto.IndoorLocationBookingDTO;
import lk.ijse.pahasarastudiospringfinal.entity.IndoorLocationBooking;
import lk.ijse.pahasarastudiospringfinal.repo.IndoorLocationBookingRepository;
import lk.ijse.pahasarastudiospringfinal.service.IndoorLocationBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndoorLocationBookingServiceImpl
        implements IndoorLocationBookingService {

    private final IndoorLocationBookingRepository repository;

    @Override
    public IndoorLocationBookingDTO save(IndoorLocationBookingDTO dto) {

        var conflicts = repository
                .findByBookingDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                        dto.getBookingDate(),
                        dto.getEndTime(),
                        dto.getStartTime()
                );

        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Time slot already booked!");
        }

        IndoorLocationBooking saved = repository.save(mapToEntity(dto));

        return mapToDTO(saved);
    }

    @Override
    public IndoorLocationBookingDTO update(Long id,
                                           IndoorLocationBookingDTO dto) {

        IndoorLocationBooking existing = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

        existing.setLocationName(dto.getLocationName());
        existing.setShootType(dto.getShootType());
        existing.setCustomerName(dto.getCustomerName());
        existing.setContactNumber(dto.getContactNumber());
        existing.setBookingDate(dto.getBookingDate());
        existing.setStartTime(dto.getStartTime());
        existing.setEndTime(dto.getEndTime());
        existing.setPrice(dto.getPrice());
        existing.setStatus(dto.getStatus());

        return mapToDTO(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public IndoorLocationBookingDTO getById(Long id) {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));
    }

    @Override
    public List<IndoorLocationBookingDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private IndoorLocationBookingDTO mapToDTO(
            IndoorLocationBooking entity) {

        return IndoorLocationBookingDTO.builder()
                .id(entity.getId())
                .locationName(entity.getLocationName())
                .shootType(entity.getShootType())
                .customerName(entity.getCustomerName())
                .contactNumber(entity.getContactNumber())
                .bookingDate(entity.getBookingDate())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .price(entity.getPrice())
                .status(entity.getStatus())
                .build();
    }

    private IndoorLocationBooking mapToEntity(
            IndoorLocationBookingDTO dto) {

        return IndoorLocationBooking.builder()
                .id(dto.getId())
                .locationName(dto.getLocationName())
                .shootType(dto.getShootType())
                .customerName(dto.getCustomerName())
                .contactNumber(dto.getContactNumber())
                .bookingDate(dto.getBookingDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .price(dto.getPrice())
                .status(dto.getStatus())
                .build();
    }
}
