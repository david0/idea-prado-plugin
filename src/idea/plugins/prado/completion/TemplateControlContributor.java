package idea.plugins.prado.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlToken;
import com.intellij.util.PlatformIcons;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;

import java.util.Collection;

/**
 * Completions for available controls
 */
public class TemplateControlContributor extends CompletionContributor {

    private PsiElementPattern.Capture<XmlToken> controlNamePattern = PlatformPatterns.psiElement(XmlToken.class).withParent(XmlTag.class);

    public TemplateControlContributor() {
        extend(CompletionType.BASIC, controlNamePattern, new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(CompletionParameters completionParameters, ProcessingContext processingContext, CompletionResultSet completionResultSet) {
                XmlTag parent = (XmlTag) completionParameters.getPosition().getParent();
                if (!parent.getNamespacePrefix().equals("com"))
                    return;

                Project project = completionParameters.getPosition().getProject();
                Collection<PhpClass> controls = PhpIndex.getInstance(project).getAllSubclasses("TControl");

                for (PhpClass control : controls)
                    completionResultSet.addElement(LookupElementBuilder.create(control.getName()).withIcon(PlatformIcons.CLASS_ICON));
            }
        });
    }


}
