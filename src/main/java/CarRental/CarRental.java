package CarRental;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table
public class CarRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private BigDecimal punishment;

    @Column
    private LocalDateTime startPeriod;

    @Column
    private LocalDateTime endPeriod;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public CarRental() {
    }


    public CarRental(double punishment, LocalDateTime startPeriod, LocalDateTime endPeriod, User user, Car car) {
        this.punishment = BigDecimal.valueOf(punishment);
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.user = user;
        this.car = car;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPunishment() {
        return punishment;
    }

    public void setPunishment(double punishment) {
        this.punishment = BigDecimal.valueOf(punishment);
    }

    public LocalDateTime getStartPeriod() {
        return startPeriod.withNano(0);
    }

    public void setStartPeriod(LocalDateTime startPeriod) {
        this.startPeriod = startPeriod.withNano(0);
    }

    public LocalDateTime getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(LocalDateTime endPeriod) {
        this.endPeriod = endPeriod;
    }

}
