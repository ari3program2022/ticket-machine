package com.ari3program.ticketmachine.line.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "WAIT_LIST")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"status"})
public class WaitList {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;

	  @Column(name="store_id")
	  private Long store_id;

	  @Temporal(TemporalType.DATE)
	  @Column(name="reserve_date")
	  private Date reserve_date;

	  @Column(name="reserve_no")
	  private Long reserve_no;

	  @Column(name="amount")
	  private Long amount;

	  @Column(name="status")
	  private String status;

	  @Column(name="customer_id")
	  private String customer_id;

}
