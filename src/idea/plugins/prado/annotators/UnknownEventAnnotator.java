package idea.plugins.prado.annotators;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import idea.plugins.prado.ControlAttributeValueCondition;
import idea.plugins.prado.PradoControlUtil;
import idea.plugins.prado.filetypes.TemplateFileUtil;
import idea.plugins.prado.quickfixes.CreateControlEventQuckFix;
import idea.plugins.prado.quickfixes.CreateControlForPageQuckFix;

import java.util.Set;

/**
 * Adds warnings for events that can ne be found in the corresponding control class
 */
public class UnknownEventAnnotator implements Annotator {
    @Override
    public void annotate(PsiElement psiElement, AnnotationHolder annotationHolder) {
        PsiElement attributeValue = PsiTreeUtil.findFirstParent(psiElement, new ControlAttributeValueCondition());
        if (!(attributeValue instanceof XmlAttributeValue))
            return;

        XmlAttribute attribute = (XmlAttribute) attributeValue.getParent();
        if (!attribute.getParent().getName().startsWith("com:") || !attribute.getName().toLowerCase().startsWith("on"))
            return;

        PsiElement textNode = attributeValue.getFirstChild().getNextSibling();
        String controlName = TemplateFileUtil.classNameForTemplateFile(psiElement.getContainingFile());
        String eventName = ((XmlAttributeValue) psiElement).getValue();

        if (eventName.contains("."))
            return; // no support for Parent./Control. yet

        PhpClass cls = TemplateFileUtil.classForTemplateFile(psiElement.getContainingFile());
        if (cls == null) {
            annotationHolder.createWarningAnnotation(textNode, "Control '" + controlName + "' does not exist and cannot handle '" + eventName + "'")
                    .registerFix(new CreateControlForPageQuckFix(controlName, psiElement.getContainingFile().getContainingDirectory()));
            return;
        }

        Set<String> eventsForControl = PradoControlUtil.eventsForControl(cls);
        if (!eventsForControl.contains(eventName))
            annotationHolder.createWarningAnnotation(textNode, "Event " + eventName + "' cannot be found in control '" + controlName + "'")
                    .registerFix(new CreateControlEventQuckFix(cls, eventName));
    }
}
