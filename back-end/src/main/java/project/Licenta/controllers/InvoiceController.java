package project.Licenta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.Licenta.payload.response.InvoiceResponse;
import project.Licenta.payload.response.SubscriptionResponse;
import project.Licenta.services.InvoiceService;
import project.Licenta.services.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/licenta/inv")
public class InvoiceController {
    @Autowired
    private InvoiceService service;

    public InvoiceController(InvoiceService service){
        this.service = service;
    }

    @GetMapping("/{user_id}")
    @PreAuthorize("hasRole('USER')")
    public List<InvoiceResponse> getAll(@PathVariable Long user_id) {
        return service.getAll(user_id);
    }
}
