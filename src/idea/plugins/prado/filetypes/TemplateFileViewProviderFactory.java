package idea.plugins.prado.filetypes;

import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.FileViewProviderFactory;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;

public class TemplateFileViewProviderFactory implements FileViewProviderFactory {
    @NotNull
    @Override
    public FileViewProvider createFileViewProvider(VirtualFile virtualFile, Language language, @NotNull PsiManager psiManager, boolean b) {
        assert language.isKindOf(TemplateLanguage.INSTANCE);
        return new TemplateFileViewProvider(psiManager, virtualFile, b, language);
    }
}
