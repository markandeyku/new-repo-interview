package solid;


public class SingleResponsibility {
}

/**
 * Here, User class does:
 * User data representation
 * Persistence logic
 * Email logic
 * That's 3 responsibilities, violating SRP.
 */

 class User {
    public String name;
    public String email;

    public void saveToDB() {
        // save user to database
    }

    public void sendWelcomeEmail() {
        // send email logic
    }


    /**
     * Now each class has one reason to change:
     * User: Only stores data
     * UserRepository: Only handles DB
     * EmailService: Only handles email
     */
    public class User1 {
        public String name;
        public String email;
    }

    public class UserRepository {
        public void save(User1 user) {
            // Save user to DB
        }
    }

    public class EmailService {
        public void sendWelcomeEmail(User1 user) {
            // Email logic
        }
    }


    /**
     * 📌 Benefits of SRP:
     * Easier to maintain: Change in one area doesn’t affect others.
     * Better testability: Test each responsibility in isolation.
     * Improved readability and reusability.
     * Fewer bugs from unintended side effects.
     */


}
