package org.mworld.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CustomTimestampSerializer extends JsonSerializer<Timestamp> {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(Timestamp value, JsonGenerator generator, SerializerProvider serializerProvider)
        throws IOException, JsonProcessingException {

        ZonedDateTime zonedDateTime = value.toInstant().atZone(ZoneId.systemDefault());
        generator.writeString(dateTimeFormatter.format(zonedDateTime));
    }

}
