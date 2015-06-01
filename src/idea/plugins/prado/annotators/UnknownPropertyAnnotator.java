package idea.plugins.prado.annotators;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import idea.plugins.prado.PradoControlUtil;

import java.util.Set;

public class UnknownPropertyAnnotator implements Annotator {
    @Override
    public void annotate(PsiElement psiElement, AnnotationHolder annotationHolder) {
        if (!(psiElement instanceof XmlAttribute))
            return;

        String propertyName = ((XmlAttribute) psiElement).getName();

        boolean firstCharIsUpperCase = propertyName.length() > 0 && propertyName.substring(0, 1).toUpperCase().equals(propertyName.substring(0, 1));
        if (!firstCharIsUpperCase)
            return; // HTML attribute

        boolean nestedAttribute = propertyName.contains(".");
        if (nestedAttribute)
            return; //not supported yet (simplification)

        String controlName = ((XmlTag) psiElement.getParent()).getLocalName();

        PhpIndex phpIndex = PhpIndex.getInstance(psiElement.getProject());
        PhpClass cls = phpIndex.getClassByName(controlName);
        if (cls == null)
            return; //invalid/unknown/not unique control name

        Set<String> attributes = PradoControlUtil.propertiesForControl(cls);
        if (!attributes.contains(propertyName))
            annotationHolder.createWarningAnnotation(psiElement.getFirstChild(), "Unknown property '" + propertyName + "'");

    }
}
