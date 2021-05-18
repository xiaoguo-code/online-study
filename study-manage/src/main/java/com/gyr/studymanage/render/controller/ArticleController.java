package com.gyr.studymanage.render.controller;

import com.gyr.studycommon.entity.*;
import com.gyr.studycommon.util.DateUtils;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.util.R;
import com.gyr.studycommon.vo.AdminVO;
import com.gyr.studycommon.vo.ArticleCategoryVO;
import com.gyr.studycommon.vo.ArticleSectionVO;
import com.gyr.studycommon.vo.ArticleVO;
import com.gyr.studymanage.render.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @desc: 文章管理
 * @Author: guoyr
 * @Date: 2021-05-03 18:34
 */

@Controller
@Slf4j
@RequestMapping("/admin/article")
public class ArticleController {

    @Autowired
    AdminService   adminService;

    @Autowired
    ArticleService articleService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseSectionService courseSectionService;




    /**
     *  根据学科类别获取课程对应的学习路线列表
     */
    @RequestMapping("/list")
    public ModelAndView listChildByParentIdNotAjax(@RequestParam(value = "async",required = false) boolean async,
                                                   ArticleVO conditions,
                                                   Model model) {
        log.info("1、查询文章列表!");
        PageUtils page = null;
       try {
            page = articleService.getArticlePageByCondition(conditions);
           List<Article> articles = (List<Article>) page.getList();
           if(!CollectionUtils.isEmpty(articles)){
               for (Article obj :
                       articles) {
                   String[] categoryIds = obj.getBlogType().split(",");
                   CategoryInfo one = categoryService.getCategoryInfoById(Integer.parseInt(categoryIds[0]));
                   CategoryInfo two = categoryService.getCategoryInfoById(Integer.parseInt(categoryIds[1]));
                   CategoryInfo three = categoryService.getCategoryInfoById(Integer.parseInt(categoryIds[2]));
                   String typeName = one.getTitle()+"-"+two.getTitle()+"-"+three.getTitle();
                   obj.setBlogType(typeName);
               }
           }
           model.addAttribute("page",page);
           model.addAttribute("articleList",page.getList());
       } catch (Exception e) {
            log.error("查询文章列表异常", e);
        }
        return new ModelAndView(!async?"article/articleList":"article/articleList::#tableBox",
                "articleModel",model);
    }


//    @RequestMapping("/getSection")
//    public ModelAndView getAllNameAndSectionName(String categoryIds,Model model){
//        List<CourseInfo> courseList = courseService.getCoursesListByType(categoryIds);
//
//        List<ArticleSectionVO> articleSectionList = new ArrayList<>();
//        if(courseList!=null){
//            for (CourseInfo course : courseList) {
//                List<CourseSectionInfo> courseSectionList = courseSectionService.getCourseSectionListByType(String.valueOf(course.getCourseId()));
//                if(courseSectionList!=null){
//                    for (CourseSectionInfo courseSection : courseSectionList) {
//                        String courseSectionIds = course.getCourseId()+","+courseSection.getCourseId();
//                        String courseSectionNames = course.getCourseName()+"-"+courseSection.getSectionName();
//                        ArticleSectionVO articleSectionVO = new ArticleSectionVO();
//                        articleSectionVO.setCourseAndSectionIds(courseSectionIds);
//                        articleSectionVO.setCourseAndSectionNames(courseSectionNames);
//                        articleSectionList.add(articleSectionVO);
//                    }
//                }
//            }
//        }
//        model.addAttribute("articleSectionList",articleSectionList);
//        return new ModelAndView("article/article_section");
//
//    }

    @RequestMapping("/addArticle")
    public ModelAndView addArticle(Model model) {

//        UsersInfo userInfo = usersService.getUserInfoById(userId);
        List<CategoryInfo> allCategory = categoryService.findAllCategory();
        List<ArticleCategoryVO> articleCategoryList = new ArrayList<>();
        if(allCategory!=null){
            for (int i = 0; i < allCategory.size(); i++) {
                CategoryInfo obj1 = allCategory.get(i);
                if(obj1.getChildrenList()!=null){
                    for (CategoryInfo obj2 : obj1.getChildrenList()) {
                        if(obj2.getChildrenList()!=null){
                            for (CategoryInfo obj3 : obj2.getChildrenList()) {
                                ArticleCategoryVO  articleCategoryVO = new ArticleCategoryVO();
                                String ids = obj1.getCategoryId()+","+obj2.getCategoryId()+","+obj3.getCategoryId();
                                String titles = obj1.getTitle()+"-"+obj2.getTitle()+"-"+obj3.getTitle();
                                articleCategoryVO.setCategoryIds(ids);
                                articleCategoryVO.setTitles(titles);
                                articleCategoryList.add(articleCategoryVO);
                            }
                        }
                    }
                }
            }
        }
        List<ArticleSectionVO> articleSectionList = new ArrayList<>();
        for(ArticleCategoryVO obj : articleCategoryList) {
            List<CourseInfo> courseList = courseService.getCoursesListByType(obj.getCategoryIds());
            if(courseList!=null){
                for (CourseInfo course : courseList) {
                    List<CourseSectionInfo> courseSectionList = courseSectionService.getCourseSectionListByType(String.valueOf(course.getCourseId()));
                    if(courseSectionList!=null){
                        for (CourseSectionInfo courseSection : courseSectionList) {
                            String courseSectionIds = obj.getCategoryIds()+"-"+course.getCourseId()+","+courseSection.getSectionId();
                            String courseSectionNames = "类别:("+obj.getTitles()+")-课程章节：("+course.getCourseName()+"-"+courseSection.getSectionName()+")";
                            ArticleSectionVO articleSectionVO = new ArticleSectionVO();
                            articleSectionVO.setCourseAndSectionIds(courseSectionIds);
                            articleSectionVO.setCourseAndSectionNames(courseSectionNames);
                            articleSectionList.add(articleSectionVO);
                        }
                    }
                }
            }
        }

        model.addAttribute("contents",null);
        model.addAttribute("articleCategoryList",articleSectionList);
        model.addAttribute("articleSectionList",null);
        return new ModelAndView("article/article_edit");
    }


    @RequestMapping("/edit/{blogId}")
    public ModelAndView editUserPage(@PathVariable("blogId") Integer blogId, Model model) {
        List<CategoryInfo> allCategory = categoryService.findAllCategory();
        List<ArticleCategoryVO> articleCategoryList = new ArrayList<>();
        if(allCategory!=null){
            for (int i = 0; i < allCategory.size(); i++) {
                CategoryInfo obj1 = allCategory.get(i);
                if(obj1.getChildrenList()!=null){
                    for (CategoryInfo obj2 : obj1.getChildrenList()) {
                        if(obj2.getChildrenList()!=null){
                            for (CategoryInfo obj3 : obj2.getChildrenList()) {
                                ArticleCategoryVO  articleCategoryVO = new ArticleCategoryVO();
                                String ids = obj1.getCategoryId()+","+obj2.getCategoryId()+","+obj3.getCategoryId();
                                String titles = obj1.getTitle()+"-"+obj2.getTitle()+"-"+obj3.getTitle();
                                articleCategoryVO.setCategoryIds(ids);
                                articleCategoryVO.setTitles(titles);
                                articleCategoryList.add(articleCategoryVO);
                            }
                        }
                    }
                }
            }
        }
        List<ArticleSectionVO> articleSectionList = new ArrayList<>();
        for(ArticleCategoryVO obj : articleCategoryList) {
            List<CourseInfo> courseList = courseService.getCoursesListByType(obj.getCategoryIds());
            if(courseList!=null){
                for (CourseInfo course : courseList) {
                    List<CourseSectionInfo> courseSectionList = courseSectionService.getCourseSectionListByType(String.valueOf(course.getCourseId()));
                    if(courseSectionList!=null){
                        for (CourseSectionInfo courseSection : courseSectionList) {
                            String courseSectionIds = obj.getCategoryIds()+"-"+course.getCourseId()+","+courseSection.getSectionId();
                            String courseSectionNames = "类别:("+obj.getTitles()+")-课程章节：("+course.getCourseName()+"-"+courseSection.getSectionName()+")";
                            ArticleSectionVO articleSectionVO = new ArticleSectionVO();
                            articleSectionVO.setCourseAndSectionIds(courseSectionIds);
                            articleSectionVO.setCourseAndSectionNames(courseSectionNames);
                            articleSectionList.add(articleSectionVO);
                        }
                    }
                }
            }
        }
        Article article = articleService.getArticleById(blogId);
        String checkValue = article.getBlogType()+"-"+article.getCourseSectionId();
        model.addAttribute("contents",article);
        model.addAttribute("checkValue",checkValue);
        model.addAttribute("articleCategoryList",articleSectionList);
        return new ModelAndView("article/article_edit");
    }

