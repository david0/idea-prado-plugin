package idea.plugins.prado.gotohandlers;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttributeValue;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import idea.plugins.prado.ControlAttributeValueCondition;
import idea.plugins.prado.filetypes.TemplateFileUtil;
import org.jetbrains.annotations.Nullable;

public class GotoEventDeclaration implements GotoDeclarationHandler {


    @Nullable
    @Override
    public PsiElement[] getGotoDeclarationTargets(PsiElement psiElement, int i, Editor editor) {

        XmlAttributeValue xmlAttributeValue = (XmlAttributeValue) PsiTreeUtil.findFirstParent(psiElement, new ControlAttributeValueCondition());
        if (xmlAttributeValue == null)
            return new PsiElement[0];

        PhpClass cls = TemplateFileUtil.classForTemplateFile(psiElement.getContainingFile());
        if (cls == null)
            return new PsiElement[0];

        for (Method method : cls.getMethods()) {
            if (method.getName().equals(xmlAttributeValue.getValue())) {
                return new PsiElement[]{method};
            }
        }


        return new PsiElement[0];
    }


    @Nullable
    @Override
    public String getActionText(DataContext dataContext) {
        return "Goto event declaration";
    }
}
