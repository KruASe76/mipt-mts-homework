package me.kruase.mipt.messages.enrichment;


import me.kruase.mipt.messages.dto.Message;
import me.kruase.mipt.messages.user.User;
import me.kruase.mipt.messages.user.UserRepository;
import me.kruase.mipt.messages.user.UserRepositoryMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class EnrichmentServiceTest {
    static final String MSISDN = "88009999999";
    static final User USER = new User("Blind", "Pew");
    static final String KEY = "attachment";
    static final String VALUE = "The black mark";

    static EnrichmentService service;

    @BeforeAll
    static void beforeAll() {
        UserRepository mockRepository = new UserRepositoryMock();
        mockRepository.updateUserByMsisdn(MSISDN, USER);

        MsisdnEnricher enricher = new MsisdnEnricher(mockRepository);

        service = new EnrichmentService();
        service.addEnricher(enricher);
    }

    @Test
    void testEnrichMessage() {
        Message message = new Message(
                Map.of(KEY, VALUE, "msisdn", MSISDN),
                Message.EnrichmentType.MSISDN);

        Message enrichedMessage = service.enrich(message);

        assertEquals(VALUE, enrichedMessage.getContent().get(KEY));
        assertEquals(USER.firstName(), enrichedMessage.getContent().get("firstName"));
        assertEquals(USER.lastName(), enrichedMessage.getContent().get("lastName"));
    }

    @Test
    void testEnrichContent() {
        Map<String, String> content = Map.of(KEY, VALUE, "msisdn", MSISDN);

        Message enrichedMessage = service.enrich(content, Message.EnrichmentType.MSISDN);

        assertEquals(VALUE, enrichedMessage.getContent().get(KEY));
        assertEquals(USER.firstName(), enrichedMessage.getContent().get("firstName"));
        assertEquals(USER.lastName(), enrichedMessage.getContent().get("lastName"));
    }

    @Test
    void testEnrichThrows() {
        EnrichmentService emptyService = new EnrichmentService();

        Message message = new Message(
                Map.of(KEY, VALUE, "msisdn", MSISDN),
                Message.EnrichmentType.MSISDN);

        assertThrows(IllegalStateException.class, () -> emptyService.enrich(message));
    }
}
