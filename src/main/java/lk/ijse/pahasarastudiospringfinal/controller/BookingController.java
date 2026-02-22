package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.BookingDTO;
import lk.ijse.pahasarastudiospringfinal.dto.ResponseDTO;
import lk.ijse.pahasarastudiospringfinal.service.BookingService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@CrossOrigin(origins = "*") // Allows your Frontend to access the API
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllBookings() {
        try {
            List<BookingDTO> bookings = bookingService.getAllBookings();
            return new ResponseEntity<>(new ResponseDTO(VarList.RSP_SUCCESS, "Success", bookings), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO> getBookingById(@PathVariable Long id) {
        BookingDTO dto = bookingService.getBookingById(id);
        if (dto != null) {
            return new ResponseEntity<>(new ResponseDTO(VarList.RSP_SUCCESS, "Success", dto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Not Found", null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveBooking(@RequestBody BookingDTO dto) {
        String res = bookingService.saveBooking(dto);
        if (res.equals(VarList.RSP_SUCCESS)) {
            return new ResponseEntity<>(new ResponseDTO(VarList.RSP_SUCCESS, "Saved", null), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ResponseDTO(res, "Failed to Save", null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateBooking(@RequestBody BookingDTO dto) {
        String res = bookingService.updateBooking(dto);
        if (res.equals(VarList.RSP_SUCCESS)) {
            return new ResponseEntity<>(new ResponseDTO(VarList.RSP_SUCCESS, "Updated", null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO(res, "Update Failed", null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteBooking(@PathVariable Long id) {
        String res = bookingService.deleteBooking(id);
        if (res.equals(VarList.RSP_SUCCESS)) {
            return new ResponseEntity<>(new ResponseDTO(VarList.RSP_SUCCESS, "Deleted", null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO(res, "Delete Failed", null), HttpStatus.BAD_REQUEST);
        }
    }
}