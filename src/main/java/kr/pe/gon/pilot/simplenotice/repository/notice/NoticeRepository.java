package kr.pe.gon.pilot.simplenotice.repository.notice;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import kr.pe.gon.pilot.simplenotice.domain.notice.Notice;

public interface NoticeRepository extends PagingAndSortingRepository<Notice, Long> { //extends CrudRepository<Notice, Long> {

	public List<Notice> findAll();
	
//	@Transactional
	@Modifying
	@Query("UPDATE Notice pnt SET pnt.viewCnt = pnt.viewCnt + 1 WHERE pnt.idx = :idx")
	public void updateIncreaseCount(@Param("idx") int idx);
	
	public Notice findByIdx(int idx);
	
	public void deleteByIdx(int idx);
	
}
