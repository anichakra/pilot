package me.anichakra.poc.pilot.driver.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category implements Serializable {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((segment == null) ? 0 : segment.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (segment == null) {
			if (other.segment != null)
				return false;
		} else if (!segment.equals(other.segment))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Category [segment=" + segment + ", type=" + type + "]";
	}
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String segment;
    private String type;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    public String getSegment() {
        return segment;
    }
    public void setSegment(String segment) {
        this.segment = segment;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
