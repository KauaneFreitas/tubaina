package br.com.caelum.tubaina.parser.html.desktop;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import br.com.caelum.tubaina.parser.html.HtmlCodeTag;

public class CodeTagTest {

    @Test
    public void shouldCallHtmlCodeTag() {
        String code = "public static void main(String[] args) {\n" +
                "    String name = \"Gabriel\";\n" +
                "    System.out.println(\"Hello, \" + name);\n" +
                "}";
        HtmlCodeTag htmlCodeTag = mock(HtmlCodeTag.class);
        CodeTag codeTag = new CodeTag(htmlCodeTag);
        codeTag.parse(code, "java");
        verify(htmlCodeTag).parse(eq(code), eq("java"));
    }
}
