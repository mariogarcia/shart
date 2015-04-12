package shart.app.ast

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotatedNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.tools.GeneralUtils
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation

import java.lang.annotation.Annotation

import static org.codehaus.groovy.ast.tools.GeneralUtils.*
import static org.codehaus.groovy.ast.ClassHelper.*

@CompileStatic
abstract class AnnotatedNodeASTTransformation<ANNOTATION, ANNOTATED> extends AbstractASTTransformation {

    private Class<ANNOTATION> annotationNodeType
    private Class<ANNOTATED> annotatedNodeType

    public AnnotatedNodeASTTransformation(Class<ANNOTATION> annotation, Class<ANNOTATED> annotated) {
        this.annotationNodeType = annotation
        this.annotatedNodeType = annotated
    }

    abstract void doVisit(AnnotationNode annotation, ClassNode annotated, SourceUnit source)

    @Override
    void visit(ASTNode[] nodes, SourceUnit source) {
        if (nodes.size() != 2) return
        if (!(nodes.first() instanceof AnnotationNode)) return
        if (!(nodes.last() instanceof ClassNode)) return

        AnnotationNode annotationNode = (AnnotationNode) nodes.first()
        ClassNode annotatedNode = (ClassNode) nodes.last()

        if (!annotationNode.classNode.isDerivedFrom(make(annotationNodeType))) return
        if (!annotatedNode.isDerivedFrom(make(annotatedNodeType))) return

        doVisit(annotationNode, annotatedNode, source)
    }


}