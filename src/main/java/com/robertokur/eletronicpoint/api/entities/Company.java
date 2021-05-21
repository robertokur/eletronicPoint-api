package com.robertokur.eletronicpoint.api.entities;

import java.io.Serializable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company implements Serializable{
private static final long serialVersionUID = 3960436649365666213L;
	
	private Long id;
	private String companyName;
	private String cnpj;
	private Date createdDate;
	private Date updatedDate;
	private List<Employee> employees;
	
	public Company() {
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "company_name", nullable = false)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "cnpj", nullable = false)
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Column(name = "created_date", nullable = false)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "updated_date", nullable = false)
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setDataAtualizacao(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Employee> getEmployees() {
		return employees;
	}

	public void setFuncionarios(List<Employee> employees) {
		this.employees = employees;
	}
	
	@PreUpdate
    public void preUpdate() {
        updatedDate = new Date();
    }
     
    @PrePersist
    public void prePersist() {
        final Date atual = new Date();
        createdDate = atual;
        updatedDate = atual;
    }

	@Override
	public String toString() {
		return "Company [id=" + id + ", companyName=" + companyName + ", cnpj=" + cnpj + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + "]";
	}
}
