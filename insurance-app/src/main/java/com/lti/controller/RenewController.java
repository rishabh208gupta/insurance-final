package com.lti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lti.dto.PolicyRegistrationStatus;
import com.lti.entity.NewPolicy;
import com.lti.service.RenewService;

@CrossOrigin
@RestController
public class RenewController {

	@Autowired
	private RenewService renewService;

	@GetMapping("/renew-policy")
	public PolicyRegistrationStatus renewPolicy(@RequestParam("policyNo") int policyNo,
			@RequestParam("policyDuration") int policyDuration) {
		
		
		//renewService.
		return null;
		
	}

}
