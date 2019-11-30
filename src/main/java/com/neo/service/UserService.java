package com.neo.service;

import com.neo.pojo.User;

import java.util.List;

public interface UserService {

    public List<User> getUserList();

    public User findUserById(long id);

    public void save(User user);

    public void edit(User user);

    public void delete(long id);

    public User open(long id);

   // public List<User> findByNameLike(String poemName);
   public List<User> findByPoemAuthor(String poemAuthor);

    public List<User> findByPoemDynasty(String poemDynasty);

    //通过诗的作者进行模糊查询
    public List<User> findAllByPoemAuthorContaining(String poemAuthor);

    public List<User> findAllByPoemDynastyContaining(String poemDynasty);

    public List<User> findAllByPoemNameContaining(String poemName);

    public List<User> findAllByPoemTypeContaining(String poemType);

    public List<User> findAllByPoemDescriptionContaining(String poemDescription);

    public List<User> findAllByDetailContentContaining(String detailContent);

}
