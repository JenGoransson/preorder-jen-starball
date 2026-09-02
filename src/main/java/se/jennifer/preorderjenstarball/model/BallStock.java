package se.jennifer.preorderjenstarball.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="ball_stock")
public class BallStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String type; // KL, KX, KR

    @Column(nullable = false)
    private int totalQuantity;

    @Column(nullable = false)
    private int reservedQuantity;

    @Version
    private Long version;

    // Getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(int reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    // Helper method
    public int getAvailableQuantity() {
        return totalQuantity - reservedQuantity;
    }

}
