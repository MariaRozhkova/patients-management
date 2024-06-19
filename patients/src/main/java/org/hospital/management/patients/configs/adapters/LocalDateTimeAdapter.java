package org.hospital.management.patients.configs.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Gson adapter to serialize and deserialize {@link LocalDateTime}.
 */
public class LocalDateTimeAdapter
    implements JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    @Override
    public JsonElement serialize(
        LocalDateTime localDateTime,
        Type typeOfSrc,
        JsonSerializationContext context
    ) {
        return new JsonPrimitive(
            localDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN))
        );
    }

    @Override
    public LocalDateTime deserialize(
        JsonElement json,
        Type typeOfT,
        JsonDeserializationContext context
    ) {
        return LocalDateTime.parse(
            json.getAsString(),
            DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
        );
    }
}
