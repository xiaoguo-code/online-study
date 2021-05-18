package com.gyr.studybusiness.render.service;

import com.gyr.studycommon.entity.CommentInfo;
import com.gyr.studycommon.util.PageUtils;

import java.util.List;


/**
 * @desc:
 * @Author: guoyr
 * @Date: 2021-03-14 18:51
 */
public interface CommentService {

    PageUtils getAllCommentByCondition(CommentInfo commentInfo);


    /**
     * 根据评论归属类别id获取所有层级的评论信息
     * @param courseSectionId
     * @return
     */
    List<CommentInfo> getChildCommentByParentId(Integer courseSectionId);

    List<CommentInfo> findAllComment(CommentInfo conditions);

    /**
     * 根据评论id获取评论信息
     * @param commentId
     * @return
     */
    CommentInfo getCommentInfoById(Integer commentId);

    /**
     * 新增评论
     * @param commentInfo
     * @return
     */
    boolean save(CommentInfo commentInfo);

    /**
     * 更新评论
     * @param commentInfo
     * @return
     */
    boolean update(CommentInfo commentInfo);

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     * @param commentIds
     * @return
     */
    boolean remove(String commentIds);
}
