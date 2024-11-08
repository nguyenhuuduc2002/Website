package vn.titv.spring.mvcsecurity.service;

import vn.titv.spring.mvcsecurity.entity.Student;
import vn.titv.spring.mvcsecurity.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    public void saveUser(User user);

    public boolean isUsernameExist(String username);

    public User findByUsername(String username);

    public void saveUser2(User user);

    public List<User> findAll();

    void deleteByUsername(String username);

    public User findByEmail(String email);

    public void updatePassword(String username, String newPassword);

    public boolean existsByUsername(String username);

    public boolean existsByEmail(String email);

    public List<User> findByRoles(String role);

    public List<User> findByEnabledFalse();


}
