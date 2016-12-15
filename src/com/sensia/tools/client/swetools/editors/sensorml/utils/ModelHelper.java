package com.sensia.tools.client.swetools.editors.sensorml.utils;

import java.util.ArrayList;
import java.util.List;

import com.sensia.relaxNG.RNGAttribute;
import com.sensia.relaxNG.RNGChoice;
import com.sensia.relaxNG.RNGDefine;
import com.sensia.relaxNG.RNGElement;
import com.sensia.relaxNG.RNGOptional;
import com.sensia.relaxNG.RNGRef;
import com.sensia.relaxNG.RNGTag;
import com.sensia.relaxNG.RNGTagList;
import com.sensia.relaxNG.RNGZeroOrMore;

public class ModelHelper {

	private ModelHelper(){}
	
	public static List<RNGElement> findTags(String namespace,String name, RNGTag tag) {
		List<RNGElement> results = new ArrayList<RNGElement> ();
		findRecursiveTags(namespace, name, tag, results);
		return results;
	}
	
	public static String findLabel(RNGElement tag) {
		String result = null;
		for(RNGTag child : tag.getChildren()) {
			if (child instanceof RNGElement) {
				String eltName = ((RNGElement) tag).getName();
				if(eltName.equals("label")) {
					result = ((RNGElement) child).getChildValueText();
					break;
				}
			}
		}
		
		// look for name attribute
		if(result == null) {
			RNGAttribute nameAtt = tag.getChildAttribute("name");
			if(nameAtt != null) {
				result = nameAtt.getChildValueText();
			}
		}
		return result;
	}
	
	private static void findRecursiveTags(String namespace,String name, RNGTag tag,List<RNGElement> results) {
		if (tag instanceof RNGElement) {
			String eltName = ((RNGElement) tag).getName();
			String nsUri = ((RNGElement) tag).getNamespace();
			if(nsUri.equals(namespace) && eltName.equals(name)) {
				results.add((RNGElement) tag);
			}
		} else if (tag instanceof RNGRef) {
			RNGDefine def = ((RNGRef) tag).getPattern();
			if (def != null)
				findRecursiveTags(namespace, name, def, results);
		} else if(tag instanceof RNGZeroOrMore) {
			List<List<RNGTag>> children0 = ((RNGZeroOrMore) tag).getPatternInstances();
			for(List<RNGTag> child0 : children0) {
				for(RNGTag child1 : child0) {
					findRecursiveTags(namespace, name, child1, results);
				}
			}
		} else if(tag instanceof RNGChoice && ((RNGChoice)tag).isSelected()) {
			findRecursiveTags(namespace, name, ((RNGChoice) tag).getSelectedPattern(), results);
		} else if(tag instanceof RNGOptional && ((RNGOptional)tag).isSelected()) {
			List<RNGTag> children = ((RNGOptional) tag).getChildren();
			for(RNGTag child : children) {
				findRecursiveTags(namespace, name, child, results);
			}
		}
		if(tag instanceof RNGTagList) {
			List<RNGTag> children = ((RNGTagList) tag).getChildren();
			for(RNGTag child : children) {
				findRecursiveTags(namespace, name, child, results);
			}
		}
	}
}
