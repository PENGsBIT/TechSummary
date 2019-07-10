package Optional;

import java.util.*;
import java.util.stream.Collectors;

public class useOptional {
    public static void main(String[] args) throws Exception {
        Optional<Integer> a;
    }

    public void getUserID(int id) {
        User user = new User("a", 3);
        user=  user.getUserById(id);
        //basic
        if (user != null) {
            String username = user.getUserName();
            System.out.println("Username is: " + username); // 使用 username
        }
        //use isPresent()
        Optional<User> userOptional = Optional.ofNullable(user.getUserById(id));
        if (userOptional.isPresent()) {
            String username = userOptional.get().getUserName();
            System.out.println("Username is: " + username); // 使用 username
        }
        //optimize
        userOptional = Optional.ofNullable(user.getUserById(id));
        userOptional.ifPresent(u -> System.out.println("Username is: " + u.getUserName()));

        //use .orElse()
        user = Optional
            .ofNullable(user.getUserById(id))
            .orElse(new User("Unknown",0 ));

        System.out.println("Username is: " + user.getUserName());

        //map
        Optional<String> username = Optional
            .ofNullable(user.getUserById(id))
            .map(element -> element.getUserName());

        System.out.println("Username is: " + username.orElse("Unknown"));

    }

}

