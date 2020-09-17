import dao.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.spi.SQLExceptionConverterFactory;
import sql.Comment;
import sql.Post;
import sql.User;

import javax.persistence.NoResultException;
import java.awt.*;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
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
    Boolean running = true;
    Boolean isOnline = false;
    User userLogged = null;

    public void welcome() {
        System.out.println("\nWelcome to my forum!");
    }

    public void run() {
        exampl();
        welcome();
        while (running) {
            System.out.println("\nChoose action:\n1: Sign in\n2: Sign up\n3: Exit");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    signIn();
                    break;
                case 2:
                    signUp();
                    break;
                case 3:
                    running = false;
                    break;
            }
        }
    }

    public void loggedIn(User userLogged) {
        while (isOnline) {
            System.out.println("---------------\n   Welcome " + userLogged.getNickname() + "\n\n   Choose action:\n1: New post\n2: See all posts\n3: See all users");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    postNewPost(userLogged);
                    break;
                case 2:
                    postDao.printAll();
                    pickPost();
                    break;
                case 3:
                    userDao.printAll();
                    break;
                case 6:
                    isOnline = false;
                    userLogged = null;
                    break;

            }
        }
    }

    public void signIn() {
        System.out.print("Login: ");
        scanner.nextLine();
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            userLogged = (User) userDao.findByLogin(login);
            if (userLogged.getPassword().equals(password)) {
                System.out.println("Successful Login");
                isOnline = true;
                loggedIn(userLogged);
            } else if (!userLogged.getPassword().equals(password)) {
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
    }


    public void postNewPost(User userPosting) {
        scanner.nextLine();
        String postContent = scanner.nextLine();

        Post postCreated = new Post(postContent, userPosting);

        postDao.create(postCreated);
    }

    public void pickPost() {
        try {
            System.out.println("Type id of post you want to interact: ");
            Long pickedPost = scanner.nextLong();
            Post post = (Post) postDao.findPostById(pickedPost);
            pickedPost(post, pickedPost);
        } catch (InputMismatchException exp) {
            System.out.println("Invalid input");
        }
    }

    public void pickedPost(Post pickedPost, Long postId) {
        System.out.println("\nPost picked: ");
        System.out.println(pickedPost);
        commentDao.printAllComments(postId);
        commentOrRun(pickedPost);
    }

    public void commentOrRun(Post pickedPost) {
        System.out.println("Do you want to ADD comment or back");
        scanner.nextLine();
        String action = scanner.nextLine().toLowerCase();
        if (action.startsWith("a") | action.startsWith("ad") | action.equals("add")) {
            addComment(pickedPost);
        }
    }

    public void addComment(Post pickedPost) {
        String content = scanner.nextLine();
        Comment commentCreated = new Comment(content, userLogged, pickedPost);
        commentDao.create(commentCreated);
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
