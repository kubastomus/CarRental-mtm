package CarRental;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String brand;

    @Column
    private String model;

    @Column
    private String color;

    @Column
    private String registrationNumber;

    @Column
    private BigDecimal price;

    @Column
    private boolean efficient;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "car", orphanRemoval = true)
    private Set<CarRental> carRentals = new HashSet<>();

    public Set<CarRental> getCarRentals() {
        return carRentals;
    }

    public void setCarRentals(Set<CarRental> carRentals) {
        this.carRentals = carRentals;
    }


    //    @Column(name = "condition")
//    @Enumerated(EnumType.STRING)
//    private Condition condition;


    public Car() {
    }


    public Car(String brand, String model, String color, String registrationNumber, double price, boolean efficient) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.registrationNumber = registrationNumber;
        this.price = BigDecimal.valueOf(price);
        this.efficient = efficient;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = BigDecimal.valueOf(price);
    }

    public boolean isEfficient() {
        return efficient;
    }

    public void setEfficient(boolean efficient) {
        this.efficient = efficient;
    }
}
