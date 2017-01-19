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
	
	public static List<RNGElement> findTags(String namespace,String name, RNGTag tag,int maxLevel) {
		List<RNGElement> results = new ArrayList<RNGElement> ();
		findRecursiveTags(namespace, name, tag, results,0,maxLevel);
		return results;
	}
	
	public static List<RNGElement> findTags(String namespace,String name, RNGTag tag) {
		List<RNGElement> results = new ArrayList<RNGElement> ();
		findRecursiveTags(namespace, name, tag, results);
		return results;
	}
	
	public static boolean isAttributeValue(String value, RNGTag tag) {
		return findRecursiveAttributeValue(value, tag);
	}
	
	public static RNGAttribute findAttributeValue(String attributeName, RNGTag tag) {
		return findRecursiveAttribute(attributeName, tag);
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
			if(nsUri.equalsIgnoreCase(namespace) && eltName.equalsIgnoreCase(name)) {
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
	
	private static void findRecursiveTags(String namespace,String name, RNGTag tag,List<RNGElement> results,int currentLevel,int maxLevel) {
		if(currentLevel >= maxLevel) {
			return;
		}
		
		if (tag instanceof RNGElement) {
			String eltName = ((RNGElement) tag).getName();
			String nsUri = ((RNGElement) tag).getNamespace();
			if(nsUri.equalsIgnoreCase(namespace) && eltName.equalsIgnoreCase(name)) {
				results.add((RNGElement) tag);
			}
		} else if (tag instanceof RNGRef) {
			RNGDefine def = ((RNGRef) tag).getPattern();
			if (def != null)
				findRecursiveTags(namespace, name, def, results,currentLevel++,maxLevel);
		} else if(tag instanceof RNGZeroOrMore) {
			List<List<RNGTag>> children0 = ((RNGZeroOrMore) tag).getPatternInstances();
			for(List<RNGTag> child0 : children0) {
				for(RNGTag child1 : child0) {
					findRecursiveTags(namespace, name, child1, results,currentLevel++,maxLevel);
				}
			}
		} else if(tag instanceof RNGChoice && ((RNGChoice)tag).isSelected()) {
			findRecursiveTags(namespace, name, ((RNGChoice) tag).getSelectedPattern(), results);
		} else if(tag instanceof RNGOptional && ((RNGOptional)tag).isSelected()) {
			List<RNGTag> children = ((RNGOptional) tag).getChildren();
			for(RNGTag child : children) {
				findRecursiveTags(namespace, name, child, results,currentLevel++,maxLevel);
			}
		}
		if(tag instanceof RNGTagList) {
			List<RNGTag> children = ((RNGTagList) tag).getChildren();
			for(RNGTag child : children) {
				findRecursiveTags(namespace, name, child, results,currentLevel++,maxLevel);
			}
		}
	}
	
	private static boolean findRecursiveAttributeValue(String value, RNGTag tag) {
		 boolean found = false;
		 if (tag instanceof RNGAttribute) {
			 RNGAttribute att = (RNGAttribute) tag;
			 return att.getChildValueText().equalsIgnoreCase(value);
		} else if (tag instanceof RNGRef) {
			RNGDefine def = ((RNGRef) tag).getPattern();
			if (def != null)
				return findRecursiveAttributeValue(value, def);
		} else if(tag instanceof RNGZeroOrMore) {
			List<List<RNGTag>> children0 = ((RNGZeroOrMore) tag).getPatternInstances();
			for(List<RNGTag> child0 : children0) {
				for(RNGTag child1 : child0) {
					if(found=findRecursiveAttributeValue(value, child1)) {
						break;
					}
				}
			}
		} else if(tag instanceof RNGChoice && ((RNGChoice)tag).isSelected()) {
			return findRecursiveAttributeValue(value, ((RNGChoice) tag).getSelectedPattern());
		} else if(tag instanceof RNGOptional && ((RNGOptional)tag).isSelected()) {
			List<RNGTag> children = ((RNGOptional) tag).getChildren();
			for(RNGTag child : children) {
				if(found=findRecursiveAttributeValue(value, child)) {
					break;
				}
			}
		} else if(tag instanceof RNGTagList) {
			List<RNGTag> children = ((RNGTagList) tag).getChildren();
			for(RNGTag child : children) {
				if(found=findRecursiveAttributeValue(value, child)) {
					break;
				}
			}
		}
		return found;
	}
	
	private static RNGAttribute findRecursiveAttribute(String attName, RNGTag tag) {
		 RNGAttribute res = null;
		 if (tag instanceof RNGAttribute) {
			 RNGAttribute att = (RNGAttribute) tag;
			 if(att.getName().equals(attName)) {
				 res = att; 
			 }
		} else if (tag instanceof RNGRef) {
			RNGDefine def = ((RNGRef) tag).getPattern();
			if (def != null)
				return findRecursiveAttribute(attName, def);
		} else if(tag instanceof RNGZeroOrMore) {
			List<List<RNGTag>> children0 = ((RNGZeroOrMore) tag).getPatternInstances();
			for(List<RNGTag> child0 : children0) {
				for(RNGTag child1 : child0) {
					if((res=findRecursiveAttribute(attName, child1)) != null) {
						break;
					}
				}
			}
		} else if(tag instanceof RNGChoice && ((RNGChoice)tag).isSelected()) {
			return findRecursiveAttribute(attName, ((RNGChoice) tag).getSelectedPattern());
		} else if(tag instanceof RNGOptional && ((RNGOptional)tag).isSelected()) {
			List<RNGTag> children = ((RNGOptional) tag).getChildren();
			for(RNGTag child : children) {
				if((res=findRecursiveAttribute(attName, child)) != null) {
					break;
				}
			}
		} else if(tag instanceof RNGTagList) {
			List<RNGTag> children = ((RNGTagList) tag).getChildren();
			for(RNGTag child : children) {
				if((res=findRecursiveAttribute(attName, child)) != null) {
					break;
				}
			}
		}
		return res;
	}
}
