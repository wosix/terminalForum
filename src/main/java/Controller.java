import dao.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.spi.SQLExceptionConverterFactory;
import sql.Comment;
import sql.Post;
import sql.User;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.Scanner;

public class Controller {

    Scanner scanner = new Scanner(System.in);
    final SessionFactory sf = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(Post.class)
            .addAnnotatedClass(Comment.class)
            .buildSessionFactory();
    HibernateUtil userDao = new HibernateUtil(sf.createEntityManager(), User.class);
    HibernateUtil postDao = new HibernateUtil(sf.createEntityManager(), Post.class);
    HibernateUtil commentDao = new HibernateUtil(sf.createEntityManager(), Comment.class);
    Boolean isOnline = false;

    public void welcome() {
        System.out.println("Welcome to my forum!");
    }

    public void run() {
        exampl();
        welcome();
        while (true) {
            System.out.println("Choose action:\n1: Sign in\n2: Sign up\n3: Exit");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    signIn();
                    break;
                case 2:
                    signUp();
                    break;
                case 4:
                    find();
                    break;
            }
        }
    }

    public void loggedIn(User userLogged) {
        while (isOnline) {
            System.out.println("Welcome " + userLogged.getNickname() + "\n Choose action:\n1: ");
        }
    }

    public void signIn() {
        System.out.print("Login: ");
        scanner.nextLine();
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            User userLog = (User) userDao.findByLogin(login);
            if (userLog.getPassword().equals(password)) {
                System.out.println("Successful Login");
                isOnline = true;
                loggedIn(userLog);
            } else if (!userLog.getPassword().equals(password)) {
                System.out.println("Incorrect Password");
            }
        } catch (NoResultException exp) {
            System.out.println("User not found");
        }
    }

    public void signUp() {
        System.out.print("Login: ");
        scanner.nextLine();
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        User userCreated = new User(city, login, password, phone);
        userDao.create(userCreated);

        System.out.println(SqlExceptionHelper.STANDARD_WARNING_HANDLER.toString());
    }

    public void find() {
        System.out.print("which user?: ");
        scanner.nextLine();
        String userToFind = scanner.nextLine();
        System.out.println(userDao.findByLogin(userToFind).toString());
    }

    public void exampl() {
        User user1 = new User("City", "user", "1234", "123456789");
        User user2 = new User("Poznań", "maciek", "1945", "531785012");
        User user3 = new User("Warszawa", "janek123", "9284", "875473019");
        User user4 = new User("Wrocław", "kox999", "7643", "285476930");
        User user5 = new User("Zielona Góra", "ania321", "6546", "847275374");
        User user6 = new User("Zielona Góra", "wosix", "7535", "847275876");

        Post post1 = new Post("hej, to moj pierwszy post", user5);
        Post post2 = new Post("hej, jak tam mija wam dzien?", user3);
        Post post3 = new Post("ale mam dzis zly dzien :(", user1);

        Comment comment1 = new Comment("a to pierwzzy komentarz!!!", user4, post1);
        Comment comment2 = new Comment("tak sobie :/", user1, post2);
        Comment comment3 = new Comment("srednio", user4, post2);
        Comment comment4 = new Comment("jest super", user2, post2);
        Comment comment5 = new Comment("jest i drugi ;)", user5, post1);

        userDao.create(user1);
        userDao.create(user2);
        userDao.create(user3);
        userDao.create(user4);
        userDao.create(user5);
        userDao.create(user6);

        postDao.create(post1);
        postDao.create(post2);
        postDao.create(post3);

        commentDao.create(comment1);
        commentDao.create(comment2);
        commentDao.create(comment3);
        commentDao.create(comment4);
        commentDao.create(comment5);
    }
}
