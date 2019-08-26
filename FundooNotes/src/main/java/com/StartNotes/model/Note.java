package com.StartNotes.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;

@Entity
@Table(name = "notes")
@EntityListeners(AuditingEntityListener.class)
public class Note implements Serializable 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long noteid;
	
	private Long userid;
	
    @NotBlank
    private String title;
    @NotBlank
    private String content;


	public Long getNoteid() {
		return noteid;
	}

	public void setNoteid(Long noteid) {
		this.noteid = noteid;
	}
	public Long getuserid() {
		return userid;
	}

	public void setuserid(Long userid) {
		this.userid = userid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}