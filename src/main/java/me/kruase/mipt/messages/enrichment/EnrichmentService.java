package me.kruase.mipt.messages.enrichment;


import me.kruase.mipt.messages.dto.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * High-level service for message content enrichment
 */
public class EnrichmentService {
    private final ConcurrentHashMap<Message.EnrichmentType, Enricher> enrichers =
            new ConcurrentHashMap<>();

    /**
     * Adds new enrichment method to the service
     *
     * @param enricher the new enrichment method
     */
    public void addEnricher(Enricher enricher) {
        enrichers.put(enricher.type(), enricher);
    }

    /**
     * Enriches the specified message
     *
     * @param message message to be enriched
     * @return enriched message (same object)
     */
    public Message enrich(Message message) throws IllegalArgumentException {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        Enricher enricher = enrichers.get(message.enrichmentType());

        if (enricher == null) {
            throw new IllegalStateException(
                    "No enricher found for type: " + message.enrichmentType());
        }

        message.setContent(enricher.enrich(message.getContent()));

        return message;
    }

    /**
     * Enriches the specified content via the specified enrichment method
     *
     * @param content content to be enriched
     * @param enrichmentType enrichment method
     * @return message object with enriched content
     */
    public Message enrich(Map<String, String> content, Message.EnrichmentType enrichmentType)
            throws IllegalArgumentException {
        Message message = new Message(content, enrichmentType);
        return enrich(message);
    }
}
