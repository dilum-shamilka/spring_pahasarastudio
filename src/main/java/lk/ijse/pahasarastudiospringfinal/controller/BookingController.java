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

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveBooking(@RequestBody BookingDTO dto) {
        String res = bookingService.saveBooking(dto);
        switch (res) {
            case VarList.RSP_SUCCESS:
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(res, "Booking Saved Successfully", dto));
            case VarList.RSP_DUPLICATED:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(res, "Slot Already Booked", null));
            case VarList.RSP_NO_DATA_FOUND:
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(res, "Client Not Found", null));
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.RSP_ERROR, "Failed to Save Booking", null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateBooking(@RequestBody BookingDTO dto) {
        String res = bookingService.updateBooking(dto);
        switch (res) {
            case VarList.RSP_SUCCESS:
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new ResponseDTO(res, "Booking Updated Successfully", dto));
            case VarList.RSP_NO_DATA_FOUND:
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(res, "Booking or Client Not Found", null));
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.RSP_ERROR, "Update Failed", null));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllBookings() {
        List<BookingDTO> list = bookingService.getAllBookings();
        if (list.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "No Bookings Found", null));
        return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Bookings Retrieved", list));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteBooking(@PathVariable Long id) {
        String res = bookingService.deleteBooking(id);
        if (res.equals(VarList.RSP_SUCCESS))
            return ResponseEntity.ok(new ResponseDTO(res, "Booking Cancelled", null));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO(res, "Booking ID Not Found", null));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ResponseDTO> updateStatus(@PathVariable Long id, @RequestParam String status) {
        String res = bookingService.updateBookingStatus(id, status);
        if (res.equals(VarList.RSP_SUCCESS))
            return ResponseEntity.ok(new ResponseDTO(res, "Booking Status Updated", null));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO(res, "Booking ID Not Found", null));
    }
}
