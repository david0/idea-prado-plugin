package idea.plugins.prado.documentation;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.documentation.PhpDocumentationProvider;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import idea.plugins.prado.ControlAttributeValueCondition;
import idea.plugins.prado.filetypes.TemplateFileUtil;
import org.apache.commons.lang.StringUtils;

public class TemplatePropertyDocumentationProvider extends AbstractDocumentationProvider {
    @Override
    public String generateDoc(PsiElement element, PsiElement originalElement) {
        XmlAttribute xmlAttribute = PsiTreeUtil.getParentOfType(originalElement, XmlAttribute.class);
        if (xmlAttribute == null)
            return null;

        if(!xmlAttribute.getParent().getNamespacePrefix().equals("com"))
            return null;

        PhpClass cls = PhpIndex.getInstance(xmlAttribute.getProject()).getClassByName(xmlAttribute.getParent().getLocalName());
        if (cls == null)
            return null;

        String propertyName = xmlAttribute.getName().toLowerCase();
        String setterName = "set" + propertyName;

        for (Method method : cls.getMethods()) {
            if (method.getName().toLowerCase().equals(setterName) || method.getName().toLowerCase().equals(propertyName)) {
                PhpDocumentationProvider phpDocumentationProvider = new PhpDocumentationProvider();
                return phpDocumentationProvider.generateDoc(method, method);
            }
        }

        return null;
    }
}
