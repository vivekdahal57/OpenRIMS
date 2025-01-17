/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: DuKe TeAm
 * License Type: Purchased
 */
package org.msh.pdex2.model.dwh;

import java.io.Serializable;
import javax.persistence.*;
/**
 * follow up and registration events bound to a Data Module 
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="reportevent")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class ReportEvent implements Serializable {
	public ReportEvent() {
	}
	
	@Column(name="ID", nullable=false)	
	@Id	
	@GeneratedValue(generator="VAC2222721894FAF247001E62")	
	@org.hibernate.annotations.GenericGenerator(name="VAC2222721894FAF247001E62", strategy="native")	
	private long ID;
	
	@OneToOne(targetEntity=org.msh.pdex2.model.dwh.ReportSession.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="reportsessionID") })	
	@Basic(fetch=FetchType.LAZY)	
	private org.msh.pdex2.model.dwh.ReportSession reportSession;
	
	@Column(name="DataModuleId", nullable=false, length=20)	
	private long dataModuleId;
	
	@Column(name="Url", nullable=true, length=255)	
	private String url;
	
	@Column(name="EventDate", nullable=true)	
	private java.util.Date eventDate;
	
	@Column(name="NextEventDate", nullable=true)	
	private java.util.Date nextEventDate;
	
	@Column(name="EventNumber", nullable=true, length=255)	
	private String eventNumber;
	
	private void setID(long value) {
		this.ID = value;
	}
	
	public long getID() {
		return ID;
	}
	
	public long getORMID() {
		return getID();
	}
	
	/**
	 * The concept related to
	 */
	public void setDataModuleId(long value) {
		this.dataModuleId = value;
	}
	
	/**
	 * The concept related to
	 */
	public long getDataModuleId() {
		return dataModuleId;
	}
	
	/**
	 * The url of the event
	 */
	public void setUrl(String value) {
		this.url = value;
	}
	
	/**
	 * The url of the event
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Start of the event
	 */
	public void setEventDate(java.util.Date value) {
		this.eventDate = value;
	}
	
	/**
	 * Start of the event
	 */
	public java.util.Date getEventDate() {
		return eventDate;
	}
	
	/**
	 * Expiration or any other left side date
	 */
	public void setNextEventDate(java.util.Date value) {
		this.nextEventDate = value;
	}
	
	/**
	 * Expiration or any other left side date
	 */
	public java.util.Date getNextEventDate() {
		return nextEventDate;
	}
	
	/**
	 * Register number
	 */
	public void setEventNumber(String value) {
		this.eventNumber = value;
	}
	
	/**
	 * Register number
	 */
	public String getEventNumber() {
		return eventNumber;
	}
	
	public void setReportSession(org.msh.pdex2.model.dwh.ReportSession value) {
		this.reportSession = value;
	}
	
	public org.msh.pdex2.model.dwh.ReportSession getReportSession() {
		return reportSession;
	}
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
