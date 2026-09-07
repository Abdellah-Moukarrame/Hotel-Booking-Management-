package Domains;

public class User{
    protected int idU = 0 ;
    protected String name ;
    protected String email ;
    protected String password ;
    protected String phone ;

    User(String name , String email , String password , String phone){
        idU++;
        name = name ;
        email = email ;
        password = password ;
        phone = phone ;
    }

    public String getEmail() {
        return email;
    }

    public int getIdU() {
        return idU;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdU(int idU) {
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