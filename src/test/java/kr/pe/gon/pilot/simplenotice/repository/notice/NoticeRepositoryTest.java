package kr.pe.gon.pilot.simplenotice.repository.notice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import kr.pe.gon.pilot.simplenotice.domain.notice.Notice;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeRepositoryTest {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeRepositoryTest.class);
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	@Test
	public void findAllTest() {
		
		List<Notice> lst = noticeRepository.findAll();
		assertTrue(lst.size()>=0);
		long count = noticeRepository.count();
		// assertEquals(lst.size(), noticeRepository.count());
		
		noticeRepository.save(
				Notice.builder()
				.title("title")
				.userNickName("nick")
				.userPw("pw")
				.contents("jpa test~~~~")
				.build());
		
		List<Notice> lst2 = noticeRepository.findAll();
		assertEquals(count + 1, lst2.size());
		
		Notice n= lst2.get(lst2.size() - 1);
		n.setContents("update jpa test !!!!!!!!!");
		
		noticeRepository.save(n);
		
		lst2 = noticeRepository.findAll();
		Notice n2= lst2.get(lst2.size() - 1);
		assertEquals("update jpa test !!!!!!!!!", n2.getContents());
		
		assertNotEquals(n.getModifyDt(), n2.getModifyDt());
		logger.debug("---->" + n.getModifyDt());
		logger.debug("---->" + n2.getModifyDt());
	}
	
	@Test
	public void findPagableTest() {
		// List<Notice> lst = noticeRepository.findAll(new PageRanges(1, 2));
		
		long count = noticeRepository.count();
		int pageSize = 5;
		Page<Notice> page = noticeRepository.findAll(PageRequest.of(0, pageSize, Sort.Direction.DESC, "idx"));
		List<Notice> lst = page.getContent();
		assertEquals(pageSize, lst.size());
		assertEquals((int)(count / pageSize) + 1, page.getTotalPages());
		
		assertEquals(page.getTotalElements(), lst.get(0).getIdx());
	}
	
//	@Test
//	public void updateIncreaseCountTest() {
//		List<Notice> lst = noticeRepository.findAll();
//		Notice n1 = lst.get(0);
//		
//		noticeRepository.updateIncreaseCount(n1.getIdx());
//			
//		List<Notice> lst2 = noticeRepository.findAll();
//		logger.debug("------------->" + n1.getIdx() + " / " + n1.getViewCnt());
//		logger.debug("------------->" + lst2.get(0).getIdx() + " / " + lst2.get(0).getViewCnt());
//		
//		assertEquals(n1.getViewCnt() + 1, lst2.get(0).getViewCnt());
//	}
	
}
