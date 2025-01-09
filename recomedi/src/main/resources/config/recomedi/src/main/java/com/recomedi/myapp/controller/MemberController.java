package com.recomedi.myapp.controller;

//import java.util.HashMap;
//import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.recomedi.myapp.domain.MemberVo;
import com.recomedi.myapp.service.MemberService;
import com.recomedi.myapp.util.UserIp;

@Controller
@RequestMapping(value="/member")
public class MemberController {
	
private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired(required=false)
	private UserIp userip;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// email
//	@Autowired(required=false)
//	private MailApi mailApi;
	
	@RequestMapping(value="memberJoin.do", method=RequestMethod.GET)
	public String memberJoin() {
		
		logger.info("memberJoin들어옴");
		
		return "WEB-INF/member/memberJoin";
	}
	
	@RequestMapping(value="/memberJoinAction.do", method=RequestMethod.POST)
	public String memberJoinAction(
			MemberVo mv,
			HttpServletRequest request,
			RedirectAttributes rttr
			) throws Exception {

		logger.info("memberJoinAction들어옴");

		// 비밀번호 암호화
		String memberpwd_enc = bCryptPasswordEncoder.encode(mv.getPwd());
		mv.setPwd(memberpwd_enc);
		
		String ip = userip.getUserIp(request);
		mv.setIp(ip);
		
		int value = memberService.memberInsert(mv);
		
		String path = "";
		if(value == 1) {
			rttr.addFlashAttribute("msg", "회원가입이 완료되었습니다.");
			path = "redirect:/";
		} else {
			rttr.addFlashAttribute("msg", "회원가입이 실패했습니다.");
			path = "redirect:/member/memberJoin.do";			
		}
		
		return path;
	}	
	
	@RequestMapping(value="memberLogin.do", method=RequestMethod.GET)
	public String memberLogin() {

		logger.info("memberLogin들어옴");
		
		return "WEB-INF/member/memberLogin";
	}
	
	@RequestMapping(value="memberLoginAction.do", method=RequestMethod.POST)
	public String memberLoginAction(
			MemberVo inputMv,
			HttpSession session,
			RedirectAttributes rttr
		) {

		logger.info("memberLoginAction들어옴");
		System.out.println("inputMv.getId() : " + inputMv.getId());
		MemberVo mv = memberService.memberSelect(inputMv.getId());
		
		System.out.println("세션에 저장된 midx: " + session.getAttribute("midx"));
		System.out.println("세션 ID: " + session.getId());
		
		System.out.println("mv : " + mv);
		String path = "";
 		
		if(mv != null) {

			String password = mv.getPwd();
			
			if(bCryptPasswordEncoder.matches(inputMv.getPwd(), password)) {
			    logger.info("비밀번호 일치");

			    session.setAttribute("sessionMember", mv);
			    session.setAttribute("midx", mv.getMidx());  // 세션에 midx 값 저장

			    rttr.addAttribute("midx", mv.getMidx());
			    rttr.addAttribute("id", mv.getId());
			    rttr.addAttribute("admin", mv.getAdmin());

			    // 사용자나 관리자에 맞는 메시지 설정
			    String welcomeMessage = mv.getName() + "님 환영합니다🎉";
			    String userTypeMessage = mv.getAdmin().equals("Y") ? "관리자 로그인" : "사용자 로그인";

			    // 콘솔에 메시지 출력
			    System.out.println(welcomeMessage + " " + userTypeMessage);

			    rttr.addFlashAttribute("msg", welcomeMessage + " " + userTypeMessage);

			    if(session.getAttribute("saveUrl") != null) {
			        path = "redirect:" + session.getAttribute("saveUrl").toString();
			    } else {
			        path = "redirect:/";
			    }

			    return path;
			}
			logger.info("비밀번호 불일치");
		}

		rttr.addAttribute("midx", "");
		rttr.addAttribute("id", "");
		rttr.addAttribute("admin", "");
		
		rttr.addFlashAttribute("msg", "아이디/비밀번호를 확인해주세요");
					
		return "redirect:/member/memberLogin.do";
		
	}
	
	@ResponseBody
	@RequestMapping(value="memberIdCheck.do", method=RequestMethod.POST)
	public JSONObject memberIdCheck(@RequestParam("id") String id) {

		logger.info("memberIdCheck들어옴");
				
		int cnt = memberService.memberIdCheck(id);
		
		JSONObject obj = new JSONObject();
		obj.put("cnt", cnt);
		
		return obj;	
		
	}
	
	@RequestMapping(value="memberLogout.do", method=RequestMethod.GET)
	public String memberLogout(
			HttpSession session,
			RedirectAttributes rttr) {

		logger.info("memberLogout들어옴");
		
		session.removeAttribute("midx");
		session.removeAttribute("Id");
		session.removeAttribute("admin");

		rttr.addFlashAttribute("msg", "로그아웃이 완료되었습니다.");
		return "redirect:/";
		
	}
	
	@RequestMapping(value="memberMypage.do", method=RequestMethod.GET)
	public String memberMypage(HttpServletRequest request, Model model) {

		logger.info("memberMypage들어옴");

		String id = request.getSession().getAttribute("id").toString();
		MemberVo mv = memberService.memberSelect(id);
		model.addAttribute("mv", mv);
		
		return "WEB-INF/member/memberMypage";
	}

	@RequestMapping(value="memberMypageAction.do", method=RequestMethod.POST)
	public String memberMypage(
			MemberVo mv, 
			HttpServletRequest request, 
			RedirectAttributes rttr
			) throws Exception {

		logger.info("memberMypageAction들어옴");
		
		String path = "";		

		// 저장된 midx를 가져온다
		String midx = request.getSession().getAttribute("midx").toString();
		int midx_int = Integer.parseInt(midx);
		mv.setMidx(midx_int);

		// 비밀번호 암호화
		String passwordEnc = bCryptPasswordEncoder.encode(mv.getPwd());
		mv.setPwd(passwordEnc);
		
		String ip = userip.getUserIp(request);
		mv.setIp(ip);
		
		int value = memberService.memberUpdate(mv);
		
		if(value == 1) {
			rttr.addFlashAttribute("msg", "회원정보 수정이 완료되었습니다.");
		} else {
			rttr.addFlashAttribute("msg", "회원정보 수정이 실패했습니다.");
		}			
		
		return "redirect:/member/memberMypage.do";
	}
	
	@RequestMapping(value="/memberDeleteAction.do")
	public String memberCancelAction(
			MemberVo mv, 
			HttpServletRequest request, 
			RedirectAttributes rttr
			) throws Exception {

		
		logger.info("memberDeleteAction들어옴");
		
		String path = "";

		// 저장된 midx를 가져온다
		String midx = request.getSession().getAttribute("midx").toString();
		int midx_int = Integer.parseInt(midx);
		mv.setMidx(midx_int);

		String ip = userip.getUserIp(request);
		mv.setIp(ip);
		
		int value = memberService.memberDelete(mv);
		
		if(value == 1) {
			rttr.addFlashAttribute("msg", "회원탈퇴가 완료되었습니다.");
		} else {
			rttr.addFlashAttribute("msg", "회원탈퇴가 실패했습니다.");
		}

		return "redirect:/member/memberLogout.do";
	}	
	
//	@RequestMapping(value="memberFindId.do", method=RequestMethod.GET)
//	public String memberFindId(HttpServletRequest request) {
//
//		logger.info("memberFindId들어옴");
//		
//		return "WEB-INF/member/memberFindId";
//	}
//
//	@RequestMapping(value="memberFindIdAction.do", method=RequestMethod.POST)
//	public String memberFindIdAction(
//			MemberVo mv, 
//			RedirectAttributes rttr
//			) {
//
//		logger.info("memberFindIdAction들어옴");
//
//		String id = "";
//		id = memberService.memberFindId(mv);
//	
//		String path = "";
//		if(id == null) {
//			rttr.addFlashAttribute("msg", "아이디가 없습니다.");
//			path = "redirect:/member/memberFindId.do";
//		} else {
//			// model.addAttribute("id", id);
//			// model.addAttribute("name", mv.getName());
//			// model.addAttribute()로 설정한 데이터는 리다이렉트 이후에 접근할 수 없으므로 rttr.addFlashAttribute를 사용
//			rttr.addFlashAttribute("id", id);
//		    rttr.addFlashAttribute("name", mv.getName());
//			path = "redirect:/member/memberFindIdResult.do";
//		}
//		
//		return path;
//	}
//
//	@RequestMapping(value="memberFindIdResult.do", method=RequestMethod.GET)
//	public String memberFindIdResult() {		
//		
//		logger.info("memberFindIdResult들어옴");
//		
//		return "WEB-INF/member/memberFindIdResult";
//	}
//	
//	@RequestMapping(value="memberFindPw.do", method=RequestMethod.GET)
//	public String memberFindPw(HttpServletRequest request) {
//
//		logger.info("memberFindPw들어옴");
//		
//		return "WEB-INF/member/memberFindPw";
//	}
//
//	@RequestMapping(value="memberFindPwAction.do", method=RequestMethod.POST)
//	public String memberFindPwAction(
//			MemberVo mv, 
//			RedirectAttributes rttr,
//			HttpServletRequest request,
//			HttpServletResponse response
//			) {
//
//		logger.info("memberFindPwAction들어옴");
//		
//		MemberVo findMv = memberService.memberFindPw(mv);
//
//		String path = "";
//		if(findMv == null) {			
//			rttr.addFlashAttribute("msg", "아이디가 없습니다.");
//			path = "redirect:/member/memberFindPw.do";
//			
//		} else {
//
//			// 비밀번호 변경
//			String temporaryPassword = UUID.randomUUID().toString();  // 임시 비밀번호 생성			
//			String passwordEnc = bCryptPasswordEncoder.encode(temporaryPassword);  // 비밀번호 암호화
//			findMv.setPassword(passwordEnc);
//
//			int value = memberService.memberUpdate(mv);
//			
//			if(value == 1) {
//				
//				// email
//				HashMap<String, Object> hm = new HashMap<String, Object>();
//				hm.put("subject", "임시 비밀번호 안내 메일입니다.");
//				hm.put("contents", findMv.getName() + "님의 임시 비밀번호는 " + temporaryPassword + " 입니다.");
//				hm.put("senderEmail", "biso150@.gmail.com");
//				hm.put("receiverEmail", "biso150@gmail.com");
//				
//				mailApi.sendEmail(request, response, hm);
//				
//				rttr.addFlashAttribute("email", findMv.getEmail());
//				path = "redirect:/member/memberFindPwResult.do";
//				
//			} else {
//				rttr.addFlashAttribute("msg", "비밀번호 찾기가 실패했습니다.");
//				path = "redirect:/member/memberFindPw.do";
//			}
//			
//		}
//				
//		return path;
//	}
//
//	@RequestMapping(value="memberFindPwResult.do", method=RequestMethod.GET)
//	public String memberFindPwResult() {
//		
//		logger.info("memberFindPwResult들어옴");
//		
//		return "WEB-INF/member/memberFindPwResult";
//	}
//	
}