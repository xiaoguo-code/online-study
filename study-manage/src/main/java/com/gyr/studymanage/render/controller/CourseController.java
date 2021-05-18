package com.gyr.studymanage.render.controller;

import com.gyr.studycommon.entity.*;
import com.gyr.studycommon.util.DateUtils;
import com.gyr.studycommon.util.GsonUtils;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.util.R;
import com.gyr.studycommon.vo.CourseSectionVO;
import com.gyr.studycommon.vo.CourseVO;
import com.gyr.studymanage.render.service.CategoryService;
import com.gyr.studymanage.render.service.CourseSectionService;
import com.gyr.studymanage.render.service.CourseService;
import com.gyr.studymanage.render.service.SectionTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @desc: 课程管理
 * @Author: guoyr
 * @Date: 2021-03-27 14:20
 */
@Controller
@RequestMapping("/admin/course")
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CourseSectionService courseSectionService;

    @Autowired
    private SectionTagService sectionTagService;

    /**
     * 跳转到课程新增或编辑界面
     * @param courseId
     * @param model
     * @return
     */
    @GetMapping("/editCoursePage")
    public ModelAndView toEditCoursePage(Integer courseId,Model model){
        if(courseId==null){
            return new ModelAndView("course/add_course","editCourseModel",model);
        }else {
            //编辑页
            CourseInfo courseInfo = courseService.getCoursesInfoById(courseId);
            Integer courseId1 = courseInfo.getCourseId();
            String categoryIdsStr = courseInfo.getCategoryIds();
            String[] categoryIds = categoryIdsStr.split(",");
            List<CourseSectionInfo> sectionInfos = courseSectionService.getCourseSectionListByType(String.valueOf(courseId1));
            List<SectionTagInfo> tagInfos = new ArrayList<>();
            for (int i = 0; i < sectionInfos.size(); i++) {
                CourseSectionInfo sectionInfo = sectionInfos.get(i);
                SectionTagInfo sectionTag = sectionTagService.getSectionTagInfoBySectionId(sectionInfo.getSectionId());
                tagInfos.add(sectionTag);
            }
            List<CategoryInfo> oneList = categoryService.getParentCategory(0);
            List<CategoryInfo> twoList = categoryService.getParentCategory(Integer.parseInt(categoryIds[0]));
            List<CategoryInfo> threeList = categoryService.getParentCategory(Integer.parseInt(categoryIds[1]));
            model.addAttribute("oneList",oneList);
            model.addAttribute("twoList",twoList);
            model.addAttribute("threeList",threeList);
            model.addAttribute("one", categoryIds[0]);
            model.addAttribute("two", categoryIds[1]);
            model.addAttribute("three", categoryIds[2]);
            model.addAttribute("courseInfo", courseInfo);
            model.addAttribute("sectionInfos", sectionInfos);
            model.addAttribute("tagInfos", tagInfos);
            return new ModelAndView("course/edit_course", "editCourseModel", model);
        }
    }



    /**
     * 根据条件获取课程列表(分页）
     */
    @GetMapping("/list")
    public ModelAndView listCourseByConditionPage(@RequestParam(value = "async",required = false) boolean async,
                                                  CourseVO conditions,
                                                  Model model) {
        log.info("1、根据条件获取课程列表");
        PageUtils page = null;
        List<CourseVO> courseVOS = new ArrayList<>();
        try {
            page = courseService.getCoursesListByCondition(conditions);
            List<CourseInfo> courseList = (List<CourseInfo>) page.getList();
            for (int i = 0; i < courseList.size(); i++) {
                CourseVO courseVO = new CourseVO();
                CourseInfo obj = courseList.get(i);
                BeanUtils.copyProperties(obj,courseVO);
                String[] categoryIds = obj.getCategoryIds().split(",");
                CategoryInfo one = categoryService.getCategoryInfoById(Integer.parseInt(categoryIds[0]));
                CategoryInfo two = categoryService.getCategoryInfoById(Integer.parseInt(categoryIds[1]));
                CategoryInfo three = categoryService.getCategoryInfoById(Integer.parseInt(categoryIds[2]));
                String levelNames = one.getTitle()+","+two.getTitle()+","+three.getTitle();
                courseVO.setLevelName(levelNames);
                courseVOS.add(courseVO);
            }
        } catch (Exception e) {
            log.error("根据条件获取课程列表异常：", e);
        }
        model.addAttribute("page",page);
        model.addAttribute("courseList",courseVOS);
        model.addAttribute("parentId",conditions.getCategoryIds());

        return new ModelAndView(!async?"course/courseList":"course/courseList::#tableBox",
                "courseListModel",model);
    }

    /**
     * 根据条件获取课程列表(分页）
     */
    @PostMapping("/listByType")
    public R listCourseByCondition(@RequestBody String courseType) {
        log.info("1、根据条件获取课程列表");
        List<CourseInfo> courseInfoList = null;
        try {
            courseInfoList = courseService.getCoursesListByType(courseType);
        } catch (Exception e) {
            log.error("根据条件获取课程列表异常：", e);
            return R.error();
        }
        return R.ok().put("courseList", courseInfoList);
    }

    /**
     * 根据课程id获取课程信息
     */
    @GetMapping("/info/{courseId}")
    public R info(@PathVariable("courseId") Integer courseId) {
        log.info("1、根据该id:{}，查询课程信息!", courseId);
        CourseInfo courseInfo = null;
        try {
            courseInfo = courseService.getCoursesInfoById(courseId);
        } catch (Exception e) {
            log.error("根据id获取课程信息异常",  e);
            return R.error();
        }
        CourseVO courseVO = new CourseVO();
        BeanUtils.copyProperties(courseInfo, courseVO);
        log.info("根据课程id：{}查询成功！", courseId);
        return R.ok().put("coursesVO", courseVO);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ResponseBody
    public R save(CourseVO courseVO) {
        log.info("1、课程-save-参数：{}", GsonUtils.toJSON(courseVO));
        try {
            courseVO.setCreateTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
            CourseInfo courseInfo = new CourseInfo();
            BeanUtils.copyProperties(courseVO,courseInfo);
            CourseInfo oldObj = courseService.getCoursesInfoByCondition(courseInfo.getCategoryIds(), courseInfo.getCourseName());
            if(oldObj!=null){
                return R.error("课程已存在");
            }
            //课程信息入库
            courseService.save(courseInfo);
            CourseInfo course = courseService.getCoursesInfoByCondition(courseInfo.getCategoryIds(), courseInfo.getCourseName());
            //获取章节列表
            String[] sectionNames = courseVO.getCourseSectionNames().split(",");
            String[] sectionDescribes = courseVO.getSectionDescribes().split(",");
            //获取标签列表
            String[] sectionTags = courseVO.getCourseSectionTagNames().split("-");
            //章节信息入库
            for (int i = 0; i < sectionNames.length; i++) {
                String name = sectionNames[i];
                String sectionDescribe = sectionDescribes[i];
                CourseSectionInfo  courseSectionInfo  = new CourseSectionInfo();
                courseSectionInfo.setCourseId(course.getCourseId());
                courseSectionInfo.setSectionName(name);
                courseSectionInfo.setSectionDescribes(sectionDescribe);
                courseSectionInfo.setPriority(i+1);
                courseSectionInfo.setCreateTime(courseVO.getCreateTime());
                courseSectionService.save(courseSectionInfo);
                CourseSectionInfo section = courseSectionService.getCourseSectionListByCondition(courseSectionInfo);
                //章节对应标签入库
                String tagName = sectionTags[i];
                SectionTagInfo sectionTagInfo = new SectionTagInfo();
                sectionTagInfo.setCategoryIds(courseVO.getCategoryIds());
                sectionTagInfo.setCourseId(course.getCourseId());
                sectionTagInfo.setSectionId(section.getSectionId());
                sectionTagInfo.setTagName(tagName);
                sectionTagInfo.setCreateTime(courseVO.getCreateTime());
                sectionTagService.save(sectionTagInfo);
            }
        } catch (Exception e) {
            log.error("课程新增异常：", e);
            return R.error();
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ResponseBody
    public R update(CourseVO courseVO) {
        log.info("1、课程-update-参数：{}", GsonUtils.toJSON(courseVO));
        try {
            courseVO.setUpdateTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
            CourseInfo courseInfo = new CourseInfo();
            BeanUtils.copyProperties(courseVO,courseInfo);
            courseService.update(courseInfo);
            //章节更新
            String sectionIdValuesStr = courseVO.getSectionIdValues();
            String[] sectionIdValues = sectionIdValuesStr.split("=");
            String[] sectionIds = sectionIdValues[0].split(",");
            String[] sectionValues = sectionIdValues[1].split(",");
            //标签更新
            String sectionTagIdValues = courseVO.getSectionTagIdValues();
            String[] tagIdValues = sectionTagIdValues.split("=");
            String[] tagIds = tagIdValues[0].split(",");
            String[] tagValues = tagIdValues[1].split("-");
            for (int i = 0; i < sectionIds.length; i++) {
                String sectionId = sectionIds[i];
                if (("newSection").equals(sectionId)) {
                    //新增操作
                    CourseSectionInfo courseSectionInfo = new CourseSectionInfo();
                    courseSectionInfo.setCourseId(courseVO.getCourseId());
                    courseSectionInfo.setSectionName(sectionValues[i]);
                    courseSectionInfo.setPriority(i + 1);
                    courseSectionInfo.setCreateTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
                    courseSectionService.save(courseSectionInfo);
                    CourseSectionInfo section = courseSectionService.getCourseSectionListByCondition(courseSectionInfo);
                    String tagId = tagIds[i];
                    if (("newTag").equals(tagId)) {
                        //新增操作
                        SectionTagInfo sectionTagInfo = new SectionTagInfo();
                        sectionTagInfo.setCategoryIds(courseVO.getCategoryIds());
                        sectionTagInfo.setCourseId(courseVO.getCourseId());
                        sectionTagInfo.setSectionId(section.getSectionId());
                        sectionTagInfo.setTagName(tagValues[i]);
                        sectionTagInfo.setCreateTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
                        sectionTagService.save(sectionTagInfo);
                    }
                } else {
                    CourseSectionInfo courseSectionInfo = new CourseSectionInfo();
                    courseSectionInfo.setSectionId(Integer.parseInt(sectionId));
                    courseSectionInfo.setSectionName(sectionValues[i]);
                    courseSectionInfo.setUpdateTime(courseVO.getUpdateTime());
                    courseSectionService.update(courseSectionInfo);
                    String tagId = tagIds[i];
                    //更新操作
                    SectionTagInfo sectionTagInfo = new SectionTagInfo();
                    sectionTagInfo.setSectionTagId(Integer.parseInt(tagId));
                    sectionTagInfo.setCategoryIds(courseVO.getCategoryIds());
                    sectionTagInfo.setCourseId(courseVO.getCourseId());
                    sectionTagInfo.setSectionId(Integer.parseInt(sectionId));
                    sectionTagInfo.setTagName(tagValues[i]);
                    sectionTagInfo.setUpdateTime(courseVO.getUpdateTime());
                    sectionTagService.update(sectionTagInfo);
                }
            }
            log.info("课程更新成功！");
        } catch (Exception e) {
            log.error("课程更新异常：", e);
            return R.error();
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ResponseBody
    public R delete( String coursesIds) {
        log.info("课程删除coursesIds：{}" + coursesIds);
        try {
//            Map<String, String> stringObjectMap = GsonUtils.toMaps(coursesIds);
            boolean flag = courseService.remove(coursesIds);

            log.info("课程删除成功！-{}", flag);
        } catch (Exception e) {
            log.error("课程除异常：", e);
            return R.error();
        }
        return R.ok();
    }

}
