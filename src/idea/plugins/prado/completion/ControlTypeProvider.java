package idea.plugins.prado.completion;

import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndexImpl;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.FieldReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider2;
import idea.plugins.prado.PradoControlUtil;
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
            if(DumbService.getInstance(psiElement.getProject()).isDumb())
                return null; //index not yet ready.

            FieldReference fieldReference = (FieldReference) psiElement;
            PhpClass phpClass = PradoControlUtil.classForFieldReference(fieldReference);
            if (phpClass == null)
                return null;

            PsiFile pageFile = TemplateFileUtil.findTemplateFileForPhpFile(phpClass.getContainingFile());
            if (pageFile == null) // no prado page class
                return null;

            List<String> values = FileBasedIndexImpl.getInstance().getValues(ViewControlsIndex.NAME, fieldReference.getName(), GlobalSearchScope.fileScope(pageFile));
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
