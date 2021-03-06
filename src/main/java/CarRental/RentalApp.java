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

        rentalRepository.loadInitialData();

        rentalRepository.addUser("Jan", "Kowal");
        rentalRepository.addCar("Lamborghini", "Aventador", "Greey", "PO KING1", 500, true);
        rentalRepository.addCar("Porsche", "Panamera S", "Silver", "PZ 02546", 450, true);
        rentalRepository.addCar("Dodge", "Challenger", "Red", "PZ DODGE", 550, true);
//        rentalRepository.deleteCar(6);
//        rentalRepository.deleteUserFromId(5);
        rentalRepository.increaseEndPeriod(1, 0, 20);
//        rentalRepository.finishPeriodAndSummary(1, false, 1000, 400);

        rentalRepository.addPeriod(33, 2,0, 5, 8);
        rentalRepository.addPeriod(44, 3, 0, 6, 7);


        // this write your CarRenatal code ;)

//        rentalRepository.finishPeriodAndSummary(4, false, 5000, 0);
//        rentalRepository.finishPeriodAndSummary(3, false, 10000, 0);


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
//                case "delUser":
//                    System.out.println("Enter Name and Surname");
//                    rentalRepository.deleteUser(scanner.next(),(scanner.next()));
//                    break;
                case "delUserFromId":
                    System.out.println("Enter Id (value)");
                    rentalRepository.deleteUserFromId(scanner.nextInt());
                    break;
                case "addCar":
                    System.out.println("Enter for Car: Brand, Model, color, regNr, price, efficientValue(boolean)");
                    rentalRepository.addCar(scanner.next(),scanner.next(),scanner.next(),(scanner.next() + " " + scanner.next()),
                            scanner.nextDouble(), scanner.nextBoolean());
                    break;
                case "delCar":
                    System.out.println("Enter: Id (value)");
                    rentalRepository.deleteCar(scanner.nextInt());
                    break;
                case "addPeriod":
                    System.out.println("Enter: punishment, valueHours, valueMinutes, valueUserId, valueCarId");
                    rentalRepository.addPeriod(scanner.nextDouble(),scanner.nextInt(), scanner.nextInt(),scanner.nextInt(),scanner.nextInt());
                    break;
                case "incEndPeriod":
                    System.out.println("Enter: selectUserId, addHour, addMinut");
                    rentalRepository.increaseEndPeriod(scanner.nextInt(),scanner.nextInt(),scanner.nextInt());
                    break;
                case "finishPeriod":
                    System.out.println("Enter: periodId, forEndIsEfficiently(boolean), punishForDamage, punishForDelay");
                    rentalRepository.finishPeriodAndSummary(scanner.nextInt(),scanner.nextBoolean(),scanner.nextDouble(),scanner.nextDouble());
                    break;

                default:
                    System.out.println("Options not recognized");
            }
        }


        HibernateUtil.shutdown();
    }

}