package com.bookstore.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="configuration")
public class Configuration {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	  private Integer cid;
	  private String property;
	  private String val;
	  private boolean active;
	  
	public Integer getCid() {
		return cid;
	}
	public String getProperty() {
		return property;
	}
	public String getVal() {
		return val;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

}
