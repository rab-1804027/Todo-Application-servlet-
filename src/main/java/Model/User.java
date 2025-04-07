package Model;

public class User {
    private int id;
    private String name;
    private String email;
    private String country;

    public User(String country, String name, String email) {
        this.country = country;
        this.name = name;
        this.email = email;
    }

    public User(int id, String name, String country, String email) {
        this.name = name;
        this.country = country;
        this.email = email;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
