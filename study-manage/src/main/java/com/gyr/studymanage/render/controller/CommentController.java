package com.gyr.studymanage.render.controller;

import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.entity.CommentInfo;
import com.gyr.studycommon.util.DateUtils;
import com.gyr.studycommon.util.GsonUtils;
import com.gyr.studycommon.util.R;
import com.gyr.studycommon.vo.CategoryVO;
import com.gyr.studycommon.vo.CommentVO;
import com.gyr.studymanage.render.service.CategoryService;
import com.gyr.studymanage.render.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author guoyr
 */
@RestController
@RequestMapping("/admin/comment")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 根据评论归属类别id获取所有层级的评论信息
     */
    @RequestMapping("/listChild/{courseSectionId}")
//    @RequiresPermissions("generator:category:list")
    public R listChildByParentId(@PathVariable("courseSectionId") Integer courseSectionId) {
        log.info("1、根据父级类别id:{}，查询类别信息!", courseSectionId);
        List<CommentInfo> childCommentList = null;
        try {
            childCommentList = commentService.getChildCommentByParentId(courseSectionId);
        } catch (Exception e) {
            log.error("根据父级类别id查询异常：", e);
            return R.error();
        }
        return R.ok().put("childCommentList", childCommentList);
    }

    /**
     * 根据id获取类别信息
     */
    @GetMapping("/info/{commentId}")
//    @RequiresPermissions("generator:category:info")
    public R info(@PathVariable("commentId") Integer commentId) {
        log.info("1、根据该id:{}，查询评论信息!", commentId);
        CommentInfo commentInfo = null;
        try {
            commentInfo = commentService.getCommentInfoById(commentId);
        } catch (Exception e) {
            log.error("根据id获取评论信息异常：", e);
            return R.error();
        }
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(commentInfo, commentVO);
        log.info("根据评论id：{}查询成功！", commentId);
        return R.ok().put("commentVO", commentVO);
    }

    /**
     * 评论
     */
    @PostMapping("/save")
    //@RequiresPermissions("generator:category:save")
    public R save(@RequestBody CommentInfo commentInfo) {
        log.info("1、评论-save-参数：{}", GsonUtils.toJSON(commentInfo));
        try {
            commentInfo.setCreateTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
            boolean flag = commentService.save(commentInfo);
            log.info("评论新增成功！-{}", flag);
        } catch (Exception e) {
            log.error("评论新增异常：", e);
            return R.error();
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
//    @RequiresPermissions("generator:category:update")
    public R update(@RequestBody CommentInfo commentInfo) {
        log.info("1、评论-update-参数：{}", GsonUtils.toJSON(commentInfo));
        try {
            commentInfo.setUpdateTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
            boolean flag = commentService.update(commentInfo);
            log.info("评论更新成功！-{}", flag);
        } catch (Exception e) {
            log.error("评论更新异常：", e);
            return R.error();
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
//    @RequiresPermissions("generator:category:delete")
    public R delete(@RequestBody String commentIds) {
        log.info("评论删除commentIds：{}" + commentIds);
        try {
            Map<String, String> stringObjectMap = GsonUtils.toMaps(commentIds);
            boolean flag = commentService.remove(stringObjectMap.get("commentIds"));
            log.info("评论删除成功！-{}", flag);
        } catch (Exception e) {
            log.error("评论除异常：", e);
            return R.error();
        }
        return R.ok();
    }


}
