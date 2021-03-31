package com.news.update.repository;

import com.news.update.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    //    boolean findAllById(String id);
    void deleteById(String id);

//        Page<Category> findAll(Pageable pageable);
    List<Category> findAllByParentId(String id);

    Set<Category> findByParentIsNotNull();

//    Page<Category> findByPublished(boolean published, Pageable pageable);
//    Page<Category> findByTitleContaining (Pageable pageable);

}
