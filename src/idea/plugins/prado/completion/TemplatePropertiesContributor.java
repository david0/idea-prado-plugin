package idea.plugins.prado.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.ElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlToken;
import com.intellij.util.PlatformIcons;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import idea.plugins.prado.PradoControlUtil;

import java.util.Set;

import static com.intellij.patterns.PlatformPatterns.or;
import static com.intellij.patterns.PlatformPatterns.psiElement;

/**
 * Completions for available template properties
 */
public class TemplatePropertiesContributor extends CompletionContributor {
    final ElementPattern<PsiElement> attributePattern = or(
            psiElement(XmlToken.class).withParent(XmlAttribute.class),
            psiElement(PsiWhiteSpace.class).withParent(psiElement().withParent(XmlTag.class))
    );

    public TemplatePropertiesContributor() {
        extend(CompletionType.BASIC, attributePattern, new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(CompletionParameters completionParameters, ProcessingContext processingContext, CompletionResultSet completionResultSet) {
                XmlTag controlTag = (XmlTag) completionParameters.getPosition().getParent().getParent();
                if (!controlTag.getName().startsWith("com:"))
                    return;

                PhpIndex phpIndex = PhpIndex.getInstance(completionParameters.getPosition().getProject());
                PhpClass cls = phpIndex.getClassByName(controlTag.getLocalName());
                if (cls == null)
                    return; //invalid/unknown/not unique control name

                Set<String> attributes = PradoControlUtil.propertiesForControl(cls);

                for (String attribute : attributes) {
                    completionResultSet.addElement(LookupElementBuilder.create(attribute).withIcon(PlatformIcons.FIELD_ICON));
                }
            }
        });

    }
}
