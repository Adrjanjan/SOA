package pl.edu.agh.soa.dao;

import pl.edu.agh.soa.model.User;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.Map;

@Stateful
public class InMemoryUserRepository implements UserRepository {

    private static final Map<String, User>  users = new HashMap<>();

    @Override
    public User getUser(String login) {
        return users.get(login);
    }

    @Override
    public void addUser(User user) {
        users.put(user.getLogin(), user);
    }
}
