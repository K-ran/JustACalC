package in.karanpurohit.justacalc.User;

/**
 * Created by ankita on 23/6/16.
 */
public class User {
    private String name;
    private String email;
    private String accessToken;
    private String id;

    public User (String id,String name, String email, String accessToken) {

        this.name = name;
        this.email = email;
        this.accessToken = accessToken;
        this.id = id;
    }
}
