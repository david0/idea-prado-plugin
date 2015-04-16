package com.phpstorm.prado.filetypes;


import com.intellij.openapi.fileTypes.LanguageFileType;
import com.phpstorm.prado.images.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TemplateFileType extends LanguageFileType {
    public static final TemplateFileType INSTANCE = new TemplateFileType();

    private TemplateFileType() {
        super(TemplateLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "PRADO Template";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "PRADO Template file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "tpl";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return Icons.TEMPLATE;
    }
}
