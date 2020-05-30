package pl.edu.agh.soa.dao;

import pl.edu.agh.soa.model.User;

public interface UserRepository {

    User getUser(String login);

    void addUser(User user);
}
