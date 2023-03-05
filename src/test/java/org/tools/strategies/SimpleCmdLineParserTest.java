package org.tools.strategies;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SimpleCmdLineParserTest {

    @Parameterized.Parameter
    public List<String> args;
    @Parameterized.Parameter(1)
    public Optional<Map<String, String>> expected;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {List.of("flag1=value1", "flag2=value2", "flag3=value3"),
                        Optional.empty()},
                {List.of("flag1=value1", "flag2=value2", "flag3=value3", "flag4"),
                        Optional.empty()},
                {List.of("--flag1=value1", "--flag2=value2", "--flag3=value3", "--flag4", "--flag5=value5"),
                        Optional.of(Map.of("flag1", "value1", "flag2", "value2", "flag3", "value3", "flag5", "value5"))},
        });
    }

    @Test
    public void testParse() {
        var parser = new SimpleCmdLineParser();
        var result = parser.parse(args);
        assertEquals(expected, result.parsedTokens());
    }

}