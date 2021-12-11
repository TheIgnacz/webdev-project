package webdevproject.error;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String name) {super("User name not found :" + name);}
}
