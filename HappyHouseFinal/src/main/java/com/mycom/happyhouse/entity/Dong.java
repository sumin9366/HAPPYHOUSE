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
@Table(name="dong")
public class Dong {	//@Entity 로 인해, housedeael 테이블을 대변하는(매핑되는) 클래스
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)	//자동으로 증가하기위해 -> 하이버네이트가 설정해줘야함
	@Column(name="dong_code", columnDefinition="char", length=10, nullable=false, updatable=false)
	private String dongCode;
	
	@Column(name="dong_name", length=20, nullable=true, updatable=false)
	private String dongName;
	
	@ManyToOne
	@JoinColumn(name="sido_code")
	private Sido sido;
	
	@Column(name="sido_name", length=20, nullable=true)
	private String sidoName;
	
	@ManyToOne
	@JoinColumn(name="gugun_code")
	private Gugun gugun;
	
	@Column(name="gugun_name", length=20, nullable=true)
	private String gugunName;
	
}
