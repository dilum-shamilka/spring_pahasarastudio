package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.PaymentDTO;
import lk.ijse.pahasarastudiospringfinal.dto.ResponseDTO;
import lk.ijse.pahasarastudiospringfinal.service.PaymentService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/payment")
@CrossOrigin
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> savePayment(@RequestBody PaymentDTO paymentDTO) {
        String res = paymentService.processPayment(paymentDTO);
        if (res.equals(VarList.RSP_SUCCESS)) {
            return new ResponseEntity<>(new ResponseDTO(VarList.RSP_SUCCESS, "Saved", paymentDTO), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ResponseDTO(res, "Failed", null), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updatePayment(@RequestBody PaymentDTO paymentDTO) {
        String res = paymentService.updatePayment(paymentDTO);
        if (res.equals(VarList.RSP_SUCCESS)) {
            return new ResponseEntity<>(new ResponseDTO(VarList.RSP_SUCCESS, "Updated", paymentDTO), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(new ResponseDTO(res, "Not Found", null), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllPayments() {
        List<PaymentDTO> list = paymentService.getAllPayments();
        return new ResponseEntity<>(new ResponseDTO(VarList.RSP_SUCCESS, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deletePayment(@PathVariable Long id) {
        String res = paymentService.deletePayment(id);
        if (res.equals(VarList.RSP_SUCCESS)) {
            return new ResponseEntity<>(new ResponseDTO(VarList.RSP_SUCCESS, "Deleted", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(res, "Not Found", null), HttpStatus.NOT_FOUND);
    }
}