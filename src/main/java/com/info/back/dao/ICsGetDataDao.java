/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ICsGetDataDao
 * Author:   Liubing
 * Date:     2018/4/25 12:05
 * Description: 获取催收数据
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.info.back.dao;

/**
 * 〈一句话功能简述〉<br> 
 * 〈获取催收数据〉
 *
 * @author Liubing
 * @create 2018/4/25
 * @since 1.0.0
 */
public interface ICsGetDataDao {

    String queryCsDataByAssetOrderId(String assetOrderId);
}
