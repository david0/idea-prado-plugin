package idea.plugins.prado.quickfixes;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.php.lang.psi.PhpPsiElementFactory;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;

public class CreateControlEventQuckFix implements IntentionAction {

    private final PhpClass control;
    private final String eventName;

    public CreateControlEventQuckFix(PhpClass control, String eventName) {
        this.control = control;
        this.eventName = eventName;
    }

    @NotNull
    @Override
    public String getText() {
        return "Add event " + eventName + " to control " + control.getName();
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "PRADO";
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile psiFile) {
        return true;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile psiFile) throws IncorrectOperationException {
        final Project myProject = project;

        StringBuilder builder = new StringBuilder("public function ");
        builder.append(eventName);
        builder.append("($sender, $param){}");

        Method ef = PhpPsiElementFactory.createMethod(myProject, builder.toString());

        PsiElement addedElement = control.addBefore(ef, control.getLastChild());

        ((Navigatable) addedElement.getNavigationElement()).navigate(true);
    }

    @Override
    public boolean startInWriteAction() {
        return true;
    }
}
