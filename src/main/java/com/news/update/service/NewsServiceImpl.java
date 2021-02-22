package com.news.update.service;

import com.news.update.entity.Category;
import com.news.update.entity.News;
import com.news.update.model.Result;
import com.news.update.model.ResultSucces;
import com.news.update.payload.NewsRequest;
import com.news.update.repository.CategoryRepository;
import com.news.update.repository.NewsRepository;
import javassist.runtime.Desc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AttachmentService attachmentService;

    @Override
    public List<News> getAll() {
        try {
            List<News> newsList = newsRepository.findAll();
            return newsList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(String hashId, NewsRequest newsRequest) {
        try {
            News news = new News();
            news.setCategory(categoryRepository.getOne(newsRequest.getCategory_id()));
            news.setContent(newsRequest.getContent());
            news.setTitle(newsRequest.getTitle());
            if (attachmentService.findByHashId(hashId) != null) {
                news.setHeadAttachment(attachmentService.findByHashId(hashId));
            }
            ;
            if (newsRepository.save(news) != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public boolean edit(String id, NewsRequest newsRequest) {
        try {
            Optional<News> newsService1 = newsRepository.findById(id);
            if (newsService1.get() != null) {
                News news = new News();
                news.setTitle(newsRequest.getTitle());
                news.setContent(newsRequest.getContent());
                news.setCategory(categoryRepository.getOne(newsRequest.getCategory_id()));
                news.setLikesCount(newsRequest.getLikesCount());
                news.setViewsCount(newsRequest.getViewsCount());
                news.setId(id);
                news.setCreateAt(newsService1.get().getCreateAt());
                if (newsRequest.getFile()!=null&&!newsRequest.getFile().equals(newsRepository.findById(id).get().getHeadAttachment())) {
                    String hashId = attachmentService.save(newsRequest.getFile());
                    news.setHeadAttachment(attachmentService.findByHashId(hashId));
                };
                if (newsRepository.save(news) != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            if (newsRepository.findById(id).get() != null) {
                if (newsRepository.findById(id).get().getHeadAttachment()!=null){
                    attachmentService.delete(newsRepository.findById(id).get().getHeadAttachment().getHashId());
                }
                newsRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Map getPages(String categoryid,int page, int size) {
        try {
            List<News> tutorials = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);

            Page<News> pageTuts = newsRepository.findAllByCategoryId(categoryid, paging);
            tutorials = pageTuts.getContent();
            System.out.println(tutorials);

            Map<String, Object> response = new HashMap<>();
            response.put("news", tutorials);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return response;
        } catch (Exception e) {
            System.out.println(e);
//            return null;
        }

        return null;
    }

}