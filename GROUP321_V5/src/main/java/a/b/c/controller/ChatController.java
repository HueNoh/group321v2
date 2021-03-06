package a.b.c.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import a.b.c.service.MemberServiceInterface;

@RequestMapping(value = "/chat")
@Controller
public class ChatController {

	@Autowired
	MemberServiceInterface memberService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {

		return "home";
	}

	@RequestMapping(value = "/chatMsg", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String chatMsg(Locale locale, Model model, HttpSession session, HttpServletRequest request,
			@RequestParam Map map) {
		String error = null;

		Gson gson = new Gson();
		JsonObject jObj = gson.fromJson((String) map.get("JSON"), JsonObject.class);
		JsonElement jeMsg = jObj.get("msg");
		JsonElement jeId = jObj.get("userId");
		JsonElement jeB_num = jObj.get("b_num");

		try {
			map.put("content", jeMsg.getAsString());
			map.put("m_id", jeId.getAsString());
			map.put("b_num", jeB_num.getAsInt());
			System.out.println("personcontroller : " + map);
			int result = memberService.msgInsert(map);
			if (0 != result) {
				error = "001";
			} else {
				error = "002";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonObject erObj = new JsonObject();

		erObj.addProperty("error", error);

		return gson.toJson(erObj);
	}

	@RequestMapping(value = "/viewMsg", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String viewMsg(Locale locale, Model model, HttpSession session, HttpServletRequest request,
			@RequestParam Map map) {

		boolean check = false;

		Gson gson = new Gson();

		JsonObject jb_num = gson.fromJson((String) map.get("JSON"), JsonObject.class);
		JsonElement je_bNum = jb_num.get("b_num");
		int b_num = je_bNum.getAsInt();

		JsonArray jArr = new JsonArray();
		JsonObject jObj = new JsonObject();
		JsonArray juArr = new JsonArray();

		WebSocket.users.add(session.getAttribute("id"));
		WebSocket.bs_num.add(b_num);

		try {
			for (int i = 0; i < WebSocket.users.size(); i++) {
				JsonObject obj = new JsonObject();
				obj.addProperty("userId", (String) WebSocket.users.get(i));
				obj.addProperty("b_num", (int) WebSocket.bs_num.get(i));
				juArr.add(obj);
			}

			JsonObject jSize = new JsonObject();

			jSize.addProperty("size", WebSocket.users.size());

			jObj.add("size", jSize);
			jObj.add("userId", juArr);
			map.put("b_num", b_num);
			List list = memberService.msgSelect(map);
			for (int i = 0; i < list.size(); i++) {
				map = (Map) list.get(i);
				JsonObject obj = new JsonObject();
				obj.addProperty("id", (String) map.get("m_id"));
				obj.addProperty("msg", (String) map.get("content"));
				jArr.add(obj);
			}

			jObj.add("msg", jArr);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(gson.toJson(jObj));
		return gson.toJson(jObj);
	}

}
