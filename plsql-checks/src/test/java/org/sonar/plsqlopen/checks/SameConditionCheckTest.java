/*
 * Sonar PL/SQL Plugin (Community)
 * Copyright (C) 2015 Felipe Zorzo
 * felipe.b.zorzo@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plsqlopen.checks;

import org.junit.Test;
import org.sonar.plsqlopen.checks.SameConditionCheck;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

public class SameConditionCheckTest extends BaseCheckTest {

    @Test
    public void test() {
        SourceFile file = scanSingleFile("same_condition.sql", new SameConditionCheck());
        String message = "This condition duplicates the one on line %s.";
        CheckMessagesVerifier.verify(file.getCheckMessages())
            .next().atLine(3).withMessage(String.format(message, 3))
            .next().atLine(4).withMessage(String.format(message, 4))
            .next().atLine(10).withMessage(String.format(message, 9))
            .next().atLine(16).withMessage(String.format(message, 15))
            .noMore();
    }
    
}