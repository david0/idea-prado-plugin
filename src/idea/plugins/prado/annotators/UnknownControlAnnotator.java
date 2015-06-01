package idea.plugins.prado.annotators;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlTag;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import idea.plugins.prado.ControlTagCondition;

import java.util.Collection;

/**
 * Adds warnings for controls that can not be found
 */
public class UnknownControlAnnotator implements Annotator {
    @Override
    public void annotate(PsiElement psiElement, AnnotationHolder annotationHolder) {
        if (new ControlTagCondition().value(psiElement) == false)
            return;
        XmlTag attribute = (XmlTag) psiElement;

        if (!attribute.getName().startsWith("com:"))
            return;

        String controlName = attribute.getLocalName();

        PhpIndex phpIndex = PhpIndex.getInstance(psiElement.getProject());
        Collection<PhpClass> classes = phpIndex.getClassesByName(controlName);
        if (classes.isEmpty())
            annotationHolder.createWarningAnnotation(psiElement, "Control " + controlName + " is not known");
    }
}
