package shart.app.ast

import android.app.Activity
import android.os.Bundle
import com.arasthel.swissknife.SwissKnife
import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.GroovyASTTransformation

import static org.codehaus.groovy.ast.ClassHelper.VOID_TYPE
import static org.codehaus.groovy.ast.ClassHelper.make
import static org.codehaus.groovy.ast.tools.GeneralUtils.*

@CompileStatic
@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
class ContentViewASTTransformation extends AnnotatedNodeASTTransformation {

    ContentViewASTTransformation() {
        super(ContentView, Activity)
    }

    @Override
    void doVisit(AnnotationNode annotation, ClassNode classNode, SourceUnit source) {
        Expression contentViewIdExpression = annotation.getMember('value')

        if (!hasDeclaredMethod(classNode, 'onCreate', 1)) {
            classNode.addMethod(createNewOnCreateMethodNode(contentViewIdExpression))
        }
    }

    MethodNode createNewOnCreateMethodNode(Expression contentViewId) {
        Parameter bundleParam = param(make(Bundle), 'bundle')
        MethodNode methodNode = new MethodNode(
            'onCreate',
            ACC_PUBLIC,
            VOID_TYPE,
            params(bundleParam),
            [] as ClassNode[],
            block(
                stmt(callSuperX('onCreate', args(varX(bundleParam)))),
                stmt(callThisX('setContentView', args(contentViewId))),
                stmt(callX(make(SwissKnife),'inject', args(varX('this'))))
            )
        )

        methodNode.addAnnotation(new AnnotationNode(make(Override)))

        return methodNode
    }

}
