package me.kruase.mipt.api.util;


import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public abstract class EntityId<T> {
    private final @NotNull T value;

    public EntityId(@NotNull T value) {
        this.value = value;
    }

    public @NotNull T value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntityId<?> entityId = (EntityId<?>) o;
        return value.equals(entityId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
