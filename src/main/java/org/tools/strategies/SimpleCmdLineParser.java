package org.tools.strategies;

import lombok.NonNull;
import org.tools.contracts.StringArrayParser;
import org.tools.models.Tokens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleCmdLineParser implements StringArrayParser<Map<String, String>> {
    private static final Set<String> FLAG_SPECIFIERS = Set.of("-", "--");
    private static final Set<String> FLAG_SEPARATORS = Set.of("=");

    @Override
    public Tokens<List<String>, Map<String, String>> parse(@NonNull List<String> tokens) {
        Map<String, String> parsed = tokens.stream()
                .map(this::parseFlag)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new Tokens<>(Optional.empty(), parsed.isEmpty() ? Optional.empty() : Optional.of(parsed));
    }

    private Optional<Map.Entry<String, String>> parseFlag(String token) {
        // expect --flag=value or -flag=value
        var startsWithSpecifier = FLAG_SPECIFIERS.stream().anyMatch(token::startsWith);
        var hasSeparator = FLAG_SEPARATORS.stream().anyMatch(token::contains);
        if (startsWithSpecifier && hasSeparator) {
            var keyValArray = token.split("=");
            String key = null;
            // TODO: clean up this messy logic
            if (keyValArray[0].startsWith("--")) {
                key = keyValArray[0].substring(2);
            } else {
                key = keyValArray[0].substring(1);
            }
            return Optional.of(new HashMap.SimpleEntry<>(key, keyValArray.length > 1 ? keyValArray[1] : null));
        }
        return Optional.empty();
    }
}
