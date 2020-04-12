import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class responsible for getting the values from the XML.
 * -DTO to pull values from xml and convert in to a lookup map. 
 */
public class XMLUtils {
	
	public static final String xml ="";

	/**
	 * To get the Document Object from the xml
	 * @param xml
	 * @return
	 */
	public Document getDocument(String xml) {
		Document document = null;
		try {
			InputStream is = this.getClass().getResourceAsStream("input.xml");
			//InputStream is1 = new ByteArrayInputStream(xml.getBytes());
			
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
		} catch (Throwable e) {
			System.out.println("XMLUtils.getDocument()" + e);
		}
		return document;
	}


	/**
	 * To process child Nodes
	 * @param key
	 * @param resultMap
	 * @param nodeList
	 */
	private void processChildNodes(String key,Map<String,List<String>> resultMap,NodeList nodeList) {
		
		if(nodeList.getLength()==0)return ;
		int pl = nodeList.getLength();
		
		List<String> list = new ArrayList<>(); 
		for(int j=0;j<pl;j++) {
			Node permissionNode = nodeList.item(j);
			if(permissionNode.getNodeType()== Node.ELEMENT_NODE) {
			  // System.out.println("\t\t"+permissionNode.getNodeName() +" ->" +permissionNode.getTextContent() +" -"+permissionNode.getNodeType());
				String value = permissionNode.getTextContent().trim();
				if(value.length()>0)
				list.add(value);
			}
			
		}
		
		resultMap.put(key, list);
	}
	
	/**
	 * 
	 * @param document
	 * @param documentRootTag
	 * @param delimiterTag - main Document root to split xml as collection
	 * @param keyTag
	 * @param valueTag
	 */
	private Map<String,Map<String,List<String>>> processDocument(Document document, String documentRootTag, String delimiterTag,String keyTag,String valueTag) {
		Map<String,Map<String,List<String>>> rootMap = new HashMap<>();
		Map<String,List<String>> resultMap = new HashMap<>();
			//reference to docRoot
		    NodeList  docRootList = document.getElementsByTagName(documentRootTag);
		    //reference to keyTag 
		    NodeList  keyTagList = document.getElementsByTagName(keyTag);
		    //reference to collection of values from valueTag.
		    NodeList  valueTagList = document.getElementsByTagName(valueTag);
		    
		    if(docRootList.getLength()==0) return rootMap;
			NamedNodeMap attr = docRootList.item(0).getAttributes();
			Node rootNode = attr.getNamedItem("id");
			
			//Step 1:root
			String rootId = rootNode.getNodeValue();
			
			//Step 2:delimit the whole document in to list of subdocument to split .
			NodeList resourcesNodeList = document.getElementsByTagName(delimiterTag);
			int n = resourcesNodeList.getLength();
			for(int i =0;i<n;i++) {
				Node resourceNode= keyTagList.item(i);
			
				String key = resourceNode.getTextContent();
//				System.out.println("key:"+key);
				//Step 3: get the key for the document collection index: 
				if(resourceNode.hasChildNodes()) {
					//Step 4: process subNode values as List
					NodeList permissionNodeList= valueTagList.item(i).getChildNodes();
					processChildNodes(key,resultMap,permissionNodeList);
				}
			}
			
			//Step 5: update the main RootMap to be returned.
			rootMap.put(rootId, resultMap);
		
			
			return rootMap;
	}
	
	public Map<String,Map<String,List<String>>> parseXML() {
		Document document = getDocument(xml);
		Map<String,Map<String,List<String>>> rootMap = processDocument(document, "Company","employee","employeeId", "experiences");
		print(rootMap );
		
		return rootMap;
	}

	
	public static void main(String args[]) {
		XMLUtils utils = new XMLUtils();
		utils.parseXML();
	}
	
	
	/**
	 * To test submap
	 * @param rootMap
	 */
	private void print(Map<String,Map<String,List<String>>> rootMap) {
		Set<String> st = rootMap.keySet();
		Iterator<String> it = st.iterator();
		while(it.hasNext()) {
			String key =it.next();
			System.out.println("docRoot#"+key);
			Map<String,List<String>> resultMap = rootMap.get(key);
			
			printSubMap(resultMap);
		}
		
	}
	
	/**
	 * To test
	 * @param rootMap
	 */
	private void printSubMap(Map<String,List<String>> resultMap) {
		Set<String> st = resultMap.keySet();
		Iterator<String> it = st.iterator();
		while(it.hasNext()) {
			String key = it.next();
			List<String> list =resultMap.get(key);
			System.out.println("\tResource:"+key );
			System.out.println("\t\t:"+list);
		}
	}
}
