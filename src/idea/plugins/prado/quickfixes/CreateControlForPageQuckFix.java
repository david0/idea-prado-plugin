package idea.plugins.prado.quickfixes;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

public class CreateControlForPageQuckFix implements IntentionAction {
    private String controlName;
    private final PsiDirectory containingDirectory;

    public CreateControlForPageQuckFix(String controlName, PsiDirectory containingDirectory) {
        this.controlName = controlName;
        this.containingDirectory = containingDirectory;
    }


    @NotNull
    @Override
    public String getText() {
        return "Create control " + controlName;
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
        StringBuilder builder = new StringBuilder()
                .append("<?php \n\nclass ")
                .append(controlName)
                .append(" extends TPage\n{\n\n}");
        PsiFile file = PsiFileFactory.getInstance(project).createFileFromText(controlName + ".php", builder.toString());
        PsiElement addedFile = containingDirectory.add(file);


        //((Navigatable) addedFile.getNavigationElement()).navigate(true);
    }

    @Override
    public boolean startInWriteAction() {
        return true;
    }
}
