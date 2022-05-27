package com.ari3program.ticketmachine.line.domain.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaxReserveNo {
	
	private int storeId;
	
	private Date reserveDate;
	
	private int sum;

}
