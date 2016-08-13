package fr.nsoft.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

	@RequestMapping("/")
	public String index() {
		return "Rapsberry Pi 3 services";
	}
	
}
