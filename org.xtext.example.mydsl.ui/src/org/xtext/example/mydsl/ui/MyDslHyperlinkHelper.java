package org.xtext.example.mydsl.ui;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.Region;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkAcceptor;
import org.xtext.example.mydsl.myDsl.Ref;
import org.xtext.example.mydsl.services.MyDslGrammarAccess;

public class MyDslHyperlinkHelper extends HyperlinkHelper {

	@Inject
	MyDslGrammarAccess ga;
	@Override
	public void createHyperlinksByOffset(XtextResource resource, int offset,
			IHyperlinkAcceptor acceptor) {
		ILeafNode leaf = NodeModelUtils.findLeafNodeAtOffset(resource.getParseResult().getRootNode(), offset);
		EObject sem = NodeModelUtils.findActualSemanticObjectFor(leaf);
		if(sem instanceof Ref){
			if(((Ref) sem).getTo().getName()==null &&leaf.getGrammarElement()==ga.getRefAccess().getFullStopKeyword_2()){
				createHyperlinksTo(resource, new Region(leaf.getOffset(), leaf.getLength()), ((Ref) sem).getTo(), acceptor);
				return;
			}
		}
		super.createHyperlinksByOffset(resource, offset, acceptor);
	}
}
