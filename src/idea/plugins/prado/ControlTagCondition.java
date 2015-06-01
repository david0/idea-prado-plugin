package idea.plugins.prado;

import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlTag;

public class ControlTagCondition implements Condition<PsiElement> {
    @Override
    public boolean value(PsiElement psiElement) {
        if (!(psiElement instanceof XmlTag))
            return false;

        XmlTag attribute = (XmlTag) psiElement;
        if (!attribute.getName().startsWith("com:"))
            return false;

        return true;
    }
}
