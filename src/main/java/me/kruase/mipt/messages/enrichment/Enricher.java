package me.kruase.mipt.messages.enrichment;


import me.kruase.mipt.messages.dto.Message;
import me.kruase.mipt.messages.user.UserRepository;

import java.util.Map;


/**
 * Abstract class for message enriching
 */
public abstract class Enricher {
    protected final UserRepository repository;

    Enricher(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Enriches the specified content
     *
     * @param content content to be enriched
     * @return enriched content (same {@link Map} object)
     */
    abstract Map<String, String> enrich(Map<String, String> content)
            throws IllegalArgumentException;

    /**
     * Returns the type of enrichment
     *
     * @return enrichment type
     */
    abstract Message.EnrichmentType type();
}
