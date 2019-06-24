package kr.pe.gon.pilot.simplenotice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.pe.gon.pilot.simplenotice.domain.common.ListResult;
import kr.pe.gon.pilot.simplenotice.domain.common.PageInfo;
import kr.pe.gon.pilot.simplenotice.domain.notice.Notice;
import kr.pe.gon.pilot.simplenotice.service.notice.NoticeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeServiceTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private NoticeService noticeService;
	
	@Test
	public void getNoticesTest() {
		logger.debug("----> getNotice start~~");
		
//		logger.debug("----> " + SecurityUtil.encryptSHA256("abc"));
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(1);
		pageInfo.setRowPerPage(5);
		
		ListResult<Notice> lr = noticeService.getNotices(pageInfo);
		
		assertEquals(26, lr.getTotalCount());
		assertEquals(5, lr.getList().size());
	}
	
	@Test
	public void saveNoticeTest() {
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(1);
		pageInfo.setRowPerPage(5);
		
		ListResult<Notice> lr = noticeService.getNotices(pageInfo);
		Notice nFirst = lr.getList().get(0);
		
		nFirst.setTitle("modify");
		noticeService.saveNotice(nFirst);

		noticeService.saveNotice(Notice.builder()
				.title("title xxxx")
				.userNickName("nick xxx")
				.userPw("pw xxx")
				.contents("jpa test~~~~")
				.build());
		
		pageInfo = new PageInfo();
		pageInfo.setCurrentPage(1);
		pageInfo.setRowPerPage(5);
		
		ListResult<Notice> lr2 = noticeService.getNotices(pageInfo);
		assertEquals(lr.getTotalCount() + 1, lr2.getTotalCount());
		assertEquals("modify", lr2.getList().get(1).getTitle());
		
	}
	
	@Test
	public void confirmPwTest() {
		
		int idx = 26;
		String pw = "XXX";
		
		assertTrue(noticeService.confirmPw(idx, pw));
		assertFalse(noticeService.confirmPw(idx, "XDA"));
	}
}
