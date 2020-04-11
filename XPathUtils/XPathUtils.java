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

	public void setPermissionList(Company company) {
		XPathExpression titlesXp = getXPath(
				"/company/employees/roles/role/permission");
		List<Company> result = titlesXp.evaluate(getDocument(), new NodeMapper<Company>() {
			public Company mapNode(Node node, int nodeIndex) throws DOMException {
				company.permissionList.add(node.getTextContent().trim());
				return company;
			}
		});
	}

	public void getemployeeList(Company company) {
		XPathExpression titlesXp = getXPath(
				"/company/employees/roles/role");
		List<Company> output = titlesXp.evaluate(getDocument(), new NodeMapper<Company>() {
			public Company mapNode(Node node, int nodeIndex) throws DOMException {
				company.resourceIdentifierList.add(node.getTextContent().trim());
				return company;
			}
		});
	}

	public static void main(String args[]) {
		XMLUtils utils = new XMLUtils();
		Company company = new Company();
		company.setPermissionList(company);
		company.setEmployeeList(company);

		System.out.println("permissionList:" + distribution.permissionList);
		System.out.println("employeeList:" + distribution.employeeList);

	}
}

class Company {

	List<String> employeeList = new ArrayList();
	List<String> permissionList = new ArrayList();

	Distribution() {

	}
}
