package br.com.felipezorzo.sonar.plsql.api;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class WhileStatementTests extends RuleTest {

    @Before
    public void init() {
        setRootRule(PlSqlGrammar.WHILE_STATEMENT);
    }

    @Test
    public void matchesWhileLoop() {
        assertThat(p).matches(""
                + "while true loop "
                + "null; "
                + "end loop;");
    }
    
    @Test
    public void matchesNestedWhileLoop() {
        assertThat(p).matches(""
                + "while true loop "
                + "while true loop "
                + "null; "
                + "end loop; "
                + "end loop;");
    }

}