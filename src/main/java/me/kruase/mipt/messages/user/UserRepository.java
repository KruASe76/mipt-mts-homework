package me.kruase.mipt.messages.user;


public interface UserRepository {
    User findByMsisdn(String msisdn);

    void updateUserByMsisdn(String msisdn, User user);
}
