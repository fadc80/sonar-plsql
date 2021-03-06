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
package org.sonar.plsqlopen.highlight;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.plsqlopen.squid.PlSqlConfiguration;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class PlSqlHighlighterTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();
    
    private String eol;

    @Test
    public void shouldAnalyse_lf() throws IOException {
    	eol = "\n";
    	verifyHighlighting();
    }
    
    @Test
    public void shouldAnalyse_crlf() throws IOException {
    	eol = "\r\n";
    	verifyHighlighting();
    }
    
    @Test
    public void shouldAnalyse_cr() throws IOException {
    	eol = "\r";
    	verifyHighlighting();
    }
    
    private void verifyHighlighting() throws IOException {
    	File baseDir = temp.newFolder();
        File file = new File(baseDir, "test.sql");
        String content = Files.toString(new File("src/test/resources/org/sonar/plsqlopen/highlight.sql"), Charsets.UTF_8);
        Files.write(content.replaceAll("\\r\\n", "\n").replaceAll("\\n", eol), file, Charsets.UTF_8);
        
        DefaultInputFile inputFile = new DefaultInputFile("key", "test.sql").setLanguage("plsqlopen")
                .initMetadata(Files.toString(file, Charsets.UTF_8));
        
        SensorContextTester context = SensorContextTester.create(baseDir);
        context.fileSystem().add(inputFile);

        PlSqlHighlighter highlighter = new PlSqlHighlighter(new PlSqlConfiguration(Charsets.UTF_8));
        highlighter.highlight(context, inputFile);
        
        String key = "key:test.sql";
        assertThat(context.highlightingTypeAt(key, 1, lineOffset(1))).containsExactly(TypeOfText.KEYWORD);
        assertThat(context.highlightingTypeAt(key, 2, lineOffset(3))).containsExactly(TypeOfText.COMMENT);
        assertThat(context.highlightingTypeAt(key, 3, lineOffset(3))).containsExactly(TypeOfText.STRUCTURED_COMMENT);
        assertThat(context.highlightingTypeAt(key, 6, lineOffset(8))).containsExactly(TypeOfText.STRING);
        assertThat(context.highlightingTypeAt(key, 7, lineOffset(1))).containsExactly(TypeOfText.KEYWORD);
    }
    
    private int lineOffset(int offset) {
        return offset - 1;
    }
}
