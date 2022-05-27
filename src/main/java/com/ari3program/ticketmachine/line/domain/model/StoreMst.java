package com.ari3program.ticketmachine.line.domain.model;

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

@Table(name = "STORE_MST")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"name", "channelId"})
public class StoreMst {
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private int id;
	
	@Column(name="name")
	  private String name;
	
	@Temporal(TemporalType.TIME)
	@Column(name="open_time")
	  private Date openTime;
	
	@Temporal(TemporalType.TIME)
	@Column(name="close_time")
	  private Date closeTime;
	
	@Column(name="channel_id")
	  private String channelId;

}
