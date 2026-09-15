package Domains;

import java.util.UUID;

public class User{
    protected UUID idU ;
    protected String name ;
    protected String email ;
    protected String password ;
    protected String phone ;

    public User(String name, String email, String phone, String password) {
        this.idU = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public UUID getIdU() {
        return this.idU;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdU(UUID idU) {
        this.idU = idU;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
