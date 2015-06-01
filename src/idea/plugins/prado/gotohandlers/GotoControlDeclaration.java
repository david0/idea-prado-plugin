package idea.plugins.prado.gotohandlers;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlTag;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import idea.plugins.prado.ControlTagCondition;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class GotoControlDeclaration implements GotoDeclarationHandler {
    @Nullable
    @Override
    public PsiElement[] getGotoDeclarationTargets(PsiElement psiElement, int i, Editor editor) {
        if (new ControlTagCondition().value(psiElement.getParent()) == false)
            return new PsiElement[0];

        String controlName = ((XmlTag) psiElement.getParent()).getLocalName();

        PhpIndex phpIndex = PhpIndex.getInstance(psiElement.getProject());
        Collection<PhpClass> classes = phpIndex.getClassesByName(controlName);

        PsiElement[] psiElements = new PsiElement[classes.size()];
        psiElements = classes.toArray(psiElements);
        return psiElements;
    }

    @Nullable
    @Override
    public String getActionText(DataContext dataContext) {
        return "Goto control declartion";
    }
}
