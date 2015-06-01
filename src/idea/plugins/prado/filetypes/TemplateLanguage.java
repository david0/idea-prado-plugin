package idea.plugins.prado.filetypes;

import com.intellij.ide.highlighter.XmlFileHighlighter;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import org.jetbrains.annotations.NotNull;

public class TemplateLanguage extends XMLLanguage {

    public static final TemplateLanguage INSTANCE = new TemplateLanguage();

    private TemplateLanguage() {
        super(TemplateLanguage.INSTANCE, "PRADO Template", "text/xml", "application/xml");
        SyntaxHighlighterFactory.LANGUAGE_FACTORY.addExplicitExtension(this, new SingleLazyInstanceSyntaxHighlighterFactory() {
            @NotNull
            protected SyntaxHighlighter createHighlighter() {
                return new XmlFileHighlighter(false, true);
            }
        });

    }

}