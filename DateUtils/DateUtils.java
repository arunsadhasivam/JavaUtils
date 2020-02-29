
class Utils{
private   PriorityQueue<Person>  getQueue(){
		log.debug("Utils.getQueue():BEGIN");
		
		PriorityQueue<Person> list = new PriorityQueue<Person>(100,new Comparator<Person>() {
					public int compare(Person a, Person b) {
						SimpleDateFormat sf = new SimpleDateFormat(
								"MM/dd/yyyy HH:mm");
						
						//important MM should be caps and HH shouldbe caps
						//otherwise
						
						//15/29/2019 2:3 5/29/2019 2:3 in this case it fails if MM is not caps
						//it considers 5/29/2019 ahead of 15
						//15/29/2019 2:3 15/29/2019 21:3  in this case if not correct format
						//i.e both date one with 02  hours    and other with 21 hrs 
						//it considers 15/29/2019 2:3 higher 
						Date d1 = null, d2 = null;
						try {
							d1 = sf.parse(a.updatedDate);
							d2 = sf.parse(b.updatedDate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						return d2.compareTo(d1);
					}
		});
		log.debug("MarketoMergeUtils.getQueue():END");
		
		return list;
	}
private  Map<String, PriorityQueue<Person>> getEmailList() {
		log.debug("MarketoMergeUtils.getEmailList():BEGIN");
		Map<String, PriorityQueue<Person>> map = new HashMap<>();
		File file = new File("C:\\CR\\Test\\duplicateEmail.csv");
		BufferedReader bis = null;
		String line = "";
		InputStreamReader is = null;
		
		try {
			is = new InputStreamReader(new FileInputStream(file));
			bis = new BufferedReader(is);

			boolean isFirstRecord = true;
			while ((line = bis.readLine()) != null ) {
				if (isFirstRecord) {
					isFirstRecord = false;
					continue;
				}
				String arr[] = line.split(",");
				String id = arr[0];
				String email = arr[1];
				String createdDate = arr[2];
				String updatedDate = arr[3];
				if (map.get(email) == null) {
					PriorityQueue<Person>  queue = getQueue();
					queue.offer(new Person(id,email, createdDate, updatedDate));
					map.put(email, queue);
				} else {
					PriorityQueue<Person>  queue = map.get(email);
					queue.offer(new Person(id,email, createdDate, updatedDate));
				}
			}

		} catch (FileNotFoundException e) {
			log.error("Utils.getEmailList()#FileNotFoundException:" ,e);
		} catch (IOException e) {
			log.error("Utils.getEmailList()#IOException:" ,e);
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				log.error("Utils.getEmailList()#IOException:" ,e);
			}
		}
		log.debug("Utils.getEmailList():END");
		
		return map;
	}
}
