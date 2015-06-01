package idea.plugins.prado.filetypes;

import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.SingleRootFileViewProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TemplateFileViewProvider extends SingleRootFileViewProvider {
    public TemplateFileViewProvider(PsiManager psiManager, VirtualFile virtualFile, boolean b, Language language) {
        super(psiManager, virtualFile, b);
    }


    @Nullable
    @Override
    protected PsiFile getPsiInner(@NotNull Language language) {
        return super.getPsiInner(language);
    }

    @NotNull
    @Override
    public Language getBaseLanguage() {
        return TemplateLanguage.INSTANCE;
    }


}
