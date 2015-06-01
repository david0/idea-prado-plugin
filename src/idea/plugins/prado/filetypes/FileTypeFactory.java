package idea.plugins.prado.filetypes;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import org.jetbrains.annotations.NotNull;

public class FileTypeFactory extends com.intellij.openapi.fileTypes.FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(PageFileType.INSTANCE, "page");
        fileTypeConsumer.consume(TemplateFileType.INSTANCE, "tpl");


    }

    public static boolean isTemplateOrPage(String fileName) {
        return fileName.endsWith(".tpl") | fileName.endsWith(".page");
    }
}
