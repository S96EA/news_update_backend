package com.news.update.service;

import com.news.update.entity.Category;
import com.news.update.entity.Comments;
import com.news.update.entity.News;
import com.news.update.entity.ShortNews;
import com.news.update.model.Result;
import com.news.update.model.ResultSucces;
import com.news.update.payload.NewsRequest;
import com.news.update.payload.NewsResponse;
import com.news.update.payload.ShortNewsRequest;
import com.news.update.repository.CategoryRepository;
import com.news.update.repository.ShortNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShortNewsServiceImpl implements ShortNewsServise {


    @Autowired
    private ShortNewsRepository shortNewsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ShortNews> getAll() {
        try {
            List<ShortNews> newsList = shortNewsRepository.findAll();
            return newsList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(ShortNewsRequest shortNewsRequest) {
        try {
            ShortNews shortNews = new ShortNews();
            shortNews.setCategory(categoryRepository.getOne(shortNewsRequest.getCategory_id()));
            shortNews.setTitleUz(shortNewsRequest.getTitleUz());
            shortNews.setTitleRu(shortNewsRequest.getTitleRu());
            if (shortNewsRepository.save(shortNews) != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean edit(String id, ShortNewsRequest shortNewsRequest) {
        try {
            Optional<ShortNews> shortNews = shortNewsRepository.findById(id);
            if (shortNews != null) {
                ShortNews news = new ShortNews();
                news.setTitleUz(shortNewsRequest.getTitleUz());
                news.setTitleRu(shortNewsRequest.getTitleRu());
                news.setId(id);
                news.setCategory(categoryRepository.getOne(shortNewsRequest.getCategory_id()));
                news.setCreateAt(shortNews.get().getCreateAt());
                if (shortNewsRepository.save(news) != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            if (shortNewsRepository.findById(id) != null) {
                shortNewsRepository.deleteById(id);
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
            List<ShortNews> tutorials = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);

            Page<ShortNews> pageTuts = shortNewsRepository.findAllByCategoryIdOrderByCreateAtDesc(categoryid, paging);
            tutorials = pageTuts.getContent();
            System.out.println(tutorials);

            Map<String, Object> response = new HashMap<>();
            response.put("shortnews", tutorials);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return response;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    @Override
    public List<Map> getHeadShortNews() {
        try {
            List<Category> categories = categoryRepository.findAll();
            List<ShortNews> tutorials = new ArrayList<>();
            List<Map> mapList = new ArrayList<>();
            for (int i = 0; i < categories.size(); i++) {
                tutorials = new ArrayList<>();
                Pageable paging = PageRequest.of(0, 2);
                Page<ShortNews> pageTuts = shortNewsRepository.findAllByCategoryIdOrderByCreateAtDesc(categories.get(i).getId(), paging);
                tutorials = pageTuts.getContent();
                Map<String, Object> map =new HashMap<>();
                map.put("categorie",categories.get(i));
                map.put("news",tutorials);
                mapList.add(map);
            }
            return mapList;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Map getAllPages(int page, int size) {
        try {
            List<ShortNews> tutorials = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);

            Page<ShortNews> pageTuts = shortNewsRepository.findAllByOrderByCreateAtDesc( paging);
            tutorials = pageTuts.getContent();
            System.out.println(tutorials);

            Map<String, Object> response = new HashMap<>();
            response.put("shortnews", tutorials);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return response;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}
