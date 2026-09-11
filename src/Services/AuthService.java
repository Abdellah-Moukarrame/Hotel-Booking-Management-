package Services;

import Domains.User;

public interface AuthService{
    User login(String email , String password);
    User register(String name , String email , String phone , String password);
    void logout();
    User updateProfile(String name , String email , String phone);
    User updatePassword(String password);
}