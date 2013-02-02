package org.xtext.example.mydsl.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.diagnostics.IDiagnosticProducer;
import org.eclipse.xtext.linking.lazy.LazyLinker;
import org.eclipse.xtext.linking.lazy.SettingDelegate;
import org.eclipse.xtext.nodemodel.BidiIterable;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.xtext.example.mydsl.myDsl.Ref;

import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;

public class MyDslLazyLinker extends LazyLinker {

	@Override
	protected void installProxies(EObject obj, IDiagnosticProducer producer,
			Multimap<Setting, INode> settingsToLink) {
		if(obj instanceof Ref){
			ICompositeNode parentNode = NodeModelUtils.getNode(obj);
			if (parentNode != null){
				BidiIterable<INode> children = parentNode.getChildren();
				for (INode iNode : children) {
					if(iNode.getGrammarElement() instanceof CrossReference && Iterables.isEmpty(iNode.getLeafNodes())){
						CrossReference ref = (CrossReference) iNode.getGrammarElement();
						producer.setNode(iNode);
						final EReference eRef = GrammarUtil.getReference(ref, obj.eClass());
						if (eRef == null) {
							throw new IllegalStateException("Couldn't find EReference for crossreference " + ref);
						}
						if (!eRef.isResolveProxies() /*|| eRef.getEOpposite() != null see https://bugs.eclipse.org/bugs/show_bug.cgi?id=282486*/) {
							final EStructuralFeature.Setting setting = ((InternalEObject) obj).eSetting(eRef);
							settingsToLink.put(new SettingDelegate(setting), iNode);
						} else {
							createAndSetProxy(obj, iNode, eRef);
						}
						return;
					}
				}
			}
		}
		super.installProxies(obj, producer, settingsToLink);
	}
}
