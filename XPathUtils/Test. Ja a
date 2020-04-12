import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.xml.xpath.NodeMapper;
import org.springframework.xml.xpath.XPathExpression;
import org.springframework.xml.xpath.XPathExpressionFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLUtils {

	public XPathExpression getXPath(String xpath) {
		XPathExpression titlesXp = null;

		try {
			titlesXp = XPathExpressionFactory.createXPathExpression(xpath);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return titlesXp;
	}

	public Document getDocument() {
		Document document = null;
		try {
			InputStream is = this.getClass().getResourceAsStream("input.xml");
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
		} catch (Throwable e) {
			System.out.println("XMLUtils.getDocument()" + e);
		}
		return document;
	}

	public void setPermissionIDentifiers(Distribution distribution) {
		XPathExpression titlesXp = getXPath(
				"/distributionDefinition/resourcePermissions/resourcePermission/permissionIdentifiers/permissionIdentifier");
		List<Distribution> result = titlesXp.evaluate(getDocument(), new NodeMapper<Distribution>() {
			public Distribution mapNode(Node node, int nodeIndex) throws DOMException {
				distribution.permissionIdentifierList.add(node.getTextContent().trim());
				return distribution;
			}
		});
	}

	public void getResourceIDentifiers(Distribution distribution) {
		XPathExpression titlesXp = getXPath(
				"/distributionDefinition/resourcePermissions/resourcePermission/resourceIdentifier");
		List<Distribution> output = titlesXp.evaluate(getDocument(), new NodeMapper<Distribution>() {
			public Distribution mapNode(Node node, int nodeIndex) throws DOMException {
				distribution.resourceIdentifierList.add(node.getTextContent().trim());
				return distribution;
			}
		});
	}

	public static void main(String args[]) {
		XMLUtils utils = new XMLUtils();
		Distribution distribution = new Distribution();
		utils.setPermissionIDentifiers(distribution);
		utils.getResourceIDentifiers(distribution);

		System.out.println("permissionIdentifierList:" + distribution.permissionIdentifierList);
		System.out.println("resourceIdentifierList:" + distribution.resourceIdentifierList);

	}
}

class Distribution {

	List<String> resourceIdentifierList = new ArrayList();
	List<String> permissionIdentifierList = new ArrayList();

	Distribution() {

	}
}
