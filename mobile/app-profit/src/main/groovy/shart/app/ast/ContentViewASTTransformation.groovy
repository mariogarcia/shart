package shart.app.ast

import android.os.Bundle
import groovyjarjarasm.asm.Opcodes
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.PropertyExpression
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

import static org.codehaus.groovy.ast.tools.GeneralUtils.*
import static org.codehaus.groovy.ast.ClassHelper.*

@GroovyASTTransformation(phase = CompilePhase.INSTRUCTION_SELECTION)
class ContentViewASTTransformation extends AbstractASTTransformation implements Opcodes {

    @Override
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        if (!isValid(astNodes)) return

        AnnotationNode annotation = astNodes.first()
        ClassNode classNode = astNodes.last()
        Expression contentViewIdExpression = annotation.getMember('value')

        if (classNode.getDeclaredMethods('onCreate').isEmpty()) {
            MethodNode methodNode = createNewOnCreateMethodNode(contentViewIdExpression)

            classNode.addMethod(methodNode)
        }

    }

    MethodNode createNewOnCreateMethodNode(Expression contentViewId) {
        MethodNode methodNode = new MethodNode(
            'onCreate',
            ACC_PUBLIC,
            make(void.class),
            params(param(make(Bundle), 'bundle')),
            [] as ClassNode[],
            block(
                stmt(callX(varX('super'),'onCreate', args(varX('bundle')))),
                stmt(callX(varX('this'),'setContentView', args(contentViewId)))
            )
        )

        methodNode.addAnnotation(new AnnotationNode(make(Override)))

        return methodNode
    }

    Boolean isValid(ASTNode[] astNodes) {
        if (astNodes.size() != 2) return false
        if (!(astNodes.first() instanceof AnnotationNode))  return false
        if (!(astNodes.last() instanceof ClassNode)) return false

        AnnotationNode annotationNode = astNodes.first()
        ClassNode clazzNode = annotationNode.classNode
        Boolean isTheSameType = clazzNode == make(ContentView)

        if (!isTheSameType) return false

        return true
    }

}