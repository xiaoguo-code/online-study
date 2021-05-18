package com.gyr.studybusiness.render.controller;

import com.gyr.studybusiness.render.service.*;
import com.gyr.studybusiness.utils.Commons;
import com.gyr.studycommon.dao.ArticleBrowseMapper;
import com.gyr.studycommon.entity.*;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.vo.ArticleVO;
import com.gyr.studycommon.vo.CategoryConditionVO;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @desc: 主页控制器
 * @Author: guoyr
 * @Date: 2021-05-13 0:27
 */

@Controller
@Slf4j
@RequestMapping("/business/main")
public class MainController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseSectionService courseSectionService;

    @Autowired
    SectionTagService sectionTagService;

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;
    @Autowired
    ArticleBrowseService articleBrowseService;

    @GetMapping("/index")
    public ModelAndView toIndex(Model model){
//        CategoryConditionVO conditionVO = new CategoryConditionVO();
//        conditionVO.setParentId(0);
        List<CategoryInfo> categoryListOne = categoryService.getParentCategory(0);
        List<CategoryInfo> categoryListTwo = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(categoryListOne)){
            CategoryInfo obj1 = categoryListOne.get(0);
            model.addAttribute("parentId",obj1.getCategoryId());
//            conditionVO.setParentId(obj1.getCategoryId());
            categoryListTwo = categoryService.getParentCategory(obj1.getCategoryId());
        }
        model.addAttribute("one",categoryListOne);
        model.addAttribute("two",categoryListTwo);
        return new ModelAndView("index","indexModel",model);
    }

    @RequestMapping("/chooseOneCategory")
    public ModelAndView chooseOneCategory(Integer categoryId,Model model){
        CategoryConditionVO conditionVO = new CategoryConditionVO();
        conditionVO.setParentId(categoryId);
        List<CategoryInfo> categoryListTwo = categoryService.getCategoryByCondition(conditionVO);

        model.addAttribute("two",categoryListTwo);
        model.addAttribute("parentId",categoryId);
        return new ModelAndView("index::#courseListTable","indexModel",model);
    }


    /**
     * 点击某一学科，获取对应阶段、课程信息
     */
    @RequestMapping("/chooseTwoCategory/{twoId}")
    public ModelAndView chooseTwoCategory(@PathVariable("twoId") Integer twoId,
                                          Model model){
        CategoryInfo twoObj = categoryService.getCategoryInfoById(twoId);
        CategoryInfo oneObj = categoryService.getCategoryInfoById(Integer.parseInt(twoObj.getParentId()));


        List<CategoryInfo> threeList = categoryService.getParentCategory(twoId);
        Integer parentId = 0;
        String categoryIds = oneObj.getCategoryId()+","+twoId;
        if(CollectionUtils.isNotEmpty(threeList)){
            CategoryInfo threeObj = threeList.get(0);
            parentId = threeObj.getCategoryId();
            categoryIds = categoryIds+","+threeObj.getCategoryId();
            List<CourseInfo> courseList = courseService.getCoursesListByType(categoryIds);
            model.addAttribute("courseList",courseList);
            if(CollectionUtils.isNotEmpty(courseList)){
                CourseInfo courseInfo = courseList.get(0);
                List<CourseSectionInfo> courseSectionList = courseSectionService.getCourseSectionListByType(String.valueOf(courseInfo.getCourseId()));
                model.addAttribute("courseSectionList",courseSectionList);
            }

        }
//        List<CourseSectionInfo> courseSectionList = new ArrayList<>();
        model.addAttribute("one",oneObj);
        model.addAttribute("two",twoObj);
        model.addAttribute("threeList",threeList);
        model.addAttribute("parentId",parentId);
//        model.addAttribute("courseSectionList",courseSectionList);
       return new ModelAndView("courseLine","indexTwoModel",model);
    }

    /**
     * 点击学科的某一个阶段，显示对应课程和章节
     */
    @RequestMapping("/chooseThreeCategory")
    public ModelAndView chooseThreeCategory(Integer threeId,
                                          Model model){
        CategoryInfo threeObj = categoryService.getCategoryInfoById(threeId);
        CategoryInfo twoObj = categoryService.getCategoryInfoById(Integer.parseInt(threeObj.getParentId()));
        CategoryInfo oneObj = categoryService.getCategoryInfoById(Integer.parseInt(twoObj.getParentId()));

        String categoryIds = oneObj.getCategoryId()+","+twoObj.getCategoryId()+","+threeId;

        List<CourseInfo> courseList = courseService.getCoursesListByType(categoryIds);
        if(CollectionUtils.isNotEmpty(courseList)){
            CourseInfo courseInfo = courseList.get(0);
            List<CourseSectionInfo> courseSectionList = courseSectionService.getCourseSectionListByType(String.valueOf(courseInfo.getCourseId()));
            model.addAttribute("courseSectionList",courseSectionList);
        }

        model.addAttribute("courseList",courseList);
//        model.addAttribute("one",oneObj);
//        model.addAttribute("two",twoObj);
//        model.addAttribute("threeList",threeList);
        return new ModelAndView("courseLine::#showTable1","indexTwoModel",model);
    }
    /**
     * 点击某一个课程，显示对应章节信息
     */
    @RequestMapping("/chooseCourse/{courseId}")
    public ModelAndView chooseCourse(@PathVariable("courseId") Integer courseId,
                                            Model model){
        List<CourseSectionInfo> courseSectionList = courseSectionService.getCourseSectionListByType(String.valueOf(courseId));

        model.addAttribute("courseSectionList",courseSectionList);

        return new ModelAndView("courseLine::#courseTable","indexTwoModel",model);
    }

    /**
     * 点击某一章节，跳到对应的博客页面
     */
    @RequestMapping("/chooseSection/{sectionId}")
    public ModelAndView chooseSection(@PathVariable("sectionId") Integer sectionId,
                                     Model model){
        //根据该章节id，获取章节对象，得到课程id
        CourseSectionInfo sectionInfo = courseSectionService.getCourseSectionInfoById(sectionId);
        //获取课程对象
        CourseInfo courseInfo = courseService.getCoursesInfoById(sectionInfo.getCourseId());
        //获取本章节tag
        SectionTagInfo sectionTagInfo = sectionTagService.getSectionTagInfoBySectionId(sectionId);
        if(StringUtils.isNotBlank(sectionTagInfo.getTagName())){
            String[] tags = sectionTagInfo.getTagName().split(",");
            List<String> tagList = Arrays.asList(tags);
            model.addAttribute("tagList",tagList);
        }
        //获取本章节文章
        String courseSectionId = courseInfo.getCourseId()+","+sectionId;
        ArticleVO articleVO = new ArticleVO();
        articleVO.setCourseSectionId(courseSectionId);
        PageUtils page = articleService.getArticlePageByCondition(articleVO);
        List<Article> list = (List<Article>) page.getList();
        if(CollectionUtils.isNotEmpty(list)){
            for (Article blog :
                    list) {
                CommentInfo conditions = new CommentInfo();
                conditions.setBlogId(blog.getBlogId());
                List<CommentInfo> allComment = commentService.findAllComment(conditions);
                if(CollectionUtils.isNotEmpty(allComment)){
                    blog.setCommentQuantity(String.valueOf(allComment.size()));
                }
                List<ArticleBrowse> browseLsit = articleBrowseService.findBrowseQuantityByBlogId(blog.getBlogId());
                if(CollectionUtils.isNotEmpty(browseLsit)){
                    blog.setBrowseQuantity(String.valueOf(browseLsit.size()));
                }
                String s = Commons.subStr(blog.getContent(), 250);
                blog.setContent(Commons.article(s));
            }
        }

        model.addAttribute("category",courseInfo.getCourseName()+"-"+sectionInfo.getSectionName());
        model.addAttribute("courseInfo",courseInfo);
        model.addAttribute("sectionInfo",sectionInfo);
        model.addAttribute("sectionTagInfo",sectionTagInfo);
        model.addAttribute("blogList",page.getList());
        model.addAttribute("page",page);
        return new ModelAndView("blog/layout");
    }
}
