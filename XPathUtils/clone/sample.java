public void cloneList() {
		ArrayList<Integer> list1 = new ArrayList<>();
		list1.add(1);
		list1.add(2);
		list1.add(3);
		ArrayList<Integer> list2 = new ArrayList<>();
		list2.add(2);
		
		ArrayList<Integer> list3= (ArrayList<Integer>)list1.clone();
		
		
		//1 2 3  | 2 | 1 2 3
		//1 2 3  | |1 2 3
		//
		list1.removeAll(list2);
		
		
		System.out.println("XMLUtils.cloneList()"+list1);
		
		
		
		
		
	}