    @PostMapping("/modify")
    @ResponseBody
    public R modifyArticle(@RequestParam(name = "cid", required = true) Integer cid,
                                     @RequestParam(name = "title", required = true) String title,
                                     @RequestParam(name = "titlePic", required = false) String titlePic,
                                     @RequestParam(name = "slug", required = false) String slug,
                                     @RequestParam(name = "content", required = true) String content,
                                     @RequestParam(name = "type", required = true) String type,
                                     @RequestParam(name = "status", required = true) String status,
                                     @RequestParam(name = "tags", required = false) String tags,
                                     @RequestParam(name = "allowComment", required = true) Boolean allowComment) {
        try{
            Article article = articleService.getArticleById(cid);
            article.setTitle(title);
            article.setContent(content);
            article.setUpdateTime(DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
            String[] split = type.split("-");
//            String[] sectionId = split[1].split(",");
            article.setBlogType(split[0]);
            article.setCourseSectionId(split[1]);
//            article.setCheckStatus(status);
//            article.setViews(String.valueOf(1));
//            article.setComments(String.valueOf(0));
//            article.setLikes(String.valueOf(0));
//            article.setIsOpen(String.valueOf(0));
            // 只允许博客文章有分类，防止作品被收入分类
            article.setTags(tags);

            article.setAllowComment(allowComment ? String.valueOf(1) : String.valueOf(0));
            //更新文章
            articleService.update(article);
        }catch (InternalError e){
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    @PostMapping(value = "/publish")
    @ResponseBody
    public R publishArticle(@RequestParam(name = "title", required = true) String title,
                                      @RequestParam(name = "titlePic", required = false) String titlePic,
                                      @RequestParam(name = "slug", required = false) String slug,
                                      @RequestParam(name = "content", required = true) String content,
                                      @RequestParam(name = "type", required = true) String type,
                                      @RequestParam(name = "status", required = true) String status,

                                      @RequestParam(name = "tags", required = false) String tags,
                                      @RequestParam(name = "allowComment", required = true) Boolean allowComment,
                                        HttpSession session) {
        try{
            AdminInfo admin  = (AdminInfo) session.getAttribute(WebConst.LOGIN_SESSION_KEY);
            Article article = new Article();
            article.setTitle(title);
//            article.setSlug(slug);
            article.setAuthorId(admin.getAdminId());
            article.setContent(content);
            article.setCreateTime(DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
            article.setPublishTime(DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
            String[] split = type.split("-");
//            String[] sectionId = split[1].split(",");
            article.setBlogType(split[0]);
            article.setCourseSectionId(split[1]);
            article.setCheckStatus(status);
            article.setViews(String.valueOf(1));
            article.setComments(String.valueOf(0));
            article.setLikes(String.valueOf(0));
            article.setIsOpen(String.valueOf(1));
            // 只允许博客文章有分类，防止作品被收入分类
            article.setTags(tags);

            article.setAllowComment(allowComment ? String.valueOf(1) : String.valueOf(0));

            System.out.println(article.toString());
            // 添加文章
            articleService.save(article);
        }catch (InternalError e){
            return R.error(e.getMessage());
        }

        return R.ok();
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ResponseBody
//    @RequiresPermissions("generator:category:delete")
    public R save(AdminInfo adminInfo) {
        log.info("保存会员="+adminInfo);
        try {
            adminInfo.setCreateTime(DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
            adminInfo.setStatus(0);
            boolean save = adminService.save(adminInfo);
        } catch (Exception e) {
            log.error("保存异常：", e);
            return R.error();
        }
        return R.ok();
    }


    /**
     * 删除
     */
    @PostMapping("/delete")
    @ResponseBody
//    @RequiresPermissions("generator:category:delete")
    public R delete(@RequestParam String blogIds) {
        log.info("删除blogIds：{}" , blogIds);
        try {
            //Map<String, String> stringObjectMap = GsonUtils.toMaps(categoryIds);
            boolean flag = articleService.remove(blogIds);
            if(flag){
                log.info("删除成功！-{}", flag);
            }else{
                log.error("删除失败");
                return R.error();
            }
        } catch (Exception e) {
            log.error("删除异常：", e);
            return R.error();
        }
        return R.ok();
    }

}
