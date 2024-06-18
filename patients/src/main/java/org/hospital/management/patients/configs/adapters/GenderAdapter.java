package org.hospital.management.patients.configs.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.hospital.management.patients.enums.Gender;
import java.lang.reflect.Type;
import org.apache.commons.lang3.EnumUtils;

public class GenderAdapter implements JsonDeserializer<Gender> {

    @Override
    public Gender deserialize(
        JsonElement json,
        Type typeOfT,
        JsonDeserializationContext context
    ) throws JsonParseException {
        return EnumUtils.getEnumIgnoreCase(Gender.class, json.getAsString());
    }
}
