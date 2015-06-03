package idea.plugins.prado;

import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.*;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class PradoControlUtil {

    public static PhpClass classForFieldReference(FieldReference fieldReference) {
        Variable[] variables = PsiTreeUtil.getChildrenOfType(fieldReference, Variable.class);
        if(variables == null)
            return null;

        Variable variable = variables[0];

        String inferredTypeName = variable.getInferredType(1).toStringRelativized("\\");
        PhpClass phpClass = PhpIndex.getInstance(fieldReference.getProject()).getClassByName(inferredTypeName);
        return phpClass;

    }

    public static Set<String> propertiesForControl(PhpClass cls) {
        Set<String> result = new HashSet<String>();

        for (Method method : cls.getMethods()) {
            if (!method.getModifier().equals(PhpModifier.PUBLIC_IMPLEMENTED_DYNAMIC))
                continue;

            if (method.getName().startsWith("set")) {
                String propertyName = method.getName().substring(3);
                result.add(propertyName);

            } else if (method.getName().toLowerCase().startsWith("on")) {
                String eventName = StringUtils.capitalize(method.getName());
                result.add(eventName);
            }
        }
        return result;
    }


    public static Set<String> eventsForControl(PhpClass cls) {
        Set<String> result = new HashSet<String>();

        for (Method method : cls.getMethods()) {
            if (!method.getModifier().equals(PhpModifier.PUBLIC_IMPLEMENTED_DYNAMIC))
                continue;

            result.add(method.getName());
        }
        return result;
    }
}
