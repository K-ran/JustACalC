package in.karanpurohit.justacalc.Functions;

import org.json.JSONException;
import org.json.JSONObject;

import in.karanpurohit.justacalc.CONSTANTS;

/**
 * Created by Karan Purohit on 23/6/16.
 */
public class Function {
    private int id;
    private String name;
    private String defenation;
    private String description;
    private int userId;
    private int publish;

    public static Function createFromJsonObject(JSONObject object){

        try {
            int id  = object.getInt(CONSTANTS.FUNCTION_ID);
            String name=object.getString (CONSTANTS.FUNCTION_NAME);
            String defenation=object.getString (CONSTANTS.FUNCTION_DEFINATION);
            String description=object.getString (CONSTANTS.FUNCTION_DESCRIPTION);
            int userId=object.getInt (CONSTANTS.FUNCTION_USERID);;
            int publish=object.getInt (CONSTANTS.FUNCTION_PUBLIC);
            return new Function (id,name,defenation,userId,description,publish);
        }
        catch (JSONException e) {
            e.printStackTrace ();
        }
        return null;
    }

    public Function (int id,String name, String defenation, int userId, String description, int publish) {
        this.name = name;
        this.defenation = defenation;
        this.userId = userId;
        this.description = description;
        this.publish = publish;
        this.id = id;
    }

    public String getName () {

        return name;
    }

    public int isPublish () {

        return publish;
    }

    public int getUserId () {

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

    public void setPublish (int publish) {

        this.publish = publish;
    }
}
