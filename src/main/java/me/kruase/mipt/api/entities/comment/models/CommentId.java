package me.kruase.mipt.api.entities.comment.models;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.kruase.mipt.api.util.EntityId;
import me.kruase.mipt.api.util.LongEntityIdSerializer;


@JsonSerialize(using = LongEntityIdSerializer.class)
public final class CommentId extends EntityId<Long> {
    public CommentId(long value) {
        super(value);
    }
}
