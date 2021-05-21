package com.robertokur.eletronicpoint.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import com.robertokur.eletronicpoint.api.enums.TypeEnum;

@Entity
@Table(name = "entry")
public class Entry {

	public Entry() {

	}
	
	private static final long serialVersionUID = 6524560251526772839L;

	private Long id;
	private Date date;
	private String description;
	private String localization;
	private Date createdDate;
	private Date updatedDate;
	private TypeEnum type;
	private Employee employee;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date", nullable = false)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "description", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description= description;
	}
	
	@Column(name = "localization", nullable = true)
	public String getLocalization() {
		return localization;
	}

	public void setLocalization(String localization) {
		this.localization = localization;
	}

	@Column(name = "createdDate", nullable = false)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "updatedDate", nullable = false)
	public Date getDataAtualizacao() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	public TypeEnum getType() {
		return type;
	}

	public void setType(TypeEnum type) {
		this.type = type;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@PreUpdate
    public void preUpdate() {
        updatedDate = new Date();
    }
     
    @PrePersist
    public void prePersist() {
        final Date actual = new Date();
        createdDate = actual;
        updatedDate = actual;
    }

	@Override
	public String toString() {
		return "Entry [id=" + id + ", date=" + date + ", description=" + description + ", localization=" + localization
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", type=" + type
				+ ", employee=" + employee + "]";
	}

}
