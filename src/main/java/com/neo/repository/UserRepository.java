package com.neo.repository;

import com.neo.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    Long deleteById(Long id);

   // @Query(value = "select u from User u where u.poemName like '%?1%'")
   // List<User> findByNameLike( String poemName);
    List<User> findByPoemAuthor( String poemAuthor);

    List<User> findByPoemDynasty( String poemDynasty);

    //模糊查询
    List<User> findAllByPoemAuthorContaining( String poemAuthor);

    List<User> findAllByPoemDynastyContaining( String poemDynasty);

    List<User> findAllByPoemNameContaining( String poemDynasty);

    List<User> findAllByPoemTypeContaining( String poemDynasty);

    List<User> findAllByPoemDescriptionContaining( String poemDescription);

   //多条件模糊查询 根据朝代和诗词的作者进行模糊查询
    List<User> findAllByPoemTypeContainingAndPoemAuthor( String poemDynasty,String poemAuthor);

    List<User> findAllByDetailContentContaining( String detailContent);

}