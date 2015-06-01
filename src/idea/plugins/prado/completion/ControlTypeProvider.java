package idea.plugins.prado.completion;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndexImpl;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.FieldReference;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider2;
import idea.plugins.prado.filetypes.TemplateFileUtil;
import idea.plugins.prado.indexes.ViewControlsIndex;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * Completes types from the templates controls
 */
public class ControlTypeProvider implements PhpTypeProvider2 {
    @Override
    public char getKey() {
        return '\u0153';
    }

    @Nullable
    @Override
    public String getType(PsiElement psiElement) {
        if (psiElement instanceof FieldReference) {
            String varName = ((FieldReference) psiElement).getName();

            PsiFile classFile = psiElement.getContainingFile();
            PsiFile pageFile = TemplateFileUtil.findTemplateFileForPhpFile(classFile);
            if (pageFile == null) // no prado page class
                return null;

            List<String> values = FileBasedIndexImpl.getInstance().getValues(ViewControlsIndex.NAME, varName, GlobalSearchScope.fileScope(pageFile));
            if (values.isEmpty())
                return null;
            return values.get(0);
        }
        return null;
    }

    @Override
    public Collection<? extends PhpNamedElement> getBySignature(String s, Project project) {
        PhpIndex phpIndex = PhpIndex.getInstance(project);
        return phpIndex.getClassesByName(s);
    }
}