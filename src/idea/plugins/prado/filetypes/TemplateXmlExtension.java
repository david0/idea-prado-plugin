package idea.plugins.prado.filetypes;

import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.HtmlXmlExtension;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

/**
 * Extension for "com:" namespace in HTML
 * (does not change anything yet)
 */
public class TemplateXmlExtension extends HtmlXmlExtension {

    @NotNull
    @Override
    public List<TagInfo> getAvailableTagNames(@NotNull XmlFile xmlFile, @NotNull XmlTag xmlTag) {
        List<TagInfo> availableTagNames = super.getAvailableTagNames(xmlFile, xmlTag);

        Collection<PhpClass> controls = PhpIndex.getInstance(xmlFile.getProject()).getAllSubclasses("TControl");
        for (PhpClass control : controls)
            availableTagNames.add(new TagInfo(control.getName(), "com"));

        return availableTagNames;
    }

}
