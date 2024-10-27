package me.kruase.mipt.messages.enrichment;


import me.kruase.mipt.messages.dto.Message;
import me.kruase.mipt.messages.user.User;
import me.kruase.mipt.messages.user.UserRepository;

import java.util.HashMap;
import java.util.Map;


/**
 * Enricher implementation that uses MSISDN
 */
public class MsisdnEnricher extends Enricher {
    MsisdnEnricher(UserRepository repository) {
        super(repository);
    }

    @Override
    public Map<String, String> enrich(Map<String, String> content) throws IllegalArgumentException {
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }

        HashMap<String, String> newContent = new HashMap<>(content);

        String msisdn = newContent.get("msisdn");

        if (msisdn == null) {
            return newContent;
        }

        User user = repository.findByMsisdn(msisdn);

        if (user == null) {
            return newContent;
        }

        newContent.put("firstName", user.firstName());
        newContent.put("lastName", user.lastName());

        return newContent;
    }

    @Override
    public Message.EnrichmentType type() {
        return Message.EnrichmentType.MSISDN;
    }
}
