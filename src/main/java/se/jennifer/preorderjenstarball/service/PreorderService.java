package se.jennifer.preorderjenstarball.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import se.jennifer.preorderjenstarball.dto.MultiPreorderRequest;
import se.jennifer.preorderjenstarball.dto.PreorderItem;
import se.jennifer.preorderjenstarball.error.StockException;
import se.jennifer.preorderjenstarball.model.BallStock;
import se.jennifer.preorderjenstarball.model.Preorder;
import se.jennifer.preorderjenstarball.repository.BallStockRepository;
import se.jennifer.preorderjenstarball.repository.PreorderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PreorderService {

    private final BallStockRepository ballStockRepository;
    private final PreorderRepository preorderRepository;
    // ⭐ Lägg till detta

    public PreorderService(
            BallStockRepository ballStockRepository,
            PreorderRepository preorderRepository

    ) {
        this.ballStockRepository = ballStockRepository;
        this.preorderRepository = preorderRepository;

    }

    @Transactional
    public List<Preorder> placeMultiOrder(MultiPreorderRequest req) {

        // ⭐ Din validering här (namn, email, items, etc.)

        List<Preorder> created = new ArrayList<>();

        for (PreorderItem item : req.getItems()) {

            // ⭐ Lagerkontroll
            BallStock stock = ballStockRepository.findByType(item.getType())
                    .orElseThrow(() -> new StockException("Typen " + item.getType() + " finns inte i lager."));

            int available = stock.getTotalQuantity() - stock.getReservedQuantity();
            if (available < item.getQuantity()) {
                throw new StockException("Det finns inte tillräckligt många " + item.getType() + " i lager.");
            }

            // ⭐ Uppdatera lager
            stock.setReservedQuantity(stock.getReservedQuantity() + item.getQuantity());
            ballStockRepository.save(stock);

            // ⭐ Skapa orderrad
            Preorder preorder = new Preorder();
            preorder.setCustomerName(req.getCustomerName());
            preorder.setCustomerEmail(req.getCustomerEmail());
            preorder.setStreet(req.getStreet());
            preorder.setPostalCode(req.getPostalCode());
            preorder.setCity(req.getCity());

            preorder.setType(item.getType());
            preorder.setQuantity(item.getQuantity());

            created.add(preorderRepository.save(preorder));
        }

        return created;
    }
}
