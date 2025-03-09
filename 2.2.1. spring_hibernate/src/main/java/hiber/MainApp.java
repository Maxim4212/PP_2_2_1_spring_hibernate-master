package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.cleanUsersTable();
        userService.cleanCarsTable();

        Car carUser1 = new Car("Жигули", 2);
        Car carUser2 = new Car("Toyota", 1);
        Car carUser3 = new Car("Moskvich", 4);
        Car carUser4 = new Car("BMW", 5);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru", carUser1));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru", carUser2));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru", carUser3));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru", carUser4));

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println();
        }

        String searchModel = "Toyota";
        int searchSeries = 1;
        User foundUser = userService.getUserByCar(searchModel, searchSeries);

        if (foundUser != null) {
            System.out.println("Найден пользователь по машине " + searchModel + " серии " + searchSeries + ":");
            System.out.println("Name: " + foundUser.getFirstName());
            System.out.println("Lastname: " + foundUser.getLastName());
            System.out.println("Email: " + foundUser.getEmail());
        } else {
            System.out.println("Пользователь с машиной " + searchModel + " серии " + searchSeries + " не найден.");
        }

        context.close();
    }
}
