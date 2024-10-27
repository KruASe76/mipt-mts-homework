package me.kruase.mipt.messages.dto;


import java.util.Map;
import java.util.Objects;


/**
 * Message DTO with enrichment type
 */
public class Message {
    private Map<String, String> content;
    private final EnrichmentType enrichmentType;

    public Message(Map<String, String> content, EnrichmentType enrichmentType) {
        this.content = content;
        this.enrichmentType = enrichmentType;
    }

    public Map<String, String> getContent() {
        return content;
    }

    public void setContent(Map<String, String> content) {
        this.content = content;
    }

    public EnrichmentType enrichmentType() {
        return enrichmentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(content, message.content) && enrichmentType == message.enrichmentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, enrichmentType);
    }

    public enum EnrichmentType {
        MSISDN;
    }
}
