package dev.shashiirk.shopmate.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shashiirk.shopmate.exception.OperationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * Utility class for marshalling and unmarshalling JSON data.
 */
@Slf4j
@Component
public class JSONUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Marshal the given object to its JSON representation.
     *
     * @param object The object to marshal
     * @param <T>    Type of the object
     * @return JSON representation of the object
     */
    public static <T> String marshal(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error occurred during marshalling object: {}", object, e);
            throw new OperationFailedException("Failed to marshal");
        }
    }

    /**
     * Unmarshal the given JSON payload into an object of the specified type.
     *
     * @param payload The JSON payload to unmarshal
     * @param target  The target class for unmarshalling
     * @param <T>     Type of the target class
     * @return Object of the specified type representing the JSON payload
     */
    public static <T> T unmarshal(String payload, Class<T> target) {
        if (ObjectUtils.isEmpty(payload)) return null;
        try {
            return objectMapper.readValue(payload, target);
        } catch (JsonProcessingException e) {
            log.error("Error occurred during unmarshalling payload: {}", payload, e);
            throw new OperationFailedException("Failed to unmarshal");
        }
    }
}
