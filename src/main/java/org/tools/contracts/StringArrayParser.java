package org.tools.contracts;

import org.tools.models.Tokens;

import java.util.List;
import java.util.Optional;

public interface StringArrayParser<P> extends Parser<List<String>, P> {
    Tokens<List<String>, P> parse(List<String> tokens);
}
