package com.redcapd.usermanager;

import com.redcapd.usermanager.entity.UserEntity;
import org.hibernate.HibernateException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import java.util.List;

@ApplicationScoped
public class UserManagerDao {
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

	public int createUser(String usr, String psw, String email, int lang) {
		UserEntity user = new UserEntity();
		try{
			user.setUsername(usr);
			user.setPassword(psw);
			user.setEmail(email);
			user.setLanguageId(lang);
			user.setSalt(BCrypt.gensalt());// Generazione del salt
			entityManager.persist(user);
			return 1;
		}
		catch(HibernateException e){
			e.printStackTrace();
		}
		finally{
			entityManager.close();
		}
		return 0;
	}

	public boolean updateUser(int uid, String psw, String email, String lang) {
		return false;
	}

	public boolean deleteUser(int uid) {
		return false;
	}

}
