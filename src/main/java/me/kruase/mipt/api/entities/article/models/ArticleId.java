package me.kruase.mipt.api.entities.article.models;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.kruase.mipt.api.util.EntityId;
import me.kruase.mipt.api.util.LongEntityIdSerializer;


@JsonSerialize(using = LongEntityIdSerializer.class)
public final class ArticleId extends EntityId<Long> {
    public ArticleId(long value) {
        super(value);
    }
}
