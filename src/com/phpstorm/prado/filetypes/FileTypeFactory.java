package com.phpstorm.prado.filetypes;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.psi.xml.XmlFile;
import org.jetbrains.annotations.NotNull;

public class FileTypeFactory extends com.intellij.openapi.fileTypes.FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(PageFileType.INSTANCE, "page");
        fileTypeConsumer.consume(TemplateFileType.INSTANCE, "tpl");
    }

    public static boolean isTemplateOrPage(XmlFile file) {
        return file.getName().endsWith(".tpl") | file.getName().endsWith(".page");
    }
}
