package idea.plugins.prado;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;
import com.intellij.util.io.StringRef;
import com.jetbrains.php.lang.parser.PhpStubElementTypes;
import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.PhpPsiUtil;
import com.jetbrains.php.lang.psi.elements.Field;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.impl.FieldImpl;
import com.jetbrains.php.lang.psi.resolve.types.PhpType;
import com.jetbrains.php.lang.psi.stubs.PhpFieldStub;
import com.jetbrains.php.lang.psi.stubs.PhpFieldStubImpl;
import org.jetbrains.annotations.NotNull;


public class PradoControlReferenceProvider extends PsiReferenceProvider {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(PsiElement psiElement, ProcessingContext processingContext) {
        if (psiElement instanceof XmlTag) {
            XmlTag tag = (XmlTag) psiElement;
            String templateFilename = tag.getContainingFile().getName();
            String prefix = templateFilename.substring(0, templateFilename.lastIndexOf("."));
            PhpFile phpFile = (PhpFile) psiElement.getContainingFile().getContainingDirectory().findFile(prefix + ".php");

            final PhpClass cls = (PhpClass) PhpPsiUtil.findAllClasses(phpFile).toArray()[0];


            //PsiElement field = PhpPsiElementFactory.createFromText(cls.getProject(), PhpStubElementTypes.CLASS_FIELD, "myField");
            //PhpFieldStub field = new PhpFieldStubImp//
            PhpFieldStub stub = new PhpFieldStubImpl(cls.getStub(), PhpStubElementTypes.CLASS_FIELD, StringRef.fromString("test1"), PhpType.OBJECT, (short) 0);
            Field field = new FieldImpl(stub);
            field.setName("test");
            //PhpPsiElementFactory.
            //Field field = cls.findFieldByName("myField", false);


            cls.getFields().add(field);
            // add contents to class
            //PsiElementFactory elementFactory = PsiElementFactory.SERVICE.getInstance(psiElement.getProject());
//                final PsiField newField = elementFactory.createFieldFromText("/** @var Test */ private $test;", null);
//

/*
                for (PsiElement element : phpFile.getChildren()) {
                    if(element instanceof PhpClass) {
                       element.add (newField);
                    }
                }
                */

        }
        return new PsiReference[0];
    }
}
