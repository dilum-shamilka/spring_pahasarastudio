package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.BookingDTO;
import lk.ijse.pahasarastudiospringfinal.dto.ResponseDTO;
import lk.ijse.pahasarastudiospringfinal.service.BookingService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bookings")
@CrossOrigin
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // --- SAVE / NEW BOOKING ---
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            String res = bookingService.saveBooking(bookingDTO);
            if (res.equals(VarList.RSP_SUCCESS)) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(VarList.RSP_SUCCESS, "Booking Saved Successfully", bookingDTO));
            } else if (res.equals(VarList.RSP_DUPLICATED)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.RSP_DUPLICATED, "Slot Already Booked", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.RSP_ERROR, "Failed to Create Booking", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }

    // --- UPDATE BOOKING (Reschedule) ---
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            String res = bookingService.updateBooking(bookingDTO);
            if (res.equals(VarList.RSP_SUCCESS)) {
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new ResponseDTO(VarList.RSP_SUCCESS, "Booking Rescheduled Successfully", bookingDTO));
            } else if (res.equals(VarList.RSP_NO_DATA_FOUND)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Booking Not Found", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.RSP_ERROR, "Update Failed", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }

    // --- GET ALL (Schedule View) ---
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllBookings() {
        try {
            List<BookingDTO> bookings = bookingService.getAllBookings();
            if (bookings != null && !bookings.isEmpty()) {
                return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Schedule Retrieved", bookings));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "No Bookings Found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }

    // --- DELETE / CANCEL ---
    @DeleteMapping("/delete/{bookingID}")
    public ResponseEntity<ResponseDTO> deleteBooking(@PathVariable Long bookingID) {
        try {
            String res = bookingService.deleteBooking(bookingID);
            if (res.equals(VarList.RSP_SUCCESS)) {
                return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Booking Cancelled", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Booking ID Not Found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }
}