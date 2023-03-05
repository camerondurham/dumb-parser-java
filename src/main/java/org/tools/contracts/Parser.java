package org.tools.contracts;

import org.tools.models.Tokens;

public interface Parser<U, P> {
    Tokens<U, P> parse(U unparsedTokens);
}
