package idea.plugins.prado.filetypes;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;

import java.util.Collection;

public class TemplateFileUtil {
    public static PsiFile findTemplateFileForPhpFile(PsiFile classFile) {
        PsiDirectory containingDirectory = classFile.getOriginalFile().getContainingDirectory();
        if (containingDirectory == null) // i don't know why, but there are files without an containing directory?!
            return null;

        String pageFileName = classFile.getName().replace(".php", ".page");
        PsiFile pageFile = containingDirectory.findFile(pageFileName);

        if (pageFile != null)
            return pageFile;

        String templateFileName = classFile.getName().replace(".php", ".tpl");
        PsiFile templateFile = containingDirectory.findFile(templateFileName);
        return templateFile;

    }

    public static String classNameForTemplateFile(PsiFile templateFile) {
        String templateFileName = templateFile.getName();

        return templateFileName.substring(0, templateFileName.lastIndexOf("."));
    }

    public static PhpClass classForTemplateFile(PsiFile templateFile) {
        String controlName = classNameForTemplateFile(templateFile);
        PhpIndex phpIndex = PhpIndex.getInstance(templateFile.getProject());
        Collection<PhpClass> classes = phpIndex.getClassesByName(controlName);
        for (PhpClass phpClass : classes) {
            PsiDirectory containingDirectory = phpClass.getContainingFile().getContainingDirectory();
            if (containingDirectory.equals(templateFile.getContainingDirectory()))
                return phpClass;
        }
        return null;
    }

}
