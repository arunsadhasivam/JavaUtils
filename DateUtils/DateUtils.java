


private  Map<String, PriorityQueue<Person>> getEmailList() {
		log.debug("MarketoMergeUtils.getEmailList():BEGIN");
		Map<String, PriorityQueue<Person>> map = new HashMap<>();
		File file = new File("C:\\CR\\marketoDuplicates\\duplicateEmail.csv");
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
			log.error("MarketoMergeUtils.getEmailList()#FileNotFoundException:" ,e);
		} catch (IOException e) {
			log.error("MarketoMergeUtils.getEmailList()#IOException:" ,e);
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				log.error("MarketoMergeUtils.getEmailList()#IOException:" ,e);
			}
		}
		log.debug("MarketoMergeUtils.getEmailList():END");
		
		return map;
	}
