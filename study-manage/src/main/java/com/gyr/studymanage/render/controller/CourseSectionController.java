package com.gyr.studymanage.render.controller;

import com.gyr.studycommon.entity.CourseSectionInfo;
import com.gyr.studycommon.util.DateUtils;
import com.gyr.studycommon.util.GsonUtils;
import com.gyr.studycommon.util.R;
import com.gyr.studycommon.vo.CourseSectionVO;
import com.gyr.studymanage.render.service.CourseSectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @desc: 课程章节管理
 * @Author: guoyr
 * @Date: 2021-03-27 16:17
 */
@RestController
@Slf4j
@RequestMapping("/admin/courseSection")
public class CourseSectionController {

    @Autowired
    private CourseSectionService courseSectionService;

    /**
     * 根据课程id获取课程章节列表
     */
    @PostMapping("/listByCourseId")
    public R listCourseByCondition(@RequestBody String courseId) {
        log.info("1、根据条件获取课程章节列表");
        List<CourseSectionInfo> courseSectionInfos = null;
        try {
            courseSectionInfos = courseSectionService.getCourseSectionListByType(courseId);
        } catch (Exception e) {
            log.error("根据条件获取课程章节列表异常：", e);
            return R.error();
        }
        return R.ok().put("courseSectionInfos", courseSectionInfos);
    }

    /**
     * 根据课程章节id获取课程章节信息
     */
    @GetMapping("/info/{courseId}")
    public R info(@PathVariable("courseId") Integer courseSectionId) {
        log.info("1、根据该id:{}，查询课程章节信息!", courseSectionId);
        CourseSectionInfo courseSectionInfo = null;
        try {
             courseSectionInfo = courseSectionService.getCourseSectionInfoById(courseSectionId);
        } catch (Exception e) {
            log.error("根据id获取课程章节信息异常",  e);
            return R.error();
        }
        CourseSectionVO courseSectionVO = new CourseSectionVO();
        BeanUtils.copyProperties(courseSectionInfo, courseSectionVO);
        log.info("根据课程章节id：{}查询成功！", courseSectionId);
        return R.ok().put("courseSectionVO", courseSectionVO);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CourseSectionInfo courseSectionInfo) {
        log.info("1、课程章节-save-参数：{}", GsonUtils.toJSON(courseSectionInfo));
        try {
            courseSectionInfo.setCreateTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
            boolean flag = courseSectionService.save(courseSectionInfo);
            log.info("课程章节新增成功！-{}", flag);
        } catch (Exception e) {
            log.error("课程章节新增异常：", e);
            return R.error();
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody CourseSectionInfo courseSectionInfo) {
        log.info("1、课程章节-update-参数：{}", GsonUtils.toJSON(courseSectionInfo));
        try {
            courseSectionInfo.setUpdateTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
            boolean flag = courseSectionService.update(courseSectionInfo);
            log.info("课程更章节新成功！-{}", flag);
        } catch (Exception e) {
            log.error("课程章节更新异常：", e);
            return R.error();
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete( String courseSectionIds) {
        log.info("课程章节删除courseSectionIds：{}" + courseSectionIds);
        try {
            Map<String, String> stringObjectMap = GsonUtils.toMaps(courseSectionIds);
            boolean flag = courseSectionService.remove(stringObjectMap.get("courseSectionIds"));
            log.info("课程章节删除成功！-{}", flag);
        } catch (Exception e) {
            log.error("课程章节删除异常：", e);
            return R.error();
        }
        return R.ok();
    }

}
