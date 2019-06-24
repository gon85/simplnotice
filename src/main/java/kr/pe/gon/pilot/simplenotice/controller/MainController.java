package kr.pe.gon.pilot.simplenotice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.pe.gon.pilot.simplenotice.domain.common.PageInfo;
import kr.pe.gon.pilot.simplenotice.domain.common.ResultResponse;
import kr.pe.gon.pilot.simplenotice.domain.notice.Notice;
import kr.pe.gon.pilot.simplenotice.service.notice.NoticeService;

@Controller
public class MainController {

	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping("/")
	public String main() {
		return "index";
	}

	@RequestMapping("/notice/new")
	@ResponseBody
	public ResultResponse noticeNew(PageInfo pageInfo) {
		ResultResponse result;
		try {
			result = new ResultResponse.Builder(ResultResponse.OK).msg("OK").build();
			result.getResults().put("notice", Notice.builder().build());
		} catch (Exception e) {
			result = new ResultResponse.Builder(ResultResponse.FAIL).msg("Fail").build();
		}
		return result;
	}
	
	@RequestMapping("/notice/list")
	@ResponseBody
	public ResultResponse noticeList(PageInfo pageInfo) {
		ResultResponse result;
		try {
			result = new ResultResponse.Builder(ResultResponse.OK).msg("OK").build();
			result.getResults().put("list", noticeService.getNotices(pageInfo));
		} catch (Exception e) {
			result = new ResultResponse.Builder(ResultResponse.FAIL).msg("Fail").build();
		}
		return result;
	}
	
	@RequestMapping("/notice/save")
	@ResponseBody
	public ResultResponse noticeSave(@RequestBody Notice nCond) {
		ResultResponse result;
		try {
			
			int r = noticeService.saveNotice(nCond);
			if (r == 1) {
				result = new ResultResponse.Builder(ResultResponse.OK).msg("OK").build();
			} else {
				result = new ResultResponse.Builder(ResultResponse.FAIL).msg("비밀번호 불일치").build();
			}
		} catch (Exception e) {
			result = new ResultResponse.Builder(ResultResponse.FAIL).msg("Fail").build();
		}
		return result;
	}
	
	@RequestMapping("/notice/remove")
	@ResponseBody
	public ResultResponse noticeRemove(@RequestParam("idx") int idx, @RequestParam("userPw") String userPw) {
		ResultResponse result;
		try {
			int r = noticeService.removeNoticeByIdx(idx, userPw);
			if (r == 1) {
				result = new ResultResponse.Builder(ResultResponse.OK).msg("OK").build();
			} else {
				result = new ResultResponse.Builder(ResultResponse.FAIL).msg("비밀번호 불일치").build();
				
			}
		} catch (Exception e) {
			result = new ResultResponse.Builder(ResultResponse.FAIL).msg("Fail").build();
		}
		return result;
	}
	
	@RequestMapping("/notice/confirmPw")
	@ResponseBody
	public ResultResponse noticeConfirmPw(@RequestParam("idx") int idx, @RequestParam("userPw") String userPw) {
		ResultResponse result;
		try {
			boolean confirm = noticeService.confirmPw(idx, userPw);
			if (confirm)
				result = new ResultResponse.Builder(ResultResponse.OK).msg("OK").build();
			else 
				result = new ResultResponse.Builder(ResultResponse.FAIL).msg("비밀번호 불일치").build();
			
		} catch (Exception e) {
			result = new ResultResponse.Builder(ResultResponse.FAIL).msg("Fail").build();
		}
		return result;
	}
	
	@RequestMapping("/notice/viewcount")
	@ResponseBody
	public ResultResponse noticeConfirmPw(@RequestParam("idx") int idx) {
		ResultResponse result;
		try {
			int viewcount = noticeService.increaseNoticeViewCount(idx);
			result = new ResultResponse.Builder(ResultResponse.OK).msg("OK").build();
			result.getResults().put("viewcount", viewcount);
		} catch (Exception e) {
			result = new ResultResponse.Builder(ResultResponse.FAIL).msg("Fail").build();
		}
		return result;
	}
}
