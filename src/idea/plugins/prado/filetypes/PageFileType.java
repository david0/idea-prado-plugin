package idea.plugins.prado.filetypes;


import org.jetbrains.annotations.NotNull;

public class PageFileType extends TemplateFileType {
    public static final PageFileType INSTANCE = new PageFileType();

    private PageFileType() {
        super();
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

}
