package idea.plugins.prado;

import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;

public class ControlAttributeValueCondition implements Condition<PsiElement> {
    @Override
    public boolean value(PsiElement psiElement) {
        return psiElement instanceof XmlAttributeValue
                && psiElement.getParent().getParent() instanceof XmlTag
                && (((XmlTag) psiElement.getParent().getParent()).getName().startsWith("com:"));
    }
};
