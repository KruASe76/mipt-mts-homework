package me.kruase.mipt.messages.enrichment;


import me.kruase.mipt.messages.dto.Message;
import me.kruase.mipt.messages.user.User;
import me.kruase.mipt.messages.user.UserRepository;
import me.kruase.mipt.messages.user.UserRepositoryMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ConcurrentTest {
    static final String FIRST_MSISDN = "88005553535";
    static final String SECOND_MSISDN = "88001234567";
    static final String THIRD_MSISDN = "88009999999";
    static final User FIRST_USER = new User("Doctor", "Livesey");
    static final User SECOND_USER = new User("Squire", "Trelawney");
    static final User THIRD_USER = new User("Blind", "Pew");

    @Test
    void testConcurrentEnrichment() throws InterruptedException, ExecutionException {
        UserRepository mockRepository = new UserRepositoryMock();
        mockRepository.updateUserByMsisdn(FIRST_MSISDN, FIRST_USER);
        mockRepository.updateUserByMsisdn(SECOND_MSISDN, SECOND_USER);
        mockRepository.updateUserByMsisdn(THIRD_MSISDN, THIRD_USER);

        EnrichmentService service = new EnrichmentService();
        service.addEnricher(new MsisdnEnricher(mockRepository));

        CopyOnWriteArrayList<Map<String, String>> given =
                new CopyOnWriteArrayList<>(List.of(
                        Map.of("key1", "value1", "msisdn", FIRST_MSISDN),
                        Map.of("key2", "value2", "msisdn", SECOND_MSISDN),
                        Map.of("key3", "value3", "msisdn", THIRD_MSISDN),
                        Map.of("key4", "value4"),
                        Map.of("key5", "value5")
                ));

        CopyOnWriteArrayList<Message> expected = new CopyOnWriteArrayList<>(List.of(
                new Message(
                        Map.of("key1", "value1",
                                "msisdn", FIRST_MSISDN,
                                "firstName", FIRST_USER.firstName(),
                                "lastName", FIRST_USER.lastName()), Message.EnrichmentType.MSISDN),
                new Message(
                        Map.of("key2", "value2",
                                "msisdn", SECOND_MSISDN,
                                "firstName", SECOND_USER.firstName(),
                                "lastName", SECOND_USER.lastName()), Message.EnrichmentType.MSISDN),
                new Message(
                        Map.of("key3", "value3",
                                "msisdn", THIRD_MSISDN,
                                "firstName", THIRD_USER.firstName(),
                                "lastName", THIRD_USER.lastName()), Message.EnrichmentType.MSISDN),
                new Message(Map.of("key4", "value4"), Message.EnrichmentType.MSISDN),
                new Message(Map.of("key5", "value5"), Message.EnrichmentType.MSISDN)
        ));

        CopyOnWriteArrayList<Message> actual = new CopyOnWriteArrayList<>();
        for (int i = 0; i < given.size(); i++) {
            actual.add(null);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        ArrayList<Future<Message>> futures = new ArrayList<>();
        for (int i = 0; i < given.size(); i++) {
            int finalI = i;

            futures.add(executorService.submit(
                    () -> actual.set(
                            finalI,
                            service.enrich(given.get(finalI), Message.EnrichmentType.MSISDN))
            ));
        }

        for (var future : futures) {
            future.get();
        }

        assertEquals(expected, actual);
    }
}
