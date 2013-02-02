package org.xtext.example.mydsl.scoping;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.linking.impl.DefaultLinkingService;
import org.eclipse.xtext.linking.impl.IllegalNodeException;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.inject.Inject;

public class MyDslLinkingService extends DefaultLinkingService {

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	@Override
	public List<EObject> getLinkedObjects(EObject context, EReference ref,
			INode node) throws IllegalNodeException {
		final EClass requiredType = ref.getEReferenceType();
		if (requiredType == null)
			return Collections.<EObject> emptyList();

		final String crossRefString = getCrossRefNodeAsString(node);
		if (crossRefString != null) {
//			if (logger.isDebugEnabled()) {
//				logger.debug("before getLinkedObjects: node: '" + crossRefString + "'");
//			}
				
			final IScope scope = getScope(context, ref);

			QualifiedName qualifiedLinkName =  "".equals(crossRefString)?QualifiedName.create(""):qualifiedNameConverter.toQualifiedName(crossRefString);
			IEObjectDescription eObjectDescription = scope.getSingleElement(qualifiedLinkName);
//			if (logger.isDebugEnabled()) {
//				logger.debug("after getLinkedObjects: node: '" + crossRefString + "' result: " + eObjectDescription);
//			}
			if (eObjectDescription != null) 
				return Collections.singletonList(eObjectDescription.getEObjectOrProxy());
		}
		return Collections.emptyList();
	}
}
