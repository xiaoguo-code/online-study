package com.gyr.studybusiness.render.controller;

import java.util.Arrays;
import java.util.Map;

import com.gyr.studybusiness.render.service.CategoryService;
import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;





}
