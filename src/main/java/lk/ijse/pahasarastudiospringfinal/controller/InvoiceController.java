package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.InvoiceDTO;
import lk.ijse.pahasarastudiospringfinal.dto.ResponseDTO;
import lk.ijse.pahasarastudiospringfinal.service.InvoiceService;
import lk.ijse.pahasarastudiospringfinal.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/invoices")
@CrossOrigin
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // --- SAVE / GENERATE INVOICE ---
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        try {
            String res = invoiceService.saveInvoice(invoiceDTO);
            if (res.equals(VarList.RSP_SUCCESS)) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(VarList.RSP_SUCCESS, "Invoice Generated Successfully", invoiceDTO));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.RSP_ERROR, "Failed to Generate Invoice", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }

    // --- UPDATE INVOICE ---
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        try {
            String res = invoiceService.updateInvoice(invoiceDTO);
            if (res.equals(VarList.RSP_SUCCESS)) {
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new ResponseDTO(VarList.RSP_SUCCESS, "Invoice Updated Successfully", invoiceDTO));
            } else if (res.equals(VarList.RSP_NO_DATA_FOUND)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Invoice Not Found", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.RSP_ERROR, "Update Failed", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }

    // --- GET ALL ---
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllInvoices() {
        try {
            List<InvoiceDTO> invoices = invoiceService.getAllInvoices();
            if (invoices != null && !invoices.isEmpty()) {
                return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Success", invoices));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "No Invoices Found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }

    // --- DELETE ---
    @DeleteMapping("/delete/{invoiceID}")
    public ResponseEntity<ResponseDTO> deleteInvoice(@PathVariable Long invoiceID) {
        try {
            String res = invoiceService.deleteInvoice(invoiceID);
            if (res.equals(VarList.RSP_SUCCESS)) {
                return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Invoice Deleted Successfully", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Invoice Not Found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.RSP_ERROR, e.getMessage(), null));
        }
    }
}