package idea.plugins.prado.documentation;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlTag;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.documentation.PhpDocumentationProvider;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import idea.plugins.prado.ControlTagCondition;

public class TemplateControlDocumentationProvider extends AbstractDocumentationProvider {
    @Override
    public String generateDoc(PsiElement element, PsiElement originalElement) {
        if (!new ControlTagCondition().value(originalElement.getParent()))
            return null;

        String controlName = ((XmlTag) originalElement.getParent()).getLocalName();

        PhpClass phpClass = PhpIndex.getInstance(originalElement.getProject()).getClassByName(controlName);
        if (phpClass == null)
            return null;

        PhpDocumentationProvider phpDocumentationProvider = new PhpDocumentationProvider();
        return phpDocumentationProvider.generateDoc(phpClass, phpClass);
    }
}
