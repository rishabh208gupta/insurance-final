package com.lti.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.dao.ClaimDao;
import com.lti.entity.Claim;
import com.lti.entity.NewPolicy;
import com.lti.entity.Policy;
import com.lti.exception.ClaimException;

@Service
@Transactional
public class ClaimService {
	@Autowired
	private ClaimDao claimDao;
	
	public List<NewPolicy> displayOnClaimPage(int customerId){
		try {
		return claimDao.fetchClaimDetails(customerId);
		}
		catch(ClaimException e) {
			throw new ClaimException("not able to select data for the customer");
		}
	}
	
	public Claim insertClaimDetails(int policyNo,String reason) {
		try {
			Claim claim =new Claim();
			claim.setDateApplied(LocalDate.now());
			claim.setStatus("pending");
			claim.setReason(reason);
			NewPolicy newPolicy=claimDao.fetchById(NewPolicy.class, policyNo);
			claim.setNewPolicy(newPolicy);
			return claimDao.save(claim);	
		}
		catch(ClaimException e) {
			throw new ClaimException("claim details not inserted");
		}
		
	}
	
	public boolean hasPolicyExpired(int policyNo) {
		LocalDate dateOfPayment = claimDao.paymentDate(policyNo);
		int policyDuration = claimDao.fetchPolicyDurationByPolicyNo(policyNo);
		Period date = Period.between(dateOfPayment, LocalDate.now());
		if (date.getYears() > policyDuration) {
			return true;
		} else {
			return false;
		}

	}
	
	public boolean isPolicyPresent(int policyNo) {
		return claimDao.isPolicyPresent(policyNo);
	}
	
	public boolean isClaimPending(int policyNo) {
		return claimDao.isClaimPresent(policyNo);
	}
	
	

}
