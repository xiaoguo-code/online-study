package com.gyr.studybusiness.render.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyr.studybusiness.render.service.CommentService;
import com.gyr.studycommon.dao.CommentMapper;
import com.gyr.studycommon.entity.CommentInfo;
import com.gyr.studycommon.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @desc: 课程信息service
 * @Author: guoyr
 * @Date: 2021-03-27 11:11
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentMapper commentMapper;

    /**
     * 递归，寻找子节点
     * @param parent
     * @param commentInfos
     * @return
     */
    private static CommentInfo findChildren(CommentInfo parent, List<CommentInfo> commentInfos) {
        for (CommentInfo child : commentInfos) {
            if (parent.getCommentId().equals(child.getCommentParentId())) {
                if (parent.getChildrenList() == null) {
                    parent.setChildrenList(new ArrayList<>());
                }
                parent.getChildrenList().add(findChildren(child, commentInfos));
            }
        }
        return parent;
    }

    @Override
    public PageUtils getAllCommentByCondition(CommentInfo commentInfo) {
        if(commentInfo.getPageIndex()==null){
            commentInfo.setPageIndex(1);
        }
        if(commentInfo.getPageSize()==null){
            commentInfo.setPageSize(10);
        }
        IPage<CommentInfo> page = new Page<>(commentInfo.getPageIndex(),commentInfo.getPageSize());
        QueryWrapper wrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(commentInfo.getContent())){
            wrapper.like("content",commentInfo.getContent());
        }
        IPage iPage = commentMapper.selectPage(page, wrapper);
        return new PageUtils(iPage);
    }

    /**
     * 根据评论归属类别id获取所有层级的评论信息
     *
     * @param courseSectionId
     * @return
     */
    @Override
    public List<CommentInfo> getChildCommentByParentId(Integer courseSectionId) {
        QueryWrapper wrapper = new QueryWrapper();
        if(courseSectionId!=null){
            wrapper.eq("courseSectionId",courseSectionId);
        }
        List<CommentInfo> treeList = new ArrayList<>();
        List<CommentInfo> commentInfos = commentMapper.selectList(wrapper);
        for(CommentInfo parent : commentInfos){
            //找到parentId为零的节点，即一级目录，
            if (Integer.getInteger("0").equals(parent.getCommentParentId())) {
                treeList.add(findChildren(parent, commentInfos));
            }
        }
        return treeList;
    }

    @Override
    public List<CommentInfo> findAllComment(CommentInfo conditions) {
        QueryWrapper wrapper = new QueryWrapper();
        if(conditions.getBlogId()!=null){
            wrapper.eq("blogId",conditions.getBlogId());
        }

        return commentMapper.selectList(wrapper);
    }

    /**
     * 根据评论id获取评论信息
     *
     * @param commentId
     * @return
     */
    @Override
    public CommentInfo getCommentInfoById(Integer commentId) {
        CommentInfo commentInfo = commentMapper.selectById(commentId);
        return commentInfo;
    }

    /**
     * 新增评论
     *
     * @param commentInfo
     * @return
     */
    @Override
    public boolean save(CommentInfo commentInfo) {
        int i = commentMapper.insert(commentInfo);
        return i>0;
    }

    /**
     * 更新评论
     *
     * @param commentInfo
     * @return
     */
    @Override
    public boolean update(CommentInfo commentInfo) {
        int i = commentMapper.updateById(commentInfo);
        return i>0;
    }

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     *
     * @param commentIds
     * @return
     */
    @Override
    public boolean remove(String commentIds) {
        if(StringUtils.isNotBlank(commentIds)){
            String[] ids = commentIds.split(",");
            int i = commentMapper.deleteBatchIds(Arrays.asList(ids));
            return i>0;
        }
        return false;
    }
}
