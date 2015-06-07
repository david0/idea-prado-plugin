package idea.plugins.prado.indexes;


import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.indexing.*;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import com.intellij.util.xml.NanoXmlUtil;
import gnu.trove.THashMap;
import idea.plugins.prado.filetypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

import java.io.StringReader;
import java.util.Map;

/**
 * Index that holds all control ids (key) and their php class name(value)
 */
public class ViewControlsIndex extends FileBasedIndexExtension {
    public static final ID NAME = ID.create("prado.viewcontrolindex");
    private final KeyDescriptor<String> keyDescriptor = new EnumeratorStringDescriptor();

    @NotNull
    @Override
    public ID getName() {
        return NAME;
    }

    @NotNull
    @Override
    public DataIndexer getIndexer() {
        return new DataIndexer<String, String, FileContent>() {
            @NotNull
            @Override
            public Map<String, String> map(@NotNull FileContent fileContent) {
                Map<String, String> map = new THashMap<String, String>();
                NanoXmlUtil.parse(new StringReader(fileContent.getContentAsText().toString()), new PageControlTypesXmlBuilder(map));
                return map;
            }
        };
    }

    @NotNull
    @Override
    public KeyDescriptor getKeyDescriptor() {
        return keyDescriptor;
    }

    @NotNull
    @Override
    public DataExternalizer getValueExternalizer() {
        return keyDescriptor;
    }

    @NotNull
    @Override
    public FileBasedIndex.InputFilter getInputFilter() {
        return new FileBasedIndex.InputFilter() {
            @Override
            public boolean acceptInput(@NotNull VirtualFile virtualFile) {
                return FileTypeFactory.isTemplateOrPage(virtualFile.getName());
            }
        };
    }

    @Override
    public boolean dependsOnFileContent() {
        return true;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    private static class PageControlTypesXmlBuilder extends NanoXmlUtil.IXMLBuilderAdapter {
        private Map<String, String> controlIdToTypeMap;
        private String currentControlType = null;

        public PageControlTypesXmlBuilder(Map<String, String> controlIdToTypeMap) {
            this.controlIdToTypeMap = controlIdToTypeMap;
        }


        @Override
        public void startElement(String name, String nsPrefix, String nsURI, String systemID, int lineNr) throws Exception {
            if (nsPrefix.equals("com"))
                currentControlType = name;
        }

        @Override
        public void endElement(String name, String nsPrefix, String nsURI) throws Exception {
            currentControlType = null;
        }

        public void addAttribute(String key, String nsPrefix, String nsURI, String value, String type) throws Exception {

            if (currentControlType != null && key.equals("ID")) {
                controlIdToTypeMap.put(value, currentControlType);
            }
        }
    }
}
