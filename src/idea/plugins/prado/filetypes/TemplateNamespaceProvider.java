package idea.plugins.prado.filetypes;

import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlFileNSInfoProvider;
import com.intellij.xml.util.XmlUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Dummy namespace provider that registers a "com" namespace in order to
 * suppress the "unbound namespace" warning
 */
public class TemplateNamespaceProvider implements XmlFileNSInfoProvider {
    public static final String PRADO_NAMESPACE = "http://pradoframework.com/template";
    public static final String PRADO_PROP_NAMESPACE = "http://pradoframework.com/template-prop";

    private static final String[][] NAMESPACES = {
            new String[]{"com", PRADO_NAMESPACE},  // XSLT URL is a hack in order to make it clear that this will be further processed
            new String[]{"prop", PRADO_PROP_NAMESPACE},
            new String[]{"", XmlUtil.HTML_URI}
    };

    public String[][] getDefaultNamespaces(@NotNull XmlFile file) {
        return FileTypeFactory.isTemplateOrPage(file.getName()) ? NAMESPACES : null;
    }

    @Override
    public boolean overrideNamespaceFromDocType(@NotNull XmlFile file) {
        return false;
    }
}