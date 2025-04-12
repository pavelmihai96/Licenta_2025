package project.Licenta.services;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Licenta.models.Invoice;
import project.Licenta.models.Subscription;
import project.Licenta.payload.response.InvoiceResponse;
import project.Licenta.payload.response.SubscriptionResponse;
import project.Licenta.repositories.InvoiceRepository;
import project.Licenta.repositories.SubscriptionRepository;
import project.Licenta.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class InvoiceService {
    @Autowired
    private InvoiceRepository invRepository;

    @Autowired
    private UserRepository userRepository;

    public InvoiceService(InvoiceRepository invRepository) {
        this.invRepository = invRepository;
    }

    public List<InvoiceResponse> getAll(Long user_id) {
        List<Invoice> invList = invRepository.findAllById_UserId(user_id);
        List<InvoiceResponse> invoiceResponseList = new ArrayList<>();
        for (Invoice i : invList) {
            invoiceResponseList.add(new InvoiceResponse(i.getId().getProvider_id(), userRepository.findUsernameById(i.getId().getProvider_id()).getUsername(), i.getStatus(), i.getAmount(), i.getDue_date()));
        }
        return invoiceResponseList;
    }
}
