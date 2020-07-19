package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchError;
import io.github.orangain.jsonmatch.JsonPath;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class UUIDMarkerPatternNode extends ValuePatternNode {
    private static UUIDMarkerPatternNode instance;

    public UUIDMarkerPatternNode(@NotNull String expected) {
        super(expected);
    }

    public static UUIDMarkerPatternNode getInstance() {
        if (instance == null) {
            instance = new UUIDMarkerPatternNode("#uuid");
        }
        return instance;
    }

    @NotNull
    @Override
    public Optional<JsonMatchError> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        if (!actualNode.isTextual()) {
            return Optional.of(error(path, actualNode, "not a string"));
        }

        try {
            UUID.fromString(actualNode.textValue());
        } catch (IllegalArgumentException ex) {
            return Optional.of(error(path, actualNode, "not a valid #uuid"));
        }

        return Optional.empty();
    }
}