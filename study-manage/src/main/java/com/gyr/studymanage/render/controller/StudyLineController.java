package com.gyr.studymanage.render.controller;

import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.entity.Course;
import com.gyr.studycommon.entity.CourseInfo;
import com.gyr.studycommon.entity.StudyLineInfo;
import com.gyr.studycommon.util.DateUtils;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.util.R;
import com.gyr.studycommon.vo.CategoryConditionVO;
import com.gyr.studycommon.vo.CourseVO;
import com.gyr.studycommon.vo.StudyLineInfoVO;
import com.gyr.studymanage.render.service.CategoryService;
import com.gyr.studymanage.render.service.CourseService;
import com.gyr.studymanage.render.service.StudyLineService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @desc: 学习路线管理
 * @Author: guoyr
 * @Date: 2021-05-03 18:34
 */

@Controller
@Slf4j
@RequestMapping("/admin/studyLine")
public class StudyLineController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    StudyLineService studyLineService;

    @Autowired
    CourseService courseService;

    /**
     *  根据学科类别获取课程对应的学习路线列表
     */
    @RequestMapping("/listStudyLine")
    public ModelAndView listChildByParentIdNotAjax(@RequestParam(value = "async",required = false) boolean async,
                                                   CategoryConditionVO conditionVO,
                                                   Model model) {
        log.info("1、根据条件获取学习路线信息列表!");
        List<CategoryInfo> childCategoryList = null;
        List<CategoryInfo> levelCategoryList = null;
        List<Course> courseList = null;
        List<StudyLineInfoVO> studyLineInfoVOList = new ArrayList<>();
        if(conditionVO.getPageIndex()==null){
            conditionVO.setPageIndex(1);
        }
        if(conditionVO.getPageSize()==null){
            conditionVO.setPageSize(10);
        }
        try {
//            CategoryConditionVO conditionVO = new CategoryConditionVO();
//            conditionVO.setParentId(parentId);
//            conditionVO.setTitle(name);
            childCategoryList = categoryService.getCategoryByCondition(conditionVO);

            for (CategoryInfo obj :childCategoryList) {
                //获取每个阶段名
                levelCategoryList = categoryService.getParentCategory(obj.getCategoryId());
                for (CategoryInfo obj2 : levelCategoryList) {
                    StudyLineInfoVO studyLineInfoVO = new StudyLineInfoVO();
                    String categoryIds = categoryService.getParentChildIdToStr(String.valueOf(obj2.getCategoryId()));
                    //查询出路线
                    StudyLineInfo studyLineInfo = studyLineService.
                            getStudyLineByCategoryId(categoryIds);
                    if(studyLineInfo!=null){
                        String[] courseIds = studyLineInfo.getCourseIds().split(",");
                        String[] courseNames = studyLineInfo.getCourseNames().split(",");
                        courseList = new ArrayList<>();
                        for(int i = 0;i< courseIds.length;i++){
                            //封装课程对应信息
                            Course course = new Course();
                            course.setCourseId(Integer.getInteger(courseIds[i]));
                            course.setCourseName(courseNames[i]);
                            courseList.add(course);
                        }
                        studyLineInfoVO.setCreateTime(studyLineInfo.getCreateTime());
                        studyLineInfoVO.setUpdateTime(studyLineInfo.getUpdateTime());
                        studyLineInfoVO.setCourseIds(studyLineInfo.getCourseIds());
                        studyLineInfoVO.setCourseNames(studyLineInfo.getCourseNames());
                        studyLineInfoVO.setLineId(studyLineInfo.getLineId());
                        studyLineInfoVO.setCategoryId(studyLineInfo.getCategoryId());
                    }
                    //封装VO对象
                    studyLineInfoVO.setCategoryName(obj.getTitle());
                    studyLineInfoVO.setLevelNames(obj2.getTitle());
                    studyLineInfoVO.setLevelId(obj2.getCategoryId());
                    studyLineInfoVO.setCourseList(courseList);

                    studyLineInfoVOList.add(studyLineInfoVO);
                }
            }
        } catch (Exception e) {
            log.error("根据条件获取学习路线信息列表", e);
        }
        PageUtils page = new PageUtils(conditionVO.getPageSize(), conditionVO.getPageIndex(),
                studyLineInfoVOList);
        model.addAttribute("page",page);
        model.addAttribute("studyLineInfoVOList",page.getList());
        model.addAttribute("parentId",conditionVO.getParentId());
        return new ModelAndView(!async? "studyLine/courseList" : "studyLine/courseList::#tableBox" ,
                "studyLineModel",model);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ResponseBody
//    @RequiresPermissions("generator:category:delete")
    public R delete(StudyLineInfo studyLineInfo) {
        log.info("保存学习路线studyLineIds={}，courseNames={},levelId={}" ,
                studyLineInfo.getLineId(),studyLineInfo.getCourseNames(),studyLineInfo.getCategoryId());
        boolean flag = false;

        try {

            String categoryIds = categoryService.getParentChildIdToStr(studyLineInfo.getCategoryId());
            studyLineInfo.setCategoryId(categoryIds);
            //创建课程
            String[] names = studyLineInfo.getCourseNames().split(",");
            String courseIds = "";
            for (int i= 0; i < names.length; i++) {
                //根据课程名查询课程是否存在
                CourseInfo courseInfo = courseService.getCoursesInfoByCondition(studyLineInfo.getCategoryId(),names[i]);
                if(courseInfo==null){
                    CourseInfo courseInfo1 = new CourseInfo();
                    courseInfo1.setCategoryIds(studyLineInfo.getCategoryId());
                    courseInfo1.setCourseName(names[i]);
                    courseInfo1.setPriority(Integer.toString(i+1));
                    courseService.save(courseInfo1);
                    CourseInfo newObj = courseService.getCoursesInfoByCondition(studyLineInfo.getCategoryId(),names[i]);
                    courseIds = courseIds + newObj.getCourseId()+",";
                }else{
                    courseInfo.setPriority(Integer.toString(i+1));
                    courseService.update(courseInfo);
                    courseIds = courseIds + courseInfo.getCourseId()+",";
                }
            }
            if(studyLineInfo.getLineId()!=null){
                //更新路线
                StudyLineInfo oldLine = studyLineService.getStudyLineById(studyLineInfo.getLineId());
                oldLine.setCourseNames(studyLineInfo.getCourseNames());
                oldLine.setCourseIds(courseIds);
                oldLine.setUpdateTime(DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
                flag = studyLineService.update(studyLineInfo);
                log.info("学习路线修改成功！-{}", flag);
            }else{
                //新增路线
                studyLineInfo.setCourseIds(courseIds);
                studyLineInfo.setCreateTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
                flag = studyLineService.save(studyLineInfo);
                log.info("学习路线新增成功！-{}", flag);
            }
        } catch (Exception e) {
            log.error("学习路线保存异常：", e);
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
    public R delete(@RequestParam String studyLineIds) {
        log.info("删除学习路线studyLineIds：{}" , studyLineIds);
        try {
            //Map<String, String> stringObjectMap = GsonUtils.toMaps(categoryIds);
            boolean flag = studyLineService.delete(studyLineIds);
            if(flag){
                log.info("学习路线删除成功！-{}", flag);
            }else{
                log.error("学习路线除失败");
                return R.error();
            }
        } catch (Exception e) {
            log.error("学习路线除异常：", e);
            return R.error();
        }
        return R.ok();
    }

}
