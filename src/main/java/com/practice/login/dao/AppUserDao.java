package com.practice.login.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.practice.login.entity.AppUser;

@Repository
@Transactional
public class AppUserDao {

	@Autowired
	private EntityManager entityManager;
	
	public AppUser findUserAccount(String userName) {
		String sql = "select au from " + AppUser.class.getName() + " au " +
				"where au.userName = :userName";
		Query query = entityManager.createQuery(sql, AppUser.class);
		query.setParameter("userName", userName);
		try {
			return (AppUser) query.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
	
}
