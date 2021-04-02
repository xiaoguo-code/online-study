package com.gyr.studymanage.render.controller;

import com.gyr.studycommon.entity.CourseInfo;
import com.gyr.studycommon.util.DateUtils;
import com.gyr.studycommon.util.GsonUtils;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.util.R;
import com.gyr.studycommon.vo.CourseVO;
import com.gyr.studymanage.render.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @desc: 课程管理
 * @Author: guoyr
 * @Date: 2021-03-27 14:20
 */
@RestController
@RequestMapping("/course")
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 根据条件获取课程列表(分页）
     */
    @PostMapping("/list")
    public R listCourseByConditionPage(@RequestBody CourseVO conditions) {
        log.info("1、根据条件获取课程列表");
        PageUtils coursesListByCondition = null;
        try {
             coursesListByCondition = courseService.getCoursesListByCondition(conditions);
        } catch (Exception e) {
            log.error("根据条件获取课程列表异常：", e);
            return R.error();
        }
        return R.ok().put("page", coursesListByCondition);
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
    public R save(@RequestBody CourseInfo courseInfo) {
        log.info("1、课程-save-参数：{}", GsonUtils.toJSON(courseInfo));
        try {
            courseInfo.setCreateTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
//            coursesInfo.setIsDelete("0");
            boolean flag = courseService.save(courseInfo);
            log.info("课程新增成功！-{}", flag);
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
    public R update(@RequestBody CourseInfo courseInfo) {
        log.info("1、课程-update-参数：{}", GsonUtils.toJSON(courseInfo));
        try {
            courseInfo.setUpdateTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
            boolean flag = courseService.update(courseInfo);
            log.info("课程更新成功！-{}", flag);
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
    public R delete(@RequestBody String coursesIds) {
        log.info("课程删除coursesIds：{}" + coursesIds);
        try {
            Map<String, String> stringObjectMap = GsonUtils.toMaps(coursesIds);
            boolean flag = courseService.remove(stringObjectMap.get("coursesIds"));
            log.info("课程删除成功！-{}", flag);
        } catch (Exception e) {
            log.error("课程除异常：", e);
            return R.error();
        }
        return R.ok();
    }
    
}
