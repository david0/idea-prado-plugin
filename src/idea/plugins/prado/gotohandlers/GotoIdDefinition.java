package idea.plugins.prado.gotohandlers;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.util.indexing.FileBasedIndex;
import com.jetbrains.php.lang.psi.elements.FieldReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import idea.plugins.prado.PradoControlUtil;
import idea.plugins.prado.filetypes.TemplateFileUtil;
import idea.plugins.prado.indexes.ViewControlsIndex;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class GotoIdDefinition implements GotoDeclarationHandler {
    @Nullable
    @Override
    public PsiElement[] getGotoDeclarationTargets(PsiElement psiElement, int i, Editor editor) {
        FieldReference fieldReference = PsiTreeUtil.getParentOfType(psiElement, FieldReference.class);
        PhpClass phpClass = PradoControlUtil.classForFieldReference(fieldReference);
        if (phpClass == null)
            return new PsiElement[0];

        PsiFile pageFile = TemplateFileUtil.findTemplateFileForPhpFile(phpClass.getContainingFile());
        if (pageFile == null) // no prado page class
            return new PsiElement[0];

        String key = fieldReference.getName();
        List<String> pageFileValuesForKey = FileBasedIndex.getInstance().getValues(ViewControlsIndex.NAME, key, GlobalSearchScope.fileScope(pageFile));
        if (!pageFileValuesForKey.isEmpty()) // if key is contained in file
        {
            Collection<XmlAttribute> xmlAttributes = PsiTreeUtil.findChildrenOfType(pageFile, XmlAttribute.class);
            for (XmlAttribute xmlAttribute : xmlAttributes) {
                if (xmlAttribute.getParent().getNamespacePrefix().equals("com")
                        && xmlAttribute.getName().equals("ID")
                        && xmlAttribute.getValue().equals(key))
                    return new PsiElement[]{xmlAttribute};
            }
        }

        return new PsiElement[0];
    }

    @Nullable
    @Override
    public String getActionText(DataContext dataContext) {
        return "Goto ID definition";
    }
}
