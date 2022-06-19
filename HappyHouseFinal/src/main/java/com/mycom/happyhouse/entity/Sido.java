package com.mycom.happyhouse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="sido")
public class Sido {
	@Id
	@Column(name="sido_code", columnDefinition="char", length=2, nullable=false, updatable=false)
	private String sidoCode;
	
	@Column(name="sido_name", length=20, nullable=true, updatable=false)
	private String sidoName;
}
