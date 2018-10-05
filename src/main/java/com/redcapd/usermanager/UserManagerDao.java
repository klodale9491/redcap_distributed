package com.redcapd.usermanager;

import com.redcapd.usermanager.entity.UserEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UserManagerDao {
	private static UserManagerDao myInstance = null;
	SessionFactory sessionFactory;
	Session session;
	EntityManager entityManager;


	private UserManagerDao(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
		entityManager = entityManagerFactory.createEntityManager();
		this.sessionFactory = new Configuration().configure().buildSessionFactory();
		this.session = sessionFactory.openSession();
	}

	public static UserManagerDao getInstance() {
		if (UserManagerDao.myInstance == null){
		    UserManagerDao.myInstance = new UserManagerDao();
        }
        return UserManagerDao.myInstance;
	}

	public List<UserEntity> getAllUsers(){
		String hql = "SELECT e FROM "+UserEntity.class.getName();
		Query<UserEntity> query = session.createQuery(hql);
		List<UserEntity> users = query.getResultList();
		return users;
	}

	public int createUser(String usr, String psw, String email, int lang) {
		//Transaction tx = this.session.beginTransaction();
		entityManager.getTransaction().begin();
		UserEntity user = new UserEntity();
		try{
			user.setUsername(usr);
			user.setPassword(psw);
			user.setEmail(email);
			user.setLanguageId(lang);
			user.setSalt(BCrypt.gensalt());// Generazione del salt
			entityManager.persist(user);
			//tx.commit(); // commit dell'inserimento
			entityManager.getTransaction().commit();
			entityManager.close();
			return 1;
		}
		catch(HibernateException e){
			//tx.rollback();
		}
		finally{
			session.close();
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
