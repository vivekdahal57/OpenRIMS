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
 * Auxiliary table to collect workflow data for KPI
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="dwhworkflows")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class dwhworkflows implements Serializable {
	public dwhworkflows() {
	}
	
	@Column(name="ID", nullable=false)	
	@Id	
	@GeneratedValue(generator="VAC222277187B38344500A60D")	
	@org.hibernate.annotations.GenericGenerator(name="VAC222277187B38344500A60D", strategy="native")	
	private long ID;
	
	@OneToOne(targetEntity=org.msh.pdex2.model.dwh.ReportSession.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="reportsessionID") })	
	@Basic(fetch=FetchType.LAZY)	
	private org.msh.pdex2.model.dwh.ReportSession reportSession;
	
	@Column(name="Workflow", nullable=true, length=255)	
	private String workflow;
	
	@Column(name="Come", nullable=true)	
	private java.util.Date come;
	
	@Column(name="Go", nullable=true)	
	private java.util.Date go;
	
	@Column(name="Lang", nullable=true, length=255)	
	private String lang;
	
	@Column(name="ApplicationID", nullable=true, length=20)	
	private long applicationID;
	
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
	 * Name of the workflow as defined in the dictionary
	 */
	public void setWorkflow(String value) {
		this.workflow = value;
	}
	
	/**
	 * Name of the workflow as defined in the dictionary
	 */
	public String getWorkflow() {
		return workflow;
	}
	
	/**
	 * When this workflow has been started
	 */
	public void setCome(java.util.Date value) {
		this.come = value;
	}
	
	/**
	 * When this workflow has been started
	 */
	public java.util.Date getCome() {
		return come;
	}
	
	/**
	 * When this workflow has been finished, if not finished yet - now()
	 */
	public void setGo(java.util.Date value) {
		this.go = value;
	}
	
	/**
	 * When this workflow has been finished, if not finished yet - now()
	 */
	public java.util.Date getGo() {
		return go;
	}
	
	/**
	 * A language that is using for workflow name
	 */
	public void setLang(String value) {
		this.lang = value;
	}
	
	/**
	 * A language that is using for workflow name
	 */
	public String getLang() {
		return lang;
	}
	
	/**
	 * Application ID from the history to link
	 */
	public void setApplicationID(long value) {
		this.applicationID = value;
	}
	
	/**
	 * Application ID from the history to link
	 */
	public long getApplicationID() {
		return applicationID;
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
