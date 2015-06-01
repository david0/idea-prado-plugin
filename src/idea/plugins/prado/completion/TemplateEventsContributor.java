package idea.plugins.prado.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlToken;
import com.intellij.util.PlatformIcons;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import idea.plugins.prado.PradoControlUtil;
import idea.plugins.prado.filetypes.TemplateFileUtil;

import java.util.Set;

import static com.intellij.patterns.PlatformPatterns.psiElement;

/**
 * Completions for template attributes like OnCommand="..."
 */
public class TemplateEventsContributor extends CompletionContributor {
    private final PsiElementPattern.Capture<XmlAttributeValue> attrValuePattern = psiElement(XmlAttributeValue.class);
    private final PsiElementPattern.Capture<PsiElement> jsOnClickPattern = psiElement().withSuperParent(4, attrValuePattern);
    private final ElementPattern<PsiElement> attrValueOrJsOnClickPattern = PlatformPatterns.or(psiElement(XmlToken.class).withParent(attrValuePattern), jsOnClickPattern);

    public TemplateEventsContributor() {
        extend(CompletionType.BASIC, attrValueOrJsOnClickPattern, new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(CompletionParameters completionParameters, ProcessingContext processingContext, CompletionResultSet completionResultSet) {
                        XmlAttribute attributeElement = PsiTreeUtil.getParentOfType(completionParameters.getPosition(), XmlAttribute.class);
                        if (attributeElement == null)
                            return;
                        String attributeName = attributeElement.getName();
                        if (!attributeName.toLowerCase().startsWith("on"))
                            return;

                        PhpClass cls = TemplateFileUtil.classForTemplateFile(completionParameters.getOriginalFile());
                        if (cls == null)
                            return; //invalid/unknown control name

                        Set<String> events = PradoControlUtil.eventsForControl(cls);
                        for (String event : events)
                            completionResultSet.addElement(LookupElementBuilder.create(event).withIcon(PlatformIcons.METHOD_ICON));
                    }

                }

        );

    }
}
