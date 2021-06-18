package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.org.alicebot.ab.Bot;
import com.example.org.alicebot.ab.Chat;
import com.example.org.alicebot.ab.MagicBooleans;
import com.example.org.alicebot.ab.MagicStrings;
import com.example.org.alicebot.ab.utils.IOUtils;

@RestController
@RequestMapping("/api/v1")
public class MobileServices {
	private static final boolean TRACE_MODE = false;
	static String botName = "super";
	
	@RequestMapping(value = "/chatbot", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> chatbot(@RequestParam(name = "message", defaultValue = "") String message) {
		Map<String, Object> responses = new HashMap<String, Object>();
		String responcemessage=null;
		if(Main.bot!=null && Main.chatSession!=null) {
		 
		try {

			String textLine = "";
				textLine = message;//IOUtils.readInputTextLine();
				if ((textLine == null) || (textLine.length() < 1))
					textLine = MagicStrings.null_input;
				else{
					String request = textLine;
			//if (MagicBooleans.trace_mode) {(chatSession.thatHistory.get(0)).get(0); Main.chatSession.predicates.get("topic");}
			//	 
					String response = Main.chatSession.multisentenceRespond(request);
					while (response.contains("&lt;"))
						response = response.replace("&lt;", "<");
					while (response.contains("&gt;"))
						response = response.replace("&gt;", ">");
					
					responses.put("success", "true");
					responses.put("input", textLine);
					responses.put("data", response);

					return new ResponseEntity<Object>(responses, HttpStatus.OK);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			responses.put("success", "false");
			responses.put("input", "error");
			responses.put("data", e.toString());
			return new ResponseEntity<Object>(responses, HttpStatus.OK);
		}
		}
		responses.put("success", "true");
		responses.put("input", responcemessage+"out"+message);
		responses.put("data", responcemessage);

		return new ResponseEntity<Object>(responses, HttpStatus.OK);
	}

	
}
