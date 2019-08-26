package com.StartNotes.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;

@Entity
@Table(name = "labels")
@EntityListeners(AuditingEntityListener.class)
public class Label implements Serializable 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long labelid;
	
	private Long noteid;
	
    @NotBlank
    private String name;


	public Long getNoteid() {
		return noteid;
	}

	public void setNoteid(Long noteid) {
		this.noteid = noteid;
	
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLabelid() {
		return labelid;
	}

	public void setLabelid(Long labelid) {
		this.labelid = labelid;
	}

	
}