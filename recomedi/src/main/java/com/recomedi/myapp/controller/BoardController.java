
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
		// ��� ������ �ϰ� �����͸� �������� ����

		// ���� �˻� ���͸� //?
		if (scri.getTitle() != null && !scri.getTitle().trim().isEmpty()) {
			scri.setSearchType("title");
		} else {
			scri.setSearchType(null); // �˻� ������ ���� ��� null�� ����
		}

		// �Խù� �� ����
		int cnt = boardService.boardTotalCount(scri);

		// ������ ���� ����
		pm.setScri(scri);
		pm.setTotalCount(cnt);

		// �������� �˻��� �Խù� ���
		ArrayList<BoardVo> blist = boardService.boardSelectAll(scri);

		// �Խù��� ���� ���
		if (blist.isEmpty()) {
			model.addAttribute("noResultMessage", "�˻� ����� �����ϴ�.");
		}

		// ����� �𵨿� �߰�
		model.addAttribute("blist", blist);
		model.addAttribute("pm", pm);

		// ����� �ڵ�
		System.out.println("scri : " + scri);// ���ŷ�ڵ� : ������
		System.out.println("Received title: " + scri.getTitle());// ���ŷ�ڵ� : ���� �˻��� �ϸ� ����

		// ��ȯ
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
		// �α� �߰�
		System.out.println("boardWriteAction �޼��� ����");

		// null üũ �߰�
		if (request == null || request.getSession() == null) {
			System.out.println("Error: request �Ǵ� session�� null�Դϴ�.");
			rttr.addFlashAttribute("msg", "���� ������ �߻��߽��ϴ�.");
			return "redirect:/error.do";
		}

		// ���ǿ��� midx ����
		Object midxObj = request.getSession().getAttribute("midx");
		if (midxObj == null) {
			System.out.println("Error: ���ǿ� midx�� �����ϴ�.");
			rttr.addFlashAttribute("msg", "�α����� �ʿ��մϴ�.");
			return "redirect:/member/memberLogin.do"; // ������ ���
		}

		String midx = midxObj.toString();
		System.out.println("midx: " + midx);

		int midx_int = Integer.parseInt(midx);

		String ip = userip.getUserIp(request);
		System.out.println("IP: " + ip);

		// BoardVo�� midx�� ip ����
		bv.setMidx(midx_int);
		bv.setIp(ip);
		bv.setState("0");

		// BoardVo ���� ���
		System.out.println("BoardVo ����: " + bv.toString());

		// null üũ �߰�
		if (boardService == null) {
			System.out.println("Error: boardService�� null�Դϴ�.");
			rttr.addFlashAttribute("msg", "���� ������ �߻��߽��ϴ�.");
			return "redirect:/error.do";
		}

		// DB�� �� ����
		int value = boardService.boardInsert(bv);
		System.out.println("boardInsert ���: " + value);

		String path = "";
		if (value == 1) {
			System.out.println("�� ���� ����");
			path = "redirect:/notice/noticeList.do";
		} else {
			System.out.println("�� ���� ����");
			rttr.addFlashAttribute("msg", "�Է��� �߸��Ǿ����ϴ�");
			path = "redirect:/notice/noticeWrite.do";
		}

		System.out.println("���� ���: " + path);
		return path; // ���𷺼� ��� ��ȯ
	}

	// ���� ó��
	@RequestMapping(value = "/noticeDelete.do", method = RequestMethod.GET)
	public String noticeDelete(@RequestParam("bidx") int bidx, RedirectAttributes rttr) {
		int result = boardService.boardDelete(bidx);

		if (result > 0) {
			rttr.addFlashAttribute("msg", "�Խñ��� �����Ǿ����ϴ�.");
		} else {
			rttr.addFlashAttribute("msg", "������ �����߽��ϴ�.");
		}

		return "redirect:/notice/noticeList.do";
	}

	@RequestMapping(value = "noticeDetail.do")
	public String noticeDetail(@RequestParam("bidx") int bidx, Model model) {
		boardService.boardViewCntUpdate(bidx);
		BoardVo bv = boardService.boardSelectOne(bidx);

		model.addAttribute("bv", bv);
		model.addAttribute("bidx", bidx); // bidx�� ��������� �𵨿� �߰�

		// ������� ���� �α� �߰�
//		System.out.println("Controller - bidx: " + bidx);
//		System.out.println("Controller - BoardVo: " + bv);

		return "WEB-INF/notice/noticeDetail";
	}

	@RequestMapping(value = "noticeModify.do")
	public String noticeModify(@RequestParam("bidx") int bidx, Model model) {

		BoardVo bv = boardService.boardSelectOne(bidx);
		model.addAttribute("bv", bv);// �� �̰� bv�� ����������?

		String path = "WEB-INF/notice/noticeModify";
		return path;
	}

	@RequestMapping(value = "noticeModifyAction.do")
	public String noticeModifyAction(@ModelAttribute BoardVo bv, HttpServletRequest request, RedirectAttributes rttr)
	        throws IOException, Exception {

	    // ���ǿ��� ����� ���� Ȯ��
	    Integer midx = (Integer) request.getSession().getAttribute("midx");  // Integer Ÿ������ ĳ����
	    int midx_int = midx;  // int�� ��ȯ
	    String ip = userip.getUserIp(request);

	    // BoardVo ��ü�� ����� ������ IP ����
	    bv.setMidx(midx_int);
	    bv.setIp(ip);

	    // ���� ó��
	    int result = boardService.boardUpdate(bv);  // boardUpdate�� �����ϸ� 1, �����ϸ� 0 ��ȯ

	    // ���� ����
	    if (bv.getContents() == null || bv.getContents().trim().isEmpty()) {
	        rttr.addFlashAttribute("msg", "������ �Է����ּ���.");
	        return "redirect:/notice/noticeModify.do?bidx=" + bv.getBidx();
	    }
	    
	    System.out.println("Title: " + bv.getTitle());
	    System.out.println("Contents: " + bv.getContents());
	    
	    String path = "";
	    if (result == 0) {
	        rttr.addFlashAttribute("msg", "������ �����߽��ϴ�.");
	        path = "redirect:/notice/noticeModify.do?bidx=" + bv.getBidx();
	    } else {
	        rttr.addFlashAttribute("msg", "������ �Ϸ�Ǿ����ϴ�.");
	        path = "redirect:/notice/noticeDetail.do?bidx=" + bv.getBidx();
	    }

	    return path;
	}
}