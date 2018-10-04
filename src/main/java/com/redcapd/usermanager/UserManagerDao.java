package com.redcapd.usermanager;

public class UserManagerDao {
	private static UserManagerDao myInstance = null;


	public UserManagerDao getInstance() {
		if (UserManagerDao.myInstance == null){
		    UserManagerDao.myInstance = new UserManagerDao();
        }
        return UserManagerDao.myInstance;
	}

	public int createUser(String usr, String psw, String email, String lang) {
		return 0;
	}

	public boolean updateUser(int uid, String psw, String email, String lang) {
	    return false;
	}

	public boolean deleteUser(int uid) {
	    return false;
	}

}
