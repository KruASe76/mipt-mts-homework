package me.kruase.mipt.messages.enrichment;


import me.kruase.mipt.messages.dto.Message;
import me.kruase.mipt.messages.user.User;
import me.kruase.mipt.messages.user.UserRepository;
import me.kruase.mipt.messages.user.UserRepositoryMock;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class MsisdnEnricherTest {
    static final String MSISDN = "88005553535";
    static final User USER = new User("Doctor", "Livesey");

    @Test
    void testEnrich() {
        UserRepository mockRepository = new UserRepositoryMock();
        mockRepository.updateUserByMsisdn(MSISDN, USER);

        MsisdnEnricher enricher = new MsisdnEnricher(mockRepository);

        Map<String, String> content = enricher.enrich(Map.of("msisdn", MSISDN));

        assertEquals(USER.firstName(), content.get("firstName"));
        assertEquals(USER.lastName(), content.get("lastName"));
    }

    @Test
    void testType() {
        assertEquals(Message.EnrichmentType.MSISDN, new MsisdnEnricher(null).type());
    }
}
