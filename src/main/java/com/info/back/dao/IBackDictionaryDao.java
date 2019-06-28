/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: IBackDictionary
 * Author:   Liubing
 * Date:     2018/5/11 16:08
 * Description: 字典表查询
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.info.back.dao;

import com.info.web.pojo.statistics.BackDictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈字典表查询〉
 *
 * @author Liubing
 * @create 2018/5/11
 * @since 1.0.0
 */
public interface IBackDictionaryDao {
    List<BackDictionary> findDictionary(String dictionary);

    List<BackDictionary> findAllLabelType();

    List<BackDictionary> findDictionarys(HashMap<String, Object> params);

    List<BackDictionary> findLabelType(String dictionary);
}
