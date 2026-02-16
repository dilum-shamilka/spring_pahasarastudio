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
@RequestMapping("/api/v1/invoices")
@CrossOrigin
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // ================== CREATE ==================
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        String res = invoiceService.saveInvoice(invoiceDTO);

        if (VarList.RSP_SUCCESS.equals(res)) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.RSP_SUCCESS, "Invoice Generated Successfully", invoiceDTO));
        } else if (VarList.RSP_DUPLICATED.equals(res)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.RSP_DUPLICATED, "Invoice Number or Booking Already Exists", null));
        } else if (VarList.RSP_NO_DATA_FOUND.equals(res)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Booking Not Found", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(VarList.RSP_ERROR, "Failed to Generate Invoice", null));
        }
    }

    // ================== UPDATE ==================
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        String res = invoiceService.updateInvoice(invoiceDTO);

        if (VarList.RSP_SUCCESS.equals(res)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ResponseDTO(VarList.RSP_SUCCESS, "Invoice Updated Successfully", invoiceDTO));
        } else if (VarList.RSP_DUPLICATED.equals(res)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.RSP_DUPLICATED, "Invoice Number Already Exists", null));
        } else if (VarList.RSP_NO_DATA_FOUND.equals(res)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Invoice or Booking Not Found", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(VarList.RSP_ERROR, "Update Failed", null));
        }
    }

    // ================== DELETE ==================
    @DeleteMapping("/delete/{invoiceID}")
    public ResponseEntity<ResponseDTO> deleteInvoice(@PathVariable Long invoiceID) {
        String res = invoiceService.deleteInvoice(invoiceID);
        if (VarList.RSP_SUCCESS.equals(res)) {
            return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Invoice Deleted Successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Invoice Not Found", null));
        }
    }

    // ================== GET ALL ==================
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllInvoices() {
        List<InvoiceDTO> invoices = invoiceService.getAllInvoices();
        if (invoices != null && !invoices.isEmpty()) {
            return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Success", invoices));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "No Invoices Found", null));
        }
    }

    // ================== GET BY ID ==================
    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO> getInvoiceById(@PathVariable Long id) {
        InvoiceDTO dto = invoiceService.getInvoiceById(id);
        if (dto != null) {
            return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Success", dto));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Invoice Not Found", null));
        }
    }

    // ================== GET BY NUMBER (OPTIONAL) ==================
    @GetMapping("/getByNumber/{invoiceNumber}")
    public ResponseEntity<ResponseDTO> getInvoiceByNumber(@PathVariable String invoiceNumber) {
        InvoiceDTO dto = invoiceService.getInvoiceByNumber(invoiceNumber);
        if (dto != null) {
            return ResponseEntity.ok(new ResponseDTO(VarList.RSP_SUCCESS, "Success", dto));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.RSP_NO_DATA_FOUND, "Invoice Not Found", null));
        }
    }
}