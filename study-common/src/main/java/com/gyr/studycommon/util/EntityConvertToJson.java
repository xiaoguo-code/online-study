package com.gyr.studycommon.util;

import com.alibaba.fastjson.JSON;
import com.gyr.studycommon.entity.Course;
import com.gyr.studycommon.entity.CourseSection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @desc: 将实体类转换成JSON字符串
 * @Author: guoyr
 * @Date: 2021-03-27 14:53
 */
public class EntityConvertToJson {

        public static void main(String[] args) throws Exception {
            System.out.println(formatJson(JSON.toJSONString(InitEntity(new CourseSection()))));
        }

        /**
         * 初始化实体类
         */
        private static Object InitEntity(Object entity) throws Exception {
            Field[] fields = entity.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType().getCanonicalName().startsWith("java")) {
                    switch (field.getType().getCanonicalName()) {
                        case "java.lang.String":
                            field.set(entity, "");
                            break;
                        case "java.lang.Integer":
                            field.set(entity, 0);
                            break;
                        case "java.lang.Boolean":
                            field.set(entity, true);
                            break;
                        case "java.util.Date":
                            field.set(entity, new Date());
                            break;
                        case "java.util.List":
                            field.set(entity, new ArrayList<>());
                            break;
                        case "java.util.Map":
                            field.set(entity, new HashMap<>());
                            break;
                        default:
                            // 打印漏掉的字段
                            System.out.println(field.getType().getCanonicalName());
                            break;
                    }
                } else if (field.getType().getCanonicalName().startsWith("com.rc.rancherapi")){
                    Object childDto = Class.forName(field.getType().getCanonicalName()).newInstance();
                    field.set(entity, InitEntity(childDto));
                } else {
                    // 打印漏掉的字段
                    System.out.println(field.getType().getCanonicalName());
                }
            }
            return entity;
        }

        /**
         * 格式化json
         */
        private static String formatJson(String jsonStr) {
            if (null == jsonStr || "".equals(jsonStr)) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            char[] c = jsonStr.toCharArray();
            //缩进数量
            int indent = 0;
            for (int i = 0; i < c.length -1; i++) {
                char current = c[i];

                if (current == '{' && c[i+1] != '}') {
                    sb.append(current);
                    sb.append("\n");
                    indent++;
                    addIndentBlank(sb, indent);
                } else if (current == '}') {
                    if (c[i-1] == '{') {
                        sb.append(current);
                    } else {
                        sb.append("\n");
                        indent--;
                        addIndentBlank(sb, indent);
                        sb.append(current);
                    }
                } else if (current == '[' && c[i+1] != ']') {
                    sb.append(current);
                    sb.append("\n");
                    indent++;
                    addIndentBlank(sb, indent);
                } else if (current == ']') {
                    if (c[i-1] == '[') {
                        sb.append(current);
                    } else {
                        sb.append("\n");
                        indent--;
                        addIndentBlank(sb, indent);
                        sb.append(current);
                    }
                } else if (current == ',') {
                    sb.append(current);
                    if (c[i+1] != '\\') {
                        sb.append("\n");
                        addIndentBlank(sb, indent);
                    }
                } else {
                    sb.append(current);
                }
            }
            sb.append("\n}");
            return sb.toString();
        }

        /**
         * 添加缩进
         */
        private static void addIndentBlank(StringBuilder sb, int indent) {
            for (int i = 0; i < indent; i++) {
                sb.append('\t');
            }
        }
    }
