package com.phpstorm.prado;

import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlFileNSInfoProvider;
import com.phpstorm.prado.filetypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Dummy namespace provider that registers a "com" namespace in order to
 * suppress the "unbound namespace" warning
 */
public class TemplateNamespaceProvider implements XmlFileNSInfoProvider {
    public static final String PRADO_NAMESPACE = "http://pradoframework.com/template";

    private static final String[][] NAMESPACES = {{"com", PRADO_NAMESPACE}};

    public String[][] getDefaultNamespaces(@NotNull XmlFile file) {
        return FileTypeFactory.isTemplateOrPage(file) ? NAMESPACES : null;
    }

    @Override
    public boolean overrideNamespaceFromDocType(@NotNull XmlFile file) {
        return false;
    }
}