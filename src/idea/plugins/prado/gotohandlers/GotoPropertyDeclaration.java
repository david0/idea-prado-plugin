package idea.plugins.prado.gotohandlers;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.jetbrains.php.lang.psi.elements.Method;
import idea.plugins.prado.filetypes.TemplateFileUtil;
import org.jetbrains.annotations.Nullable;

public class GotoPropertyDeclaration implements GotoDeclarationHandler {
    @Nullable
    @Override
    public PsiElement[] getGotoDeclarationTargets(PsiElement psiElement, int i, Editor editor) {
        XmlAttribute xmlAttribute = PsiTreeUtil.getParentOfType(psiElement, XmlAttribute.class);
        if (xmlAttribute == null)
            return null;

        Method method = TemplateFileUtil.phpMethodForAttribute(xmlAttribute);
        if (method == null)
            return null;
        return new PsiElement[]{method};
    }

    @Nullable
    @Override
    public String getActionText(DataContext dataContext) {
        return "Goto declaration of property";
    }
}
