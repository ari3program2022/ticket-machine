package com.ari3program.ticketmachine.line.domain.service.waitlist;

public class WaitListFoundException  extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public WaitListFoundException(String message) {
		super(message);
	}

}
