package com.redcapd.usermanager;

import com.redcapd.usermanager.entity.User;
import com.redcapd.usermanager.entity.UserEntity;
import org.hibernate.HibernateException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import javax.enterprise.context.SessionScoped;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@SessionScoped
public class UserManagerDao implements Serializable {
	EntityManagerFactory entityManagerfactory;
	EntityManager entityManager;


	public UserManagerDao(){
		entityManagerfactory = Persistence.createEntityManagerFactory("MyPersistenceUnit");
		entityManager = entityManagerfactory.createEntityManager();
	}

	public List<UserEntity> getAllUsers(){
		String hql = "SELECT e FROM UserEntity e";
		Query query = entityManager.createQuery(hql);
		List<UserEntity> users = query.getResultList();
		return users;
	}

	public User getUserByUsername(String username) {
		try{
			UserEntity user = entityManager.find(UserEntity.class, username);
			return user.toUser();
		}
		catch(EntityNotFoundException e){
			e.printStackTrace();
			return null;
		}
	}

	public UserEntity getUser(int id){
		UserEntity user = entityManager.find(UserEntity.class, id);
		if(user == null){
			throw new EntityNotFoundException("Nessun utente trovato con ID = " + id);
		}
		return user;
	}

	public int createUser(String usr, String psw, String email, int lang) {
		EntityTransaction transaction = entityManager.getTransaction();
		UserEntity user = new UserEntity();
		user.setUsername(usr);
		user.setPassword(psw);
		user.setEmail(email);
		user.setLanguageId(lang);
		user.setSalt(BCrypt.gensalt());// Generazione del salt
		try{
			transaction.begin();
			entityManager.persist(user);
			transaction.commit();
			return 0;
		}
		catch(HibernateException e){
			e.printStackTrace();
			transaction.rollback();
			return -1;
		}
		finally{
			entityManager.close();
		}
	}

	public int updateUser(int id, String usr,String psw,String eml,int lng){
		EntityTransaction transaction = entityManager.getTransaction();
		UserEntity user = entityManager.find(UserEntity.class, id);
		if(user != null){
			user.setUsername(usr);
			user.setPassword(psw);
			user.setEmail(eml);
			user.setLanguageId(lng);
			try{
				transaction.begin();
				entityManager.persist(user);
				transaction.commit();
				return 0;
			}
			catch(HibernateException e){
				e.printStackTrace();
				transaction.rollback();
				return -1;
			}
		}
		return -2;
	}

	public int deleteUser(int id) {
		EntityTransaction transaction = entityManager.getTransaction();
		UserEntity user = entityManager.find(UserEntity.class, id);
		try{
			transaction.begin();
			entityManager.remove(user);
			transaction.commit();
			return 0;
		}
		catch(HibernateException e){
			e.printStackTrace();
			transaction.rollback();
			return -1;
		}
	}

}
