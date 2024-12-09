package me.kruase.mipt.api.util;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;


public final class LongEntityIdSerializer extends StdSerializer<EntityId<Long>> {
    public LongEntityIdSerializer() {
        super((Class<EntityId<Long>>) null);
    }

    @Override
    public void serialize(EntityId<Long> value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        gen.writeNumber(value.value());
    }
}
