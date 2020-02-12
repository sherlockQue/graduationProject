
package com.a.modules.sys.dao;

import java.util.List;

import com.a.modules.sys.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 部门管理
 *
 */
public interface SysDeptDao extends BaseMapper<SysDept> {

	/**
	 * 查询所有部们
	 * @param params
	 * @return
	 */
    List<SysDept> AqueryList();

    /**
     * 查询第一级子部门ID列表
     * @param parentId  上级部门ID
     */
    List<Long> AqueryDetpIdList(Long parentId);

}
