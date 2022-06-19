package com.mycom.happyhouse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="gugun")
public class Gugun {
	@Id
	@Column(name="gugun_code", columnDefinition="char", length=5, nullable=false, updatable=false)
	private String gugunCode;
	
	@Column(name="gugun_name", length=20, nullable=true, updatable=false)
	private String gugunName;
	
	@ManyToOne
	@JoinColumn(name="sido_code")
	private Sido sido;
	
}
