package com.phpstorm.prado.filetypes;


import com.intellij.openapi.fileTypes.LanguageFileType;
import com.phpstorm.prado.images.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PageFileType extends LanguageFileType {
    public static final PageFileType INSTANCE = new PageFileType();

    private PageFileType() {
        super(TemplateLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "PRADO Page";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "PRADO Page";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "page";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return Icons.TEMPLATE;
    }
}
