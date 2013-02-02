package org.xtext.example.mydsl.scoping;

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.xtext.example.mydsl.myDsl.Class;

public class MyDslNameProvider extends DefaultDeclarativeQualifiedNameProvider {

	QualifiedName qualifiedName(Class c){
		QualifiedName result = c.getName()!=null?QualifiedName.create(c.getName()):QualifiedName.create("");
		return result;
	}
}
