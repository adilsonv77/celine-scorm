package br.univali.celine.lms.model;

import java.util.Date;

import br.univali.celine.lmsscorm.User;
import br.univali.celine.lmsscorm.UserPractice;

public class UserPracticeImpl implements UserPractice {

	private User user;
	private int quantity;
	private double time;
	private Date lastTimeAccessed;

	public int getQuantity() {
		return quantity;
	}

	public double getTime() {
		return time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setTime(double time) {
		this.time = time;
	}
	
	
	public void setLastTimeAccessed(Date lastTimeAccessed) {
		this.lastTimeAccessed = lastTimeAccessed;
	}

	public Date getLastTimeAccessed() {
		return lastTimeAccessed;
	}

}
