package shart.app.ast

import android.view.Menu
import android.view.MenuItem
import groovyjarjarasm.asm.Opcodes
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.PropertyExpression
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

import static org.codehaus.groovy.ast.ClassHelper.make
import static org.codehaus.groovy.ast.tools.GeneralUtils.*

@GroovyASTTransformation(phase = CompilePhase.INSTRUCTION_SELECTION)
class OptionsMenuASTTransformation extends AbstractASTTransformation implements Opcodes {

    @Override
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        if (!isValid(astNodes)) return

        AnnotationNode annotation = astNodes.first()
        ClassNode classNode = astNodes.last()
        Expression contentViewIdExpression = annotation.getMember('value')

        MethodNode onCreateOptionsMenuMethod = getOnCreateOptionsMenuMethod(contentViewIdExpression)
        MethodNode onOptionsItemSelectedMethod = getOnOptionsItemSelectedMethod(classNode)

        classNode.addMethod(onCreateOptionsMenuMethod)

        if(!classNode.getDeclaredMethods('onOptionsItemSelected').isEmpty()) return
        classNode.addMethod(onOptionsItemSelectedMethod)
    }

    MethodNode getOnCreateOptionsMenuMethod(Expression contentViewIdExpression) {
        MethodCallExpression methodCall =
            callX(varX('menuInflater'), 'inflate', args(contentViewIdExpression, varX('menu')))

        MethodNode onCreateOptionsMenuMethod =
            new MethodNode(
                'onCreateOptionsMenu',
                ACC_PUBLIC,
                make(boolean),
                params(param(make(Menu), 'menu')),
                [] as ClassNode[],
                block(stmt(methodCall), returnS(constX(true))))

        return onCreateOptionsMenuMethod
    }

    Boolean isValid(ASTNode[] astNodes) {
        if (astNodes.size() != 2) return false
        if (!(astNodes.first() instanceof AnnotationNode))  return false
        if (!(astNodes.last() instanceof ClassNode)) return false

        return true
    }

    MethodNode getOnOptionsItemSelectedMethod(ClassNode classNode) {
        List<IfStatement> ifStatementList =
            classNode
            .methods
            .findAll(this.&hasOnOptionsItemSelectedAnnotation)
            .collect(this.&extractIfStatementFromMethodNode) + [finalOnOptionsItemSelectedReturn]

        MethodNode onOptionsItemSelectedMethod = new MethodNode(
                'onOptionsItemSelected',
                ACC_PUBLIC,
                make(boolean),
                params(param(make(MenuItem), 'menuItem')),
                [] as ClassNode[],
                block(ifStatementList as Statement[]))

        return onOptionsItemSelectedMethod
    }

    ReturnStatement getFinalOnOptionsItemSelectedReturn() {
        return returnS(callSuperX('onOptionsItemSelected',args(varX('menuItem'))))
    }

    Boolean hasOnOptionsItemSelectedAnnotation(MethodNode node) {
        return !node.getAnnotations(make(OnOptionsItemSelected)).isEmpty()
    }

    Expression extractAnnotationValueFrom(MethodNode node) {
        PropertyExpression expression = node
            .getAnnotations(make(OnOptionsItemSelected))
            .first()
            .getMember('value')

        return expression
    }

    IfStatement extractIfStatementFromMethodNode(MethodNode methodNode) {
        return generateIfStatement(
            extractAnnotationValueFrom(methodNode),
            callX(varX('menuItem'),'getItemId'),
            callThisX(methodNode.name)
        )
    }

    IfStatement generateIfStatement(Expression var1, Expression var2, MethodCallExpression action) {
        return ifS(eqX(var1,var2), block(stmt(action), returnS(constX(true))))
    }

}
