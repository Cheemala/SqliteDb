package asqlitedb.cheemala.com.model;

public class User {

    public String id;
    public String userName;

    public User(String id, String userName){
        this.id = id;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
