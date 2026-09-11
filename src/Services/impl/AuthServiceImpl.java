package Services.impl;

import Domains.User;
import Repository.UserRepository;
import Services.AuthService;
import Session.SessionManager;

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    public AuthServiceImpl(
            UserRepository userRepository,
            SessionManager sessionManager
    ) {
        this.userRepository = userRepository;
        this.sessionManager = sessionManager;
    }

    @Override
    public User register(
            String name,
            String email,
            String phone,
            String password
    ) {

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already used");
        }

        User user = new User(name, email, phone, password);

        userRepository.save(user);

        return login(email, password);
    }

    @Override
    public User login(String email, String password) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password");
        }

        sessionManager.setCurrentUser(user);

        return user;
    }

    @Override
    public void logout() {

        sessionManager.clearSession();
    }

    @Override
    public User updateProfile(String newname, String newemail, String newphone) {
        User currentuser = sessionManager.getCurrentUser();
        if (currentuser == null) {
            throw new IllegalStateException("No user found with this data");

        }
        else {
            currentuser.setName(newname);
            currentuser.setPhone(newphone);
            currentuser.setEmail(newemail);
            userRepository.save(currentuser);
        }
        return currentuser;
    }

    @Override
    public User updatePassword(String newpassword) {
        User currentUser = sessionManager.getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("No user found with this data");

        }
        else {
            currentUser.setPassword(newpassword);
            userRepository.save(currentUser);
        }
        return null;
    }

}