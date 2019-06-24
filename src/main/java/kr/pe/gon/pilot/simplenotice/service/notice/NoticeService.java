package kr.pe.gon.pilot.simplenotice.service.notice;

import kr.pe.gon.pilot.simplenotice.domain.common.ListResult;
import kr.pe.gon.pilot.simplenotice.domain.common.PageInfo;
import kr.pe.gon.pilot.simplenotice.domain.notice.Notice;

public interface NoticeService {

//	public Notice addNotice(Notice nCond);
//	public Notice modifyNotice(Notice nCond);
	
	/**
	 * 
	 * @param nCond
	 * @return 1: 정상, -1:비밀번호 불일치(수정일경우)
	 */
	public int saveNotice(Notice nCond);
	
	/**
	 * 
	 * @param idx
	 * @return 1:정상, -1:비밀번호 불일치
	 */
	public int removeNoticeByIdx(int idx, String userPw);
	
	public int increaseNoticeViewCount(int idx);
	
	public ListResult<Notice> getNotices(PageInfo pageInfo );
	
	public boolean confirmPw(int idx, String pw);
	
}
