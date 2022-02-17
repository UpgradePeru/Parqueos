package pe.upgrade.parqueo.model.api;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    public static User getUser(JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email");
        String role = jsonObject.getString("role");
        String token = jsonObject.getString("token");

        User user = new User(name, email, role, token);

        return user;
    }

    private String name;
    private String email;
    private String role;
    private String token;

    public User(String name, String email, String role, String token) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setUEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean equals(Object obj) {
        boolean result = false;

        if (obj != null && obj instanceof User) {
            User that = (User) obj;
            if (this.email.equalsIgnoreCase(that.email)) {
                result = true;
            }
        }
        return result;
    }

    public String toString() {
        return this.name + "(" + this.email + ")";
    }
}
