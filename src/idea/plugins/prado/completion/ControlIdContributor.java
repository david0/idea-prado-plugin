package idea.plugins.prado.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.PlatformIcons;
import com.intellij.util.ProcessingContext;
import com.intellij.util.indexing.FileBasedIndex;
import com.jetbrains.php.lang.parser.PhpElementTypes;
import idea.plugins.prado.filetypes.TemplateFileUtil;
import idea.plugins.prado.indexes.ViewControlsIndex;

import java.util.Collection;
import java.util.List;

import static com.intellij.patterns.PlatformPatterns.psiElement;

/**
 * Completions for fields derived from the template (.page/.tpl)
 */
public class ControlIdContributor extends CompletionContributor {
    public ControlIdContributor() {
        extend(CompletionType.BASIC, psiElement().withParent(psiElement(PhpElementTypes.FIELD_REFERENCE).withChild(psiElement().withText("$this"))), new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(CompletionParameters completionParameters, ProcessingContext processingContext, CompletionResultSet completionResultSet) {

                PsiFile classFile = completionParameters.getPosition().getContainingFile();
                PsiFile pageFile = TemplateFileUtil.findTemplateFileForPhpFile(classFile);
                if (pageFile == null) // no prado page class
                    return;

                Project project = completionParameters.getPosition().getProject();
                Collection<String> allKeys = FileBasedIndex.getInstance().getAllKeys(ViewControlsIndex.NAME, project);

                for (String key : allKeys) {
                    List<String> pageFileValuesForKey = FileBasedIndex.getInstance().getValues(ViewControlsIndex.NAME, key, GlobalSearchScope.fileScope(pageFile));
                    if (!pageFileValuesForKey.isEmpty()) // if key is contained in file
                        completionResultSet.addElement(LookupElementBuilder.create(key)
                                .withTypeText(pageFileValuesForKey.get(0))
                                .withIcon(PlatformIcons.FIELD_ICON));
                }
            }
        });
    }


}
