package com.springBoot.web.springbootfirstwebapplication;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springBoot.web.models.Details;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Controller
public class LoginController {
	@RequestMapping(value = "/getMovie/{name}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Details> getLoginMessage(@PathVariable("name") String name, @CookieValue(value = "id", defaultValue = "")String id
							, HttpServletResponse response) {
		System.out.println(id);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://www.omdbapi.com")
				.queryParam("plot", "full")
				.queryParam("t", name)
				.queryParam("apikey", "caee358");

		RestTemplate restTemplate = new RestTemplate();
		String res = restTemplate.getForObject(builder.toUriString(), String.class);
		ObjectMapper mapper = new ObjectMapper();
		Details details1 = new Details();
		try {
			mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			details1 = mapper.readValue(res, Details.class);
		}catch (Exception ex){
			System.out.println(ex);
		}
		Cookie cookie = new Cookie("id", "test");
		response.addCookie(cookie);
		return ResponseEntity.ok(details1);
	}
}
