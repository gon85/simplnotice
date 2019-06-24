package kr.pe.gon.pilot.simplenotice.domain.notice;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@Table(name="PNB_NOTICE_TRX")
public class Notice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idx;
	private String title;
	private String userNickName;
	private String userPw;
	private String contents;
	private int viewCnt;
	@Column(nullable = false, insertable = false, updatable = false)
	@CreationTimestamp
	private Date createDt;
	@Column(nullable = false, insertable = false, updatable = true)
	@UpdateTimestamp
	private Date modifyDt;	
	
}
