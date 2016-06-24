package in.karanpurohit.justacalc.Functions;

/**
 * Created by Karan Purohit on 23/6/16.
 */
public class Function {
    private String name;
    private String defenation;
    private String description;
    private String userId;
    private boolean publish;

    public Function (String name, String defenation, String userId, String description, boolean publish) {
        this.name = name;
        this.defenation = defenation;
        this.userId = userId;
        this.description = description;
        this.publish = publish;
    }

    public String getName () {

        return name;
    }

    public boolean isPublish () {

        return publish;
    }

    public String getUserId () {

        return userId;
    }

    public String getDescription () {

        return description;
    }

    public String getDefenation () {

        return defenation;
    }

    public void setDefenation (String defenation) {

        this.defenation = defenation;
    }

    public void setName (String name) {

        this.name = name;
    }

    public void setPublish (boolean publish) {

        this.publish = publish;
    }
}
