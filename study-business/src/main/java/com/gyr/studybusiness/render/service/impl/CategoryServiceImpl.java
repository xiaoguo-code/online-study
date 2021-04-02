package com.gyr.studybusiness.render.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.gyr.studybusiness.render.service.CategoryService;
import com.gyr.studycommon.dao.CategoryMapper;
import com.gyr.studycommon.entity.Category;
import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryInfo> findAllCategory() {
        List<CategoryInfo> treeList = new ArrayList<>();
        Wrapper<CategoryInfo> categoryInfoWrapper = new QueryWrapper<>();
        List<CategoryInfo> listCategoryInfo=categoryMapper.selectList(categoryInfoWrapper); //查处所有的数据
        for (CategoryInfo parent : listCategoryInfo) { //遍历
            if (parent.getParentId().equals("0")) { //找到parentId为零的节点，即一级目录，
                treeList.add(findChildren(parent, listCategoryInfo));
            }
        }
        return treeList;
    }

    //寻找子节点
    private static CategoryInfo findChildren(CategoryInfo parent, List<CategoryInfo> listGoodsCategory) {
        for (CategoryInfo child : listGoodsCategory) {
            if (parent.getCategoryId().equals(child.getParentId())) {
                if (parent.getChildrenList() == null) {
                    parent.setChildrenList(new ArrayList<CategoryInfo>());
                }
                parent.getChildrenList().add(findChildren(child, listGoodsCategory));
            }
        }
        return parent;
    }


    @Override
    public boolean save(CategoryInfo categoryInfo) {
        int insert = categoryMapper.insert(categoryInfo);
        return insert>0;
    }

    @Override
    public boolean update(CategoryInfo categoryInfo) {
        return false;
    }

    @Override
    public boolean remove(int categoryId) {
        return false;
    }
}