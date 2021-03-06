package com.lti.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lti.entity.Claim;
import com.lti.entity.Customer;
import com.lti.entity.NewPolicy;
import com.lti.entity.Policy;
import com.lti.exception.ClaimException;

@Component
public class ClaimDao extends GenericDao {

	@PersistenceContext
	private EntityManager entityManager;

	
	public List<NewPolicy> fetchClaimDetails(int customerId) {
		return entityManager
				.createQuery("select n from Customer c join c.vehicles v join v.newPolicy n join n.policy p"
				+ " where c.customerId=:customerId").setParameter("customerId", customerId).getResultList();
	}

	public boolean isPolicyPresent(int policyNo) {
		return (long)entityManager
				.createQuery("select count(p.policyNo) from NewPolicy p where p.policyNo=:policyNo")
				.setParameter("policyNo", policyNo)
				.getSingleResult() ==1  ?true:false;
	}
	
	public boolean isClaimPresent(int policyNo) {
		String name="pending";
		return (long)entityManager
				.createQuery("select count(c.claimId) from Claim c join c.newPolicy n where n.policyNo=:policyNo and c.status like :status ")
				.setParameter("policyNo", policyNo)
				.setParameter("status", "%"+name+"%")
				.getSingleResult() ==1  ?true:false;
	}
	
	public LocalDate paymentDate(int policyNo) {
		return (LocalDate) entityManager
				.createQuery("select max(p.paymentDate) from Payment p join p.newPolicy n where n.policyNo=:policyNo")
				.setParameter("policyNo", policyNo).getSingleResult();
	}
	
	public int fetchPolicyDurationByPolicyNo(int policyNo) {
		return (int) entityManager
				.createQuery("select p.policyDuration from NewPolicy n join n.policy p where n.policyNo=:policyNo")
				.setParameter("policyNo", policyNo)
				.getSingleResult();
	}


}
