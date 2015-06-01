package idea.plugins.prado.filetypes;


import com.intellij.ide.highlighter.XmlLikeFileType;
import com.intellij.lang.html.HTMLLanguage;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import idea.plugins.prado.images.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.nio.charset.Charset;

public class TemplateFileType extends XmlLikeFileType {
    public static final TemplateFileType INSTANCE = new TemplateFileType();

    public TemplateFileType() {
        super(HTMLLanguage.INSTANCE);
        //super(TemplateLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "PRADO Template";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "PRADO Template";
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

    @Override
    public String getCharset(@NotNull VirtualFile virtualFile, @NotNull byte[] bytes) {
        return null;
    }

    @Override
    public Charset extractCharsetFromFileContent(@Nullable Project project, @Nullable VirtualFile file, @NotNull CharSequence content) {
        return null;
    }
}
