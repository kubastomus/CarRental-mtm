package CarRental;

import ConnectionTest.HibernateUtil;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    public static EntityManager em = sessionFactory.createEntityManager();


    public static void main(String[] args) {


        // odniesienie do metod dane początkowe, dodawanie, usuwanie, edycja, rozliczanie wypożyczeń, itd
        loadInitialData();

        addUser("Jan", "Kowal");
        addCar("Lamborghini", "Aventador", "Greey", "PO KING1", 500, true);
        addCar("Porsche", "Panamera S", "Silver", "PZ 02546", 450, true);
        addCar("Dodge", "Challenger", "Red", "PZ DODGE", 550, true);
//        deleteCar(6);
//        deleteUser(5);
        increaseEndPeriod(1, 0, 20);
        finishPeriodAndSummary(2, false,1000, 400, 1);

        addPeriod(33, 2,0, 5, 8);
        addPeriod(44, 3, 0, 6, 7);

        // this write your CarRenatal code ;)


        HibernateUtil.shutdown();
    }

    public static void loadInitialData(){

        em.getTransaction().begin();


        User user1 = new User("Alina", "Wojciechowska");
        User user2 = new User("Jan", "Kowalski");
        User user3 = new User("Ewa", "Niewolińska");
        User user4 = new User("Wojciech", "Kowalski");
        User user5 = new User("Karol", "Janowski");

        Car car1 = new Car("Toyota", "Corolla", "Green", "PO HG142", 150, true);
        Car car2 = new Car("Volvo", "V40", "Blue", "PO 458OP", 250, true);
        Car car3 = new Car("Mercedes", "S500", "Black", "PO 236WZ", 350, true);
        Car car4 = new Car("Audi", "RS6", "Silver", "PO 89712", 365, true);
        Car car5 = new Car("Lincoln", "MKZ", "Black", "PO 014CA", 320, true);

        CarRental period1 = new CarRental(200,
                LocalDateTime.of(2022, 04, 13, 15, 00),
                LocalDateTime.of(2022, 04, 13, 21, 10), user2, car1);

        CarRental period2 = new CarRental(0,
                LocalDateTime.of(2022, 04, 11, 21, 00),
                LocalDateTime.of(2022, 04, 11, 23, 30), user1, car2);


        em.persist(car1);
        em.persist(car2);
        em.persist(car3);
        em.persist(car4);
        em.persist(car5);
        em.persist(period1);
        em.persist(period2);
        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);
        em.persist(user5);


        em.getTransaction().commit();

    }

    public static void addUser(String name, String surname){

        em.getTransaction().begin();

        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        em.persist(user);
        System.out.println("Added new user: " + user.getName() + " " + user.getSurname());

        em.getTransaction().commit();
    }

    public static void deleteUser(int rowsDelete){

        em.getTransaction().begin();

        Query query=em.createQuery("delete from User u where u.id=:id");
        query.setParameter("id", rowsDelete);
        query.executeUpdate();
        System.out.println("Deleted user of value Id: " + rowsDelete);

        em.getTransaction().commit();
    }

    public static void addCar(String brand, String model, String color, String regNr, double price, boolean efficientValue){

        em.getTransaction().begin();

        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setColor(color);
        car.setRegistrationNumber(regNr);
        car.setPrice(price);
        car.setEfficient(efficientValue);
        em.persist(car);
        System.out.println("Added new car: " + car.getBrand() + " " + car.getModel());

        em.getTransaction().commit();
    }

    public static void deleteCar(int rowsDelete){

        em.getTransaction().begin();

        Query query = em.createQuery("delete from Car c where c.id=:id");
        query.setParameter("id", rowsDelete);
        query.executeUpdate();
        System.out.println("Deleted car of value Id: " + rowsDelete);

        em.getTransaction().commit();
    }

    public static void addPeriod(double punishment, int valueHours, int valueMinutes, int valueUserId, int valueCarId){

        em.getTransaction().begin();

        //użycie select z SQL, aby wyciągnąc konkretnego Usera po jego id do listy, a następnie przypisać pod seter do wypożyczenia
        Query queryUser = em.createQuery("from User as u where u.id=:id");
        queryUser.setParameter("id", valueUserId);
        List<User> userList = queryUser.getResultList();

        // sprawdzamy czy Car o konkretnym Id jest tuż wypożyczony, aby ustawić warunek, że nie można wypożyczyć go przez zwróceniem
        List<CarRental> resultAllCar = em.createQuery("select r from CarRental r "
                        + "join fetch r.car "
                        + "where r.car.id =:id", CarRental.class)
                .setParameter("id", valueCarId)
                .getResultList();

        // analogicznie jak dodawanie Usera, dodajemy wybrany Car
        Query queryCar = em.createQuery("from Car as c where c.id=:id");
        queryCar.setParameter("id", valueCarId);
        List<Car> carList = queryCar.getResultList();

        // na podstawie rozmiaru powyższych list (czy wybrany Car jest wypożyczony, lub jest w bazie Car) ustawiam warunek nowego wypożyczenia
        if (resultAllCar.size() > 0 || carList.size() == 0 || (carList.size() > 0 && carList.get(0).isEfficient() == false)){
            System.out.println("The selected car is currently rented or unavailable !!!");
        } else {
            CarRental period = new CarRental();
            period.setPunishment(punishment);
            period.setStartPeriod(LocalDateTime.now().withSecond(0).withNano(0));
            period.setEndPeriod(period.getStartPeriod().plusHours(valueHours).plusMinutes(valueMinutes));
            period.setUser(userList.get(0));
            period.setCar(carList.get(0));
            em.persist(period);

            System.out.println("User " + userList.get(0).getName() + " " + userList.get(0).getSurname() +
                    " render a car " + carList.get(0).getBrand() + carList.get(0).getModel() +
                    " with a registration number " + carList.get(0).getRegistrationNumber() +
                    " for a priod of " + valueHours + " hour/hours " + valueMinutes + " minute/minutes from now");
        }

        em.getTransaction().commit();
    }

    public static void increaseEndPeriod(int selectUserId, int addHour, int addMinut){

        em.getTransaction().begin();

        List<CarRental> resultTime = em.createQuery("select r from CarRental r "
                        + "join fetch r.user "
                        + "where r.user.id =:id", CarRental.class)
                .setParameter("id", selectUserId)
                .getResultList();

        resultTime.forEach(t -> System.out.println("Period before increase: " + t.getStartPeriod() + " " + t.getEndPeriod()));

        for (CarRental t: resultTime){
            t.setEndPeriod(t.getEndPeriod().plusHours(addHour).plusMinutes(addMinut));
            System.out.println("Final end period after increase: " + t.getEndPeriod());
        }

        em.getTransaction().commit();
    }

    public static void finishPeriodAndSummary(int userId, boolean forEndIsEfficiently, double punishForDamage, double punishForDelay, int carId){

        em.getTransaction().begin();

        List<CarRental> periodTimeActual = em.createQuery("select r from CarRental r "
                        + "join fetch r.user "
                        + "where r.user.id =:id", CarRental.class)
                .setParameter("id", userId)
                .getResultList();

        // ustawiamy jaki jest stan auta przy odbiorze i uwzględniamy wysokość kary
        for(CarRental eff: periodTimeActual){
            eff.getCar().setEfficient(forEndIsEfficiently);
            if(eff.getCar().isEfficient() == false){
                eff.setPunishment(eff.getPunishment().doubleValue() + punishForDamage);
            }
        }

        // sprawdzamy, czy auto jest oddane na czas, jeśli nie to ustanawiamy karę za opóźnienie
        for (CarRental t: periodTimeActual) {
            if (t.getEndPeriod().isBefore(LocalDateTime.now())) {
                t.setPunishment(t.getPunishment().doubleValue() + punishForDelay);
            }

            System.out.println("The total amount of: " + (t.getPunishment().doubleValue() + t.getCar().getPrice().doubleValue()));

            // zakończenie wypożyczenia
            Query query = em.createQuery("delete from CarRental where id=:id");
            query.setParameter("id", carId);
            int rowsDelete = query.executeUpdate();
            System.out.println("Deleted period of value Id: " + rowsDelete);
        }

        em.getTransaction().commit();
    }

}