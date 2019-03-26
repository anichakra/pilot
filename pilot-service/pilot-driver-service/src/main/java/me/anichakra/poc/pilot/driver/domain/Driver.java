package me.anichakra.poc.pilot.driver.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class Driver implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private Date licenseExpiryDate;
	private int yearOfLicense;
	@OneToOne(fetch=FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private Category category;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLicenseExpiryDate() {
		return licenseExpiryDate;
	}

	public void setLicenseExpiryDate(Date licenseExpiryDate) {
		this.licenseExpiryDate = licenseExpiryDate;
	}

	public int getYearOfLicense() {
		return yearOfLicense;
	}

	public void setYearOfLicense(int yearOfLicense) {
		this.yearOfLicense = yearOfLicense;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		ObjectMapper mapperObj = new ObjectMapper();
		try {
			return mapperObj.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return "id:" + id;
		}

	}
}
