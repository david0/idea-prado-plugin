package idea.plugins.prado.filetypes;

import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.XmlElementDescriptor;
import com.intellij.xml.XmlElementsGroup;
import com.intellij.xml.XmlNSDescriptor;
import org.jetbrains.annotations.Nullable;

/**
 * Created by ottodavid on 30.05.15.
 */
public class PradoElementDescriptor implements com.intellij.xml.XmlElementDescriptor {


    @Override
    public String getQualifiedName() {
        return "TBody";
    }

    @Override
    public String getDefaultName() {
        return "TBody";
    }

    @Override
    public XmlElementDescriptor[] getElementsDescriptors(XmlTag xmlTag) {
        return new XmlElementDescriptor[0];
    }

    @Nullable
    @Override
    public XmlElementDescriptor getElementDescriptor(XmlTag xmlTag, XmlTag xmlTag1) {
        return null;
    }

    @Override
    public XmlAttributeDescriptor[] getAttributesDescriptors(@Nullable XmlTag xmlTag) {
        return new XmlAttributeDescriptor[0];
    }

    @Nullable
    @Override
    public XmlAttributeDescriptor getAttributeDescriptor(String s, @Nullable XmlTag xmlTag) {
        return null;
    }

    @Nullable
    @Override
    public XmlAttributeDescriptor getAttributeDescriptor(XmlAttribute xmlAttribute) {
        return null;
    }

    @Override
    public XmlNSDescriptor getNSDescriptor() {
        return null;
    }

    @Nullable
    @Override
    public XmlElementsGroup getTopGroup() {
        return null;
    }

    @Override
    public int getContentType() {
        return 0;
    }

    @Nullable
    @Override
    public String getDefaultValue() {
        return null;
    }

    @Override
    public PsiElement getDeclaration() {
        return null;
    }

    @Override
    public String getName(PsiElement psiElement) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void init(PsiElement psiElement) {

    }

    @Override
    public Object[] getDependences() {
        return new Object[0];
    }
}
