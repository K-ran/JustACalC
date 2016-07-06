package in.karanpurohit.justacalc.Functions;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import in.karanpurohit.justacalc.CONSTANTS;

/**
 * Created by Karan Purohit on 23/6/16.
 */
public class Function implements Parcelable{

    private int id;
    private String name;
    private String defenation;

    private String description;

    private int userId;
    private int publish;

    private int votes;

    public static Function createFromJsonObject(JSONObject object){

        try {
            int id  = object.getInt(CONSTANTS.FUNCTION_ID);
            String name=object.getString(CONSTANTS.FUNCTION_NAME);
            String defenation=object.getString (CONSTANTS.FUNCTION_DEFINATION);
            String description=object.getString (CONSTANTS.FUNCTION_DESCRIPTION);
            int userId=object.getInt(CONSTANTS.FUNCTION_USERID);
            int publish=object.getInt (CONSTANTS.FUNCTION_PUBLIC);
            int votes = object.optInt(CONSTANTS.FUNCTION_VOTES,0);
            return new Function (id,name,defenation,userId,description,publish,votes);
        }
        catch (JSONException e) {
            e.printStackTrace ();
        }
        return null;
    }

    public Function (int id,String name, String defenation, int userId, String description, int publish,int votes) {
        this.name = name;
        this.defenation = defenation;
        this.userId = userId;
        this.description = description;
        this.publish = publish;
        this.id = id;
        this.votes = votes;
    }


    public Function (Parcel in) {
        String[] data=new String[6];
        in.readStringArray (data);
        id=Integer.parseInt (data[0]);
        name = data[1];
        defenation=data[2];
        description=data[3];
        userId=Integer.parseInt (data[4]);
        publish=Integer.parseInt (data[5]);
    }


    public String getName () {

        return name;
    }

    public int getId(){
        return id;
    }

    public int isPublish () {

        return publish;
    }

    public int getVotes() {
        return votes;
    }

    public int getUserId () {

        return userId;
    }

    public int getPublish () {

        return publish;
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

    public void setDescription (String description) {

        this.description = description;
    }

    public void setName (String name) {

        this.name = name;
    }

    public void setPublish (int publish) {

        this.publish = publish;
    }

    public String getParameters(){
        String def = getDefenation ();
        int start=-1,end=-1;
        for(int i=0;i<def.length ();i++){
            if(def.charAt (i)=='(')
                start=i;
            if(def.charAt (i)==')') {
                end = i;
                break;
            }
        }
        return def.substring (start+1,end);
    }

    public String getFunctionBody(){
        String def = getDefenation ();
        int start=-1;
        for(int i=0;i<def.length ();i++){
            if(def.charAt (i)=='{') {
                start = i;
                break;
            }
        }
        return def.substring (start+1,def.length ()-1);
    }

    @Override
    public int describeContents () {

        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Function createFromParcel(Parcel in) {
            return new Function (in);
        }

        public Function[] newArray(int size) {
            return new Function[size];
        }
    };

    @Override
    public void writeToParcel (Parcel dest, int flags) {
                dest.writeStringArray (new String[]{
                      id+"",name,defenation,description,userId+"",publish+""
                });
    }

    @Override
    public boolean equals (Object o) {
        Function other = (Function)o;
        return (id==other.getId ());
    }
}
