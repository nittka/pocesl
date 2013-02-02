package org.xtext.example.mydsl.scoping;

import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.naming.IQualifiedNameConverter.DefaultImpl;

public class MyDslQualifiedNameConverter extends DefaultImpl {

	@Override
	public QualifiedName toQualifiedName(String qualifiedNameAsString) {
		if(qualifiedNameAsString!=null &&"".equals(qualifiedNameAsString)){
			return QualifiedName.create("");
		}
		return super.toQualifiedName(qualifiedNameAsString);
	}
}
