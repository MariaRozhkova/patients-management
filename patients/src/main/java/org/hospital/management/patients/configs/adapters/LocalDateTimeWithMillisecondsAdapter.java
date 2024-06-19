package org.hospital.management.patients.configs.adapters;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Gson adapter to serialize and deserialize {@link LocalDateTime} with milliseconds.
 */
public class LocalDateTimeWithMillisecondsAdapter
    implements JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {

    private static final String DATE_TIME_WITH_MILLISECONDS_PATTERN
        = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";

    @Override
    public JsonElement serialize(
        LocalDateTime localDateTime,
        Type typeOfSrc,
        JsonSerializationContext context
    ) {
        return new JsonPrimitive(
            localDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_WITH_MILLISECONDS_PATTERN))
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
            DateTimeFormatter.ofPattern(DATE_TIME_WITH_MILLISECONDS_PATTERN)
        );
    }
}
