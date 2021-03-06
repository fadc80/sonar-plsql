/*
 * Sonar PL/SQL Plugin (Community)
 * Copyright (C) 2015-2016 Felipe Zorzo
 * mailto:felipebzorzo AT gmail DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.plugins.plsqlopen.api.sql;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.sonar.plugins.plsqlopen.api.DmlGrammar;
import org.sonar.plugins.plsqlopen.api.RuleTest;

public class ForUpdateClauseTest extends RuleTest {

    @Before
    public void init() {
        setRootRule(DmlGrammar.FOR_UPDATE_CLAUSE);
    }
    
    @Test
    public void matchesSimpleForUpdate() {
        assertThat(p).matches("for update");
    }
    
    @Test
    public void matchesForUpdateOfColumn() {
        assertThat(p).matches("for update of col");
    }
    
    @Test
    public void matchesForUpdateOfColumnWithTable() {
        assertThat(p).matches("for update of tab.col");
    }
    
    @Test
    public void matchesForUpdateOfColumnWithSchemaAndTable() {
        assertThat(p).matches("for update of sch.tab.col");
    }
    
    @Test
    public void matchesForUpdateOfMultipleColumns() {
        assertThat(p).matches("for update of col, col2, col3");
    }
    
    @Test
    public void matchesForUpdateNoWait() {
        assertThat(p).matches("for update nowait");
    }
    
    @Test
    public void matchesForUpdateWait() {
        assertThat(p).matches("for update wait 1");
    }
    
    @Test
    public void matchesForUpdateSkipLocked() {
        assertThat(p).matches("for update skip locked");
    }
    
    @Test
    public void matchesLongForUpdate() {
        assertThat(p).matches("for update of col skip locked");
    }

}
