package com.neo.service.impl;

import com.neo.pojo.User;
import com.neo.repository.UserRepository;
import com.neo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void edit(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        userRepository.delete(id);
    }

    @Override
    public User open(long id) {
        return userRepository.findById(id);
    }

//    @Override
//    public List<User> findByNameLike(String poemName) {
//        return userRepository.findByNameLike(poemName);
//    }
    @Override
    public List<User> findByPoemAuthor(String poemAuthor) {
        return userRepository.findByPoemAuthor(poemAuthor);
    }

    @Override
    public List<User> findByPoemDynasty(String poemDynasty) {
        return userRepository.findByPoemDynasty(poemDynasty);
    }

    @Override
    public List<User> findAllByPoemAuthorContaining(String poemAuthor) {
        return userRepository.findAllByPoemAuthorContaining(poemAuthor);
    }

    @Override
    public List<User> findAllByPoemDynastyContaining(String poemDynasty) {
        return userRepository.findAllByPoemDynastyContaining(poemDynasty);
    }

    @Override
    public List<User> findAllByPoemNameContaining(String poemName) {
        return userRepository.findAllByPoemNameContaining(poemName);
    }

    @Override
    public List<User> findAllByPoemTypeContaining(String poemType) {
        return userRepository.findAllByPoemTypeContaining(poemType);
    }

    @Override
    public List<User> findAllByPoemDescriptionContaining(String poemDescription) {
        return userRepository.findAllByPoemDescriptionContaining(poemDescription);
    }

    @Override
    public List<User> findAllByDetailContentContaining(String detailContent) {
        return userRepository.findAllByDetailContentContaining(detailContent);
    }
}


