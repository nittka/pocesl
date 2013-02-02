package org.xtext.example.mydsl.validation;

import org.eclipse.xtext.validation.Check;
import org.xtext.example.mydsl.myDsl.Class;
import org.xtext.example.mydsl.myDsl.Ref;
 

public class MyDslJavaValidator extends AbstractMyDslJavaValidator {

	@Check
	public void checkRef(Ref ref) {
		System.out.println(ref.getTo().getDisplayName());
	}

	@Check
	public void checkClass(Class c) {
//		System.out.println(c.getName());
	}

}
