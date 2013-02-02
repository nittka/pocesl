package org.xtext.example.mydsl.ui;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.DefaultLocationInFileProvider;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.TextRegion;
import org.xtext.example.mydsl.myDsl.Class;
import org.xtext.example.mydsl.myDsl.MyDslPackage;
import org.xtext.example.mydsl.myDsl.Ref;

public class MyDslLocationInFileProvider extends DefaultLocationInFileProvider {

	@Override
	protected ITextRegion doGetLocationOfFeature(EObject owner,
			EStructuralFeature feature, int indexInList, RegionDescription query) {
		if((owner instanceof Ref ) && feature==MyDslPackage.Literals.REF__TO){
			if(((Ref)owner).getTo().getName()==null){
				ICompositeNode node = NodeModelUtils.findActualNodeFor(owner);
				return new TextRegion(node.getOffset()+4,0);
			}
		} 
		return super.doGetLocationOfFeature(owner, feature, indexInList, query);
	}
	
	@Override
	protected List<INode> getLocationNodes(EObject obj) {
		if(obj instanceof Class){
			if(((Class) obj).getName()==null){
				ICompositeNode start = NodeModelUtils.getNode(obj);
				int targetOffset = start.getOffset();
				for (ILeafNode leaf: start.getLeafNodes()) {
					if(leaf.getOffset()>=targetOffset){
						return Collections.singletonList((INode)leaf);
					}
				}
			}
		}
		return super.getLocationNodes(obj);
	}
}
