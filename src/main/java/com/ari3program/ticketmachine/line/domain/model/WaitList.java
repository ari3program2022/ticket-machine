package com.ari3program.ticketmachine.line.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ari3program.ticketmachine.line.constant.WaitListStatus;

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
	  private int id;

	  @Column(name="store_id")
	  private int storeId;

	  @Temporal(TemporalType.DATE)
	  @Column(name="reserve_date")
	  private Date reserveDate;

	  @Column(name="reserve_no")
	  private int reserveNo;

	  @Column(name="amount")
	  private int amount;

	  @Enumerated(EnumType.STRING)
	  @Column(name="status")
	  private WaitListStatus status;

	  @Column(name="customer_id")
	  private String customerId;
	  
	  @Column(name="created_by")
	  private String createdBy;
	  
	  @Column(name="updated_by")
	  private String updatedBy;
	  

}
