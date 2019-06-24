package kr.pe.gon.pilot.simplenotice.service.notice;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kr.pe.gon.pilot.simplenotice.domain.common.ListResult;
import kr.pe.gon.pilot.simplenotice.domain.common.PageInfo;
import kr.pe.gon.pilot.simplenotice.domain.notice.Notice;
import kr.pe.gon.pilot.simplenotice.repository.notice.NoticeRepository;
import kr.pe.gon.pilot.simplenotice.utils.SecurityUtil;

@Service
public class NoticeServiceImpl implements NoticeService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NoticeServiceImpl.class);
	
	@Autowired
	private NoticeRepository noticeRepository;
	
//	@Override
//	public Notice addNotice(Notice nCond) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Notice modifyNotice(Notice nCond) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Transactional
	@Override
	public int saveNotice(Notice nCond) {
		
		if (nCond.getIdx() <= 0) {
			nCond.setUserPw(SecurityUtil.encryptSHA256(nCond.getUserPw()));
			noticeRepository.save(nCond);
			return 1;
		} else {
			if (this.confirmPw(nCond.getIdx(), nCond.getUserPw())) {
				nCond.setUserPw(SecurityUtil.encryptSHA256(nCond.getUserPw()));
				noticeRepository.save(nCond);
				return 1;
			} else {
				return -1;
			}
		}
	}

	@Transactional
	@Override
	public int removeNoticeByIdx(int idx, String userPw) {
		if (this.confirmPw(idx, userPw)) {
			noticeRepository.deleteByIdx(idx);
			return 1;
		} else {
			return -1;
		}
	}

	@Transactional
	@Override
	public int increaseNoticeViewCount(int idx) {
		noticeRepository.updateIncreaseCount(idx);
		Notice n =noticeRepository.findByIdx(idx);
		return n.getViewCnt();
	}

	@Override
	public ListResult<Notice> getNotices(PageInfo pageInfo) {
		
		Page<Notice> page = noticeRepository.findAll(PageRequest.of(pageInfo.getCurrentPage() - 1, pageInfo.getRowPerPage(), Sort.Direction.DESC, "idx"));
		
		ListResult<Notice> lstResult = new ListResult<Notice>();
		lstResult.setTotalCount(page.getTotalElements());
		lstResult.setList(page.getContent());
		return lstResult;
	}

	@Override
	public boolean confirmPw(int idx, String pw) {
		
		Notice notice = noticeRepository.findByIdx(idx);
		
		String encryptStr = SecurityUtil.encryptSHA256(pw);
		return notice.getUserPw().equals(encryptStr);
	}

}
