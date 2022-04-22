package CarRental;

import ConnectionTest.HibernateUtil;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class RentalApp {

    public static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    public static EntityManager em = sessionFactory.createEntityManager();


    public static void main(String[] args) {


        // odniesienie do metod dane początkowe, dodawanie, usuwanie, edycja, rozliczanie wypożyczeń, itd

        RentalRepository rentalRepository = new RentalRepository();

        RentalRepository.loadInitialData();

        RentalRepository.addUser("Jan", "Kowal");
        RentalRepository.addCar("Lamborghini", "Aventador", "Greey", "PO KING1", 500, true);
        RentalRepository.addCar("Porsche", "Panamera S", "Silver", "PZ 02546", 450, true);
        RentalRepository.addCar("Dodge", "Challenger", "Red", "PZ DODGE", 550, true);
//        RentalRepository.deleteCar(6);
//        RentalRepository.deleteUserFromId(5);
        RentalRepository.increaseEndPeriod(1, 0, 20);
        RentalRepository.finishPeriodAndSummary(2,1000, 400, 1, false);

        RentalRepository.addPeriod(33, 2,0, 5, 8);
        RentalRepository.addPeriod(44, 3, 0, 6, 7);

        // this write your CarRenatal code ;)

//        RentalRepository.finishPeriodAndSummary(6, 0, 0, 4, true);



//        HibernateUtil.shutdown();     // przeniesione na koniec z uwagi na scanner


        Scanner scanner = new Scanner(System.in);
        boolean isWorking = true;

        while (isWorking) {
            System.out.printf("Enter an option:\n" +
                    "End App - %30s\n" +
                    "Add new User - %25s\n" +
                    "Delete User - %26s\n" +
                    "Delete User from Id - %18s\n" +
                    "Add new Car - %26s\n" +
                    "Delete Car - %27s\n" +
                    "Add new Period - %23s\n" +
                    "Increase time rental - %17s\n" +
                    "Finish Period and summary - %10s\n",
                    "exit", "addUser", "delUser", "delUserFromId",
                    "addCar", "delCar", "addPeriod", "incEndPeriod", "finishPeriod");

            String text = scanner.nextLine();

            switch (text) {
                case "exit":
                    isWorking = false;
                    break;
                case "addUser":
                    System.out.println("Enter Name and Surname");
                    rentalRepository.addUser(scanner.next(),(scanner.next()));
                    break;
                case "delUser":
                    System.out.println("Enter Name and Surname");
                    rentalRepository.deleteUser(scanner.next(),(scanner.next()));
                    break;
                case "delUserFromId":
                    System.out.println("Enter Id (value)");
                    rentalRepository.deleteUserFromId(scanner.nextInt());
                    break;
                case "addCar":
                    System.out.println("Enter for Car: Brand, Model, color, regNr, price, efficientValue(boolean)");
                    rentalRepository.addCar(scanner.next(),scanner.next(),scanner.next(),(scanner.next() + " " + scanner.next()),
                            scanner.nextDouble(), scanner.hasNextBoolean());
                    break;
                case "delCar":
                    System.out.println("Enter: Id (value)");
                    rentalRepository.deleteCar(scanner.nextInt());
                case "addPeriod":
                    System.out.println("Enter: punishment, valueHours, valueMinutes, valueUserId, valueCarId");
                    rentalRepository.addPeriod(scanner.nextDouble(),scanner.nextInt(),scanner.nextInt(),scanner.nextInt(),scanner.nextInt());
                case "incEndPeriod":
                    System.out.println("Enter: selectUserId, addHour, addMinut");
                    rentalRepository.increaseEndPeriod(scanner.nextInt(),scanner.nextInt(),scanner.nextInt());
                case "finishPeriod":
                    System.out.println("Enter: userId, punishForDamage, punishForDelay, periodId, forEndIsEfficiently(boolean)");
                    rentalRepository.finishPeriodAndSummary(scanner.nextInt(),scanner.nextDouble(),scanner.nextDouble(),scanner.nextInt(),scanner.hasNextBoolean());

                default:
                    System.out.println("Options not recognized");
            }
        }


        HibernateUtil.shutdown();
    }

}
