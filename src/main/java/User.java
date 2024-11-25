import java.util.ArrayList;
public class User {
    String ID;
    String name;
    ArrayList<String> followers = new ArrayList<>();
    ArrayList<String> posts = new ArrayList<String>();

    public void print(){
        System.out.println("User ID is: " + ID);
        System.out.println("User name is: " + name);
        //System.out.println("Number of followers: " + followers.size());
        System.out.println("User's followers: " + followers);
    }

    @Override
    public String toString(){
        return ("user: " + this.name + " - Id: " + this.ID);
    }
}
