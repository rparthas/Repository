package com.tw.cashier;

import java.util.Date;

public class Story extends Task {
  
	String acceptanceCriteria;
	
	Date startDate;
	
	Date endDate;
	
	@Override
	public void close() {
	 System.out.println("Matches acceptance criteria");
	 this.status="END";
		
	}

}
