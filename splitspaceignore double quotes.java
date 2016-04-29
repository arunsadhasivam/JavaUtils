public class Test {

	public static void main(String args[]) {
		String line = "1000, 10,  \"value header\", space ";
		
		String arr[] = line.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
		System.out.println(arr.length);
		
		String result[] = line.replaceAll("^\"", "").split("\"?(,|$)(?=(([^\"]*\"){2})*[^\"]*$) *\"?", -1);
		for (String token : result) {
			System.out.println("Test.main()" + token);
		}

	}
}
