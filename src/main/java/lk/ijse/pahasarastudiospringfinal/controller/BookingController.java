package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.BookingDTO;
import lk.ijse.pahasarastudiospringfinal.service.BookingService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bookings")
@CrossOrigin(origins = "*") // Allow all origins for frontend
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Fetch all bookings (for table)
    @GetMapping("/getAll")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();

        // Ensure serviceName is set (fallback)
        bookings.forEach(b -> {
            if (b.getServiceName() == null) b.setServiceName("Not Selected");
        });

        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> saveBooking(@RequestBody BookingDTO dto) {
        String res = bookingService.saveBooking(dto);
        return ResponseEntity.ok(Map.of(
                "code", res.equals(VarList.RSP_SUCCESS) ? "00" : "01",
                "status", res.equals(VarList.RSP_SUCCESS) ? "success" : "error",
                "message", res.equals(VarList.RSP_SUCCESS) ? "Booking saved!" : "Failed"
        ));
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> updateBooking(@RequestBody BookingDTO dto) {
        String res = bookingService.updateBooking(dto);
        return ResponseEntity.ok(Map.of(
                "code", res.equals(VarList.RSP_SUCCESS) ? "00" : "01",
                "status", res.equals(VarList.RSP_SUCCESS) ? "success" : "error",
                "message", res.equals(VarList.RSP_SUCCESS) ? "Booking updated!" : "Failed"
        ));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteBooking(@PathVariable Long id) {
        String res = bookingService.deleteBooking(id);
        return ResponseEntity.ok(Map.of(
                "code", res.equals(VarList.RSP_SUCCESS) ? "00" : "01",
                "status", res.equals(VarList.RSP_SUCCESS) ? "success" : "error",
                "message", res.equals(VarList.RSP_SUCCESS) ? "Booking deleted!" : "Failed"
        ));
    }

    // Optional: Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        BookingDTO dto = bookingService.getBookingById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }
}
