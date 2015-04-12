package shart.app.ast

import android.app.Activity
import android.view.Menu
import android.view.MenuItem
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
import org.codehaus.groovy.transform.GroovyASTTransformation

import static org.codehaus.groovy.ast.ClassHelper.boolean_TYPE
import static org.codehaus.groovy.ast.ClassHelper.make
import static org.codehaus.groovy.ast.tools.GeneralUtils.*

@GroovyASTTransformation(phase = CompilePhase.INSTRUCTION_SELECTION)
class OptionsMenuASTTransformation extends AnnotatedNodeASTTransformation {

    OptionsMenuASTTransformation() {
        super(OptionsMenu, Activity)
    }

    @Override
    void doVisit(AnnotationNode annotation, ClassNode classNode, SourceUnit sourceUnit) {
        Expression contentViewIdExpression = annotation.getMember('value')
        MethodNode onCreateOptionsMenuMethod = getOnCreateOptionsMenuMethod(contentViewIdExpression)
        MethodNode onOptionsItemSelectedMethod = getOnOptionsItemSelectedMethod(classNode)

        classNode.addMethod(onCreateOptionsMenuMethod)

        if (!hasDeclaredMethod(classNode, 'onOptionsItemSelected', 1)) {
            classNode.addMethod(onOptionsItemSelectedMethod)
        }
    }

    MethodNode getOnCreateOptionsMenuMethod(Expression contentViewIdExpression) {
        MethodCallExpression methodCall =
                callX(varX('menuInflater'), 'inflate', args(contentViewIdExpression, varX('menu')))
        MethodNode onCreateOptionsMenuMethod =
            new MethodNode(
                'onCreateOptionsMenu',
                ACC_PUBLIC,
                boolean_TYPE,
                params(param(make(Menu), 'menu')),
                [] as ClassNode[],
                block(
                    stmt(methodCall),
                    returnS(constX(true))
                )
            )

        return onCreateOptionsMenuMethod
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
                boolean_TYPE,
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
