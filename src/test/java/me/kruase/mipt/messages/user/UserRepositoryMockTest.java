package me.kruase.mipt.messages.user;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class UserRepositoryMockTest {
    static final String FIRST_MSISDN = "88005553535";
    static final String SECOND_MSISDN = "88001234567";
    static final String THIRD_MSISDN = "88009999999";
    static final User FIRST_USER = new User("Doctor", "Livesey");
    static final User SECOND_USER = new User("Squire", "Trelawney");
    static final User THIRD_USER = new User("Blind", "Pew");

    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryMock();
        userRepository.updateUserByMsisdn(FIRST_MSISDN, FIRST_USER);
        userRepository.updateUserByMsisdn(SECOND_MSISDN, SECOND_USER);
    }

    @Test
    void testFindByMsisdn() {
        assertEquals(FIRST_USER, userRepository.findByMsisdn(FIRST_MSISDN));
        assertEquals(SECOND_USER, userRepository.findByMsisdn(SECOND_MSISDN));
        assertNull(userRepository.findByMsisdn(THIRD_MSISDN));
    }

    @Test
    void testUpdateUserByMsisdn() {
        assertNull(userRepository.findByMsisdn(THIRD_MSISDN));

        userRepository.updateUserByMsisdn(THIRD_MSISDN, THIRD_USER);

        assertEquals(THIRD_USER, userRepository.findByMsisdn(THIRD_MSISDN));
    }
}
