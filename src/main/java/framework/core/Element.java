package framework.core;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Element {
	public String elementName;
	public String elementValue;
	public String elementType;
	
	
	// web, native both
	public static final String XPATH="XPATH";
	public static final String CLASS="CLASS";
	public static final String NAME="NAME";
	//web-specific
	public static final String CSS="CSS";
	public static final String ID="ID";
	public static final String LINKTEXT="LINKTEXT";
	public static final String PARTIALLINKTEXT="PARTIALLINKTEXT";
	public static final String TAGNAME="TAGNAME";
	// native specific
	public static final String ACCESSIBILITYID="ACCESSIBILITYID";
	public static final String RUNTIMEID="RUNTIMEID";
	
	
	
	public Element(final String elementName, final String elementValue,final String elementType) {
		super();
		this.elementName=elementName;
		this.elementValue=elementValue;
		this.elementType=elementType;
	}
	
	public String getElementName() {
		return this.elementName;
	}
	public String getElementValue() {
		return this.elementValue;
	}
	public String getElementType() {
		return this.elementType;
	}
	
	
	private Element fixXpath(String elementValue) {
		Pattern replacePattern=Pattern.compile("[=,]'[^']*(['][\\w\\!@#$%^&*-;:.\342\u201E\242/]*)+'");
		Matcher replaceMatcher=replacePattern.matcher(elementValue);
		while(replaceMatcher.find()) {
			String matchValue=replaceMatcher.group();
			matchValue=matchValue.replace("='", "=\"");
			matchValue=matchValue.replace(",'", ",\"");
			matchValue=matchValue.substring(0, matchValue.length()-1)+"\"";
			elementValue=elementValue.replace(replaceMatcher.group(), matchValue);
		}
		return new Element(this.elementValue, elementValue, this.elementValue);
	}
	
	public Element format(final String substitution) {
		String elementValue=this.elementValue;
		final Pattern typePattern=Pattern.compile("[=,]''[^\\]]*''");
		final Matcher typeMatcher=typePattern.matcher(elementValue);
		if (typeMatcher.find()) {
			elementValue=MessageFormat.format(elementValue, substitution);
		}else {
			Pattern pattern = Pattern.compile("([{][0-9]+[}])");
			Matcher matcher=pattern.matcher(elementValue);
			if (matcher.find()) {
				pattern=Pattern.compile("([{]0[}])");
				matcher=pattern.matcher(elementValue);
				elementValue=matcher.replaceAll(substitution.toString());
			}
		}
		return this.fixXpath(elementValue);
	}
	
	
	public Element format(final Object... substitutions) {
		String elementValue=this.elementValue;
		Pattern typePattern=Pattern.compile("[=,]''[^\\]]*''");
		Matcher typeMatcher=typePattern.matcher(elementValue);
		if (typeMatcher.find()) {
			elementValue=MessageFormat.format(elementValue, substitutions);
		}else {
			Pattern pattern = Pattern.compile("([{][0-9]+[}])");
			Matcher matcher=pattern.matcher(elementValue);
			int count =0;
			while(matcher.find()) {
				count++;
			}
			for (int i = 0; i < count; ++i) {
				pattern=Pattern.compile("([{]" +i + "[}])");
				matcher=pattern.matcher(elementValue);
				elementValue=matcher.replaceAll(substitutions[i].toString());
			}
		}
		return this.fixXpath(elementValue);
	}
	
	
	

	
	

}
