package me.kruase.mipt.messages.user;


import java.util.concurrent.ConcurrentHashMap;


/**
 * {@link UserRepository} implementation utilizing {@link ConcurrentHashMap}
 */
public class UserRepositoryMock implements UserRepository {
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    @Override
    public User findByMsisdn(String msisdn) {
        return users.get(msisdn);
    }

    @Override
    public void updateUserByMsisdn(String msisdn, User user) {
        users.put(msisdn, user);
    }
}
