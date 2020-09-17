package com.dbl.nsl.erp.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "datetimemodel")
public class DateTimeModel {
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long id;
	  
	    @Column  
	    @Temporal(TemporalType.DATE)
	    @JsonFormat(pattern="yyyy-MM-dd")
	    private Date date;
	    
	    @Column
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	    private Date datetime;
	    
	    @Column
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Paris")
	    private Date datetimewithzone;
	    
	    @Column
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date defaultformatdatetime;
	    
	    public DateTimeModel() {}
	    
	    public DateTimeModel(Date date, Date datetime, Date datetimewithzone, Date defaultformatdatetime) {
	      this.date = date;
	      this.datetime = datetime;
	      this.datetimewithzone = datetimewithzone;
	      this.defaultformatdatetime = defaultformatdatetime;
	    }
	    
	    // Getter/Setter date
	    public void setDate(Date date) {
	      this.date = date;
	    }
	    
	    public Date getDate() {
	      return this.date;
	    }
	    
	    // Getter/Setter datetime
	    public void setDatetime(Date datetime) {
	      this.datetime = datetime;
	    }
	    
	    public Date getDatetime() {
	      return this.datetime;
	    }
	    
	    // Getter/Setter datetimewithzone
	    public void setDatetimewithzone(Date datetimewithzone) {
	      this.datetimewithzone = datetimewithzone;
	    }
	    
	    public Date getDatetimewithzone() {
	      return this.datetimewithzone;
	    }
	    
	    // Getter/Setter defaultformatdatetime
	    public void setDefaultformatdatetime(Date defaultformatdatetime) {
	      this.defaultformatdatetime = defaultformatdatetime;
	    }
	    
	    public Date getDefaultformatdatetime() {
	      return this.defaultformatdatetime;
	    }

}
