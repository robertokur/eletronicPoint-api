package com.robertokur.eletronicpoint.api.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.robertokur.eletronicpoint.api.enums.ProfileEnum;

@Entity
@Table(name = "employee")
public class Employee implements Serializable{
	public Employee() {
		
	}
	private static final long serialVersionUID = -5754246207015712518L;
	private Long id;
	private String name;
	private String email;
	private String password;
	private String cpf;
	private BigDecimal hourValue;
	private Float qtdHoursWorkDay;
	private Float qtdHoursLunch;
	private ProfileEnum profile;
	private Date createdDate;
	private Date updatedDate;
	private Company company;
	private List<Entry> entries;
	


	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "cpf", nullable = false)
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Column(name = "hour_value", nullable = true)
	public BigDecimal getHourValue() {
		return hourValue;
	}
	
	@Transient
	public Optional<BigDecimal> getHourValueOpt() {
		return Optional.ofNullable(hourValue);
	}

	public void sethourValue(BigDecimal hourValue) {
		this.hourValue = hourValue;
	}

	@Column(name = "qtd_hours_work_day", nullable = true)
	public Float getQtdHoursWorkDay() {
		return qtdHoursWorkDay;
	}
	
	@Transient
	public Optional<Float> getQtdHoursWorkDayOpt() {
		return Optional.ofNullable(qtdHoursWorkDay);
	}

	public void setQtdHorasWorkDay(Float qtdHoursWorkDay) {
		this.qtdHoursWorkDay = qtdHoursWorkDay;
	}

	@Column(name = "qtd_hours_lunch", nullable = true)
	public Float getQtdHoursLunch() {
		return qtdHoursLunch;
	}
	
	@Transient
	public Optional<Float> getQtdHoursLunchOpt() {
		return Optional.ofNullable(qtdHoursLunch);
	}

	public void setQtdHoursLunch(Float qtdHoursLunch) {
		this.qtdHoursLunch = qtdHoursLunch;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "profile", nullable = false)
	public ProfileEnum getProfile() {
		return profile;
	}

	public void setProfile(ProfileEnum profile) {
		this.profile = profile;
	}

	@Column(name = "createdDate", nullable = false)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "updatedDate", nullable = false)
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
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
		return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", cpf=" + cpf
				+ ", hourValue=" + hourValue+ ", qtdHoursWorkDay=" + qtdHoursWorkDay + ", qtdHoursLunch="
				+ qtdHoursLunch + ", profile=" + profile + ", createdDate="
				+ createdDate + ", updatedDate=" + updatedDate + ", Company=" + company + "]";
	}
}
