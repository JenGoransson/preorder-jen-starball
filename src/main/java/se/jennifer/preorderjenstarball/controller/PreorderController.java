package se.jennifer.preorderjenstarball.controller;

import org.springframework.web.bind.annotation.*;
import se.jennifer.preorderjenstarball.dto.MultiPreorderRequest;
import se.jennifer.preorderjenstarball.model.BallStock;
import se.jennifer.preorderjenstarball.model.Preorder;
import se.jennifer.preorderjenstarball.repository.BallStockRepository;
import se.jennifer.preorderjenstarball.repository.PreorderRepository;
import se.jennifer.preorderjenstarball.service.PreorderService;

import java.util.List;

@RestController
@RequestMapping("/preorders")
public class PreorderController {

    private final PreorderService preorderService;
    private final BallStockRepository ballStockRepository;
    private final PreorderRepository preorderRepository;

    public PreorderController(PreorderService preorderService,
                              BallStockRepository ballStockRepository,
                              PreorderRepository preorderRepository) {
        this.preorderService = preorderService;
        this.ballStockRepository = ballStockRepository;
        this.preorderRepository = preorderRepository;
    }

    @PostMapping("/multi")
    public List<Preorder> placeMulti(@RequestBody MultiPreorderRequest request) {
        return preorderService.placeMultiOrder(request);
    }

    @GetMapping("/stock")
    public List<BallStock> getStock() {
        return ballStockRepository.findAll();
    }

    @GetMapping
    public List<Preorder> getAllPreorders() {
        return preorderRepository.findAll();
    }
}

