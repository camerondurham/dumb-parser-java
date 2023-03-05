package org.tools.models;

import java.util.Optional;

public record Tokens<U, P>(Optional<U> unparsedTokens, Optional<P> parsedTokens) {
}
