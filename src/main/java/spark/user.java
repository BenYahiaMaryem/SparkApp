package spark;
import java.io.Serializable;

public final class user implements Serializable {
    private String UserName;
    private Integer age;

    public String getUserName() {
        return UserName;
    }

    public void setName(String name) {
        this.UserName = name;
    }

    
}