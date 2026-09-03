package se.jennifer.preorderjenstarball.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import se.jennifer.preorderjenstarball.model.Preorder;
import se.jennifer.preorderjenstarball.repository.PreorderRepository;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final PreorderRepository preorderRepository;

    @Value("${ADMIN_KEY}")
    private String adminKey;

    public AdminController(PreorderRepository preorderRepository) {
        this.preorderRepository = preorderRepository;
    }

    @GetMapping("/bookings")
    public List<Preorder> getAllBookings(@RequestParam String key) {

        if (!key.equals(adminKey)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access");
        }

        return preorderRepository.findAll();
    }
}
