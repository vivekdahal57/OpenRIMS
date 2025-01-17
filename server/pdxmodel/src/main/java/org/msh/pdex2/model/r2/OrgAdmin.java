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
package org.msh.pdex2.model.r2;

import java.io.Serializable;
import javax.persistence.*;
/**
 * Admin units responsibility for an organization
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="orgadmin")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class OrgAdmin implements Serializable {
	public OrgAdmin() {
	}
	
	@Column(name="ID", nullable=false)	
	@Id	
	@GeneratedValue(generator="VAC2222721894FAF246901E5B")	
	@org.hibernate.annotations.GenericGenerator(name="VAC2222721894FAF246901E5B", strategy="native")	
	private long ID;
	
	@OneToOne(targetEntity=org.msh.pdex2.model.r2.Concept.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="conceptID") })	
	@Basic(fetch=FetchType.LAZY)	
	private org.msh.pdex2.model.r2.Concept adminUnit;
	
	private void setID(long value) {
		this.ID = value;
	}
	
	public long getID() {
		return ID;
	}
	
	public long getORMID() {
		return getID();
	}
	
	public void setAdminUnit(org.msh.pdex2.model.r2.Concept value) {
		this.adminUnit = value;
	}
	
	public org.msh.pdex2.model.r2.Concept getAdminUnit() {
		return adminUnit;
	}
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
