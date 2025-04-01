package project.Licenta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.Licenta.models.Subscription;
import project.Licenta.repositories.SubscriptionRepository;
import project.Licenta.repositories.UserRepository;
import project.Licenta.services.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/licenta/subs")
public class SubscriptionController {
    @Autowired
    private SubscriptionService service;

    public SubscriptionController(SubscriptionService service) {
        this.service = service;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('PROVIDER')")
    public List<Subscription> getAll() {
        return service.getAll();
    }


    @GetMapping("/{user_id}/{provider_id}")
    @PreAuthorize("hasRole('USER') or hasRole('PROVIDER')")
    public Subscription getById(@PathVariable Long user_id, @PathVariable Long provider_id) {
        return service.getById(user_id, provider_id);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('USER')")
    public String create(@RequestBody Subscription subscription) {
        return service.create(subscription);
    }

    /*
    @PutMapping("/{user_id}/{provider_id}")
    @PreAuthorize("hasRole('USER')")
    public Subscription update(@PathVariable Long user_id, @PathVariable Long provider_id, @RequestBody Subscription subscription) {
        return service.update(user_id, provider_id, subscription);
    }
     */

    @PutMapping("/{updateStatus}/{user_id}/{provider_id}")
    @PreAuthorize("hasRole('USER')")
    public void updateStatus(@PathVariable Long user_id, @PathVariable Long provider_id, @PathVariable String updateStatus) {
        service.updateStatus(user_id, provider_id, updateStatus);
    }

    /*
    @DeleteMapping("/{user_id}/{provider_id}")
    @PreAuthorize("hasRole('USER') or hasRole('PROVIDER')")
    public void delete(@PathVariable Long user_id, @PathVariable Long provider_id) {
        service.delete(user_id, provider_id);
    }
     */

}
