
package com.recomedi.myapp.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.recomedi.myapp.domain.BoardVo;
import com.recomedi.myapp.domain.PageMaker;
import com.recomedi.myapp.domain.SearchCriteria;
import com.recomedi.myapp.service.BoardService;
import com.recomedi.myapp.util.UserIp;

@Controller
@RequestMapping(value = "/notice/")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired(required = false)
	private BoardService boardService;

	@Autowired(required = false)
	private PageMaker pm;

	@Autowired(required = false)
	private UserIp userip;

	@RequestMapping(value = "noticeList.do")
	public String noticeList(SearchCriteria scri, Model model) {
		// 배경 설정만 하고 데이터를 전달하지 않음

		// 제목 검색 필터링 //?
		if (scri.getTitle() != null && !scri.getTitle().trim().isEmpty()) {
			scri.setSearchType("title");
		} else {
			scri.setSearchType(null); // 검색 조건이 없을 경우 null로 설정
		}

		// 게시물 총 개수
		int cnt = boardService.boardTotalCount(scri);

		// 페이지 정보 설정
		pm.setScri(scri);
		pm.setTotalCount(cnt);

		// 제목으로 검색된 게시물 목록
		ArrayList<BoardVo> blist = boardService.boardSelectAll(scri);

		// 게시물이 없을 경우
		if (blist.isEmpty()) {
			model.addAttribute("noResultMessage", "검색 결과가 없습니다.");
		}

		// 결과를 모델에 추가
		model.addAttribute("blist", blist);
		model.addAttribute("pm", pm);

		// 디버깅 코드
		System.out.println("scri : " + scri);// 디버킹코드 : 값있음
		System.out.println("Received title: " + scri.getTitle());// 디버킹코드 : 값은 검색을 하면 나옴

		// 반환
		String path = "WEB-INF/notice/noticeList";
		return path;
	}

	@RequestMapping(value = "noticeWrite.do")
	public String noticeWrite() {

		String path = "WEB-INF/notice/noticeWrite";
		return path;
	}

	@RequestMapping(value = "noticeWriteAction.do")
	public String noticeWriteAction(BoardVo bv, HttpServletRequest request, RedirectAttributes rttr) throws Exception {
		// 로깅 추가
		System.out.println("boardWriteAction 메서드 시작");

		// null 체크 추가
		if (request == null || request.getSession() == null) {
			System.out.println("Error: request 또는 session이 null입니다.");
			rttr.addFlashAttribute("msg", "서버 오류가 발생했습니다.");
			return "redirect:/error.do";
		}

		// 세션에서 midx 추출
		Object midxObj = request.getSession().getAttribute("midx");
		if (midxObj == null) {
			System.out.println("Error: 세션에 midx가 없습니다.");
			rttr.addFlashAttribute("msg", "로그인이 필요합니다.");
			return "redirect:/member/memberLogin.do"; // 수정된 경로
		}

		String midx = midxObj.toString();
		System.out.println("midx: " + midx);

		int midx_int = Integer.parseInt(midx);

		String ip = userip.getUserIp(request);
		System.out.println("IP: " + ip);

		// BoardVo에 midx와 ip 설정
		bv.setMidx(midx_int);
		bv.setIp(ip);
		bv.setState("0");

		// BoardVo 내용 출력
		System.out.println("BoardVo 내용: " + bv.toString());

		// null 체크 추가
		if (boardService == null) {
			System.out.println("Error: boardService가 null입니다.");
			rttr.addFlashAttribute("msg", "서버 오류가 발생했습니다.");
			return "redirect:/error.do";
		}

		// DB에 글 저장
		int value = boardService.boardInsert(bv);
		System.out.println("boardInsert 결과: " + value);

		String path = "";
		if (value == 1) {
			System.out.println("글 저장 성공");
			path = "redirect:/notice/noticeList.do";
		} else {
			System.out.println("글 저장 실패");
			rttr.addFlashAttribute("msg", "입력이 잘못되었습니다");
			path = "redirect:/notice/noticeWrite.do";
		}

		System.out.println("리턴 경로: " + path);
		return path; // 리디렉션 경로 반환
	}

	// 삭제 처리
	@RequestMapping(value = "/noticeDelete.do", method = RequestMethod.GET)
	public String noticeDelete(@RequestParam("bidx") int bidx, RedirectAttributes rttr) {
		int result = boardService.boardDelete(bidx);

		if (result > 0) {
			rttr.addFlashAttribute("msg", "게시글이 삭제되었습니다.");
		} else {
			rttr.addFlashAttribute("msg", "삭제에 실패했습니다.");
		}

		return "redirect:/notice/noticeList.do";
	}

	@RequestMapping(value = "noticeDetail.do")
	public String noticeDetail(@RequestParam("bidx") int bidx, Model model) {
		boardService.boardViewCntUpdate(bidx);
		BoardVo bv = boardService.boardSelectOne(bidx);

		model.addAttribute("bv", bv);
		model.addAttribute("bidx", bidx); // bidx를 명시적으로 모델에 추가

		// 디버깅을 위한 로그 추가
//		System.out.println("Controller - bidx: " + bidx);
//		System.out.println("Controller - BoardVo: " + bv);

		return "WEB-INF/notice/noticeDetail";
	}

	@RequestMapping(value = "noticeModify.do")
	public String noticeModify(@RequestParam("bidx") int bidx, Model model) {

		BoardVo bv = boardService.boardSelectOne(bidx);
		model.addAttribute("bv", bv);// 왜 이걸 bv로 변경했을까?

		String path = "WEB-INF/notice/noticeModify";
		return path;
	}

	@RequestMapping(value = "noticeModifyAction.do")
	public String noticeModifyAction(@ModelAttribute BoardVo bv, HttpServletRequest request, RedirectAttributes rttr)
	        throws IOException, Exception {

	    // 세션에서 사용자 정보 확인
	    Integer midx = (Integer) request.getSession().getAttribute("midx");  // Integer 타입으로 캐스팅
	    int midx_int = midx;  // int로 변환
	    String ip = userip.getUserIp(request);

	    // BoardVo 객체에 사용자 정보와 IP 설정
	    bv.setMidx(midx_int);
	    bv.setIp(ip);

	    // 수정 처리
	    int result = boardService.boardUpdate(bv);  // boardUpdate가 성공하면 1, 실패하면 0 반환

	    // 내용 검증
	    if (bv.getContents() == null || bv.getContents().trim().isEmpty()) {
	        rttr.addFlashAttribute("msg", "내용을 입력해주세요.");
	        return "redirect:/notice/noticeModify.do?bidx=" + bv.getBidx();
	    }
	    
	    System.out.println("Title: " + bv.getTitle());
	    System.out.println("Contents: " + bv.getContents());
	    
	    String path = "";
	    if (result == 0) {
	        rttr.addFlashAttribute("msg", "수정에 실패했습니다.");
	        path = "redirect:/notice/noticeModify.do?bidx=" + bv.getBidx();
	    } else {
	        rttr.addFlashAttribute("msg", "수정이 완료되었습니다.");
	        path = "redirect:/notice/noticeDetail.do?bidx=" + bv.getBidx();
	    }

	    return path;
	}
}