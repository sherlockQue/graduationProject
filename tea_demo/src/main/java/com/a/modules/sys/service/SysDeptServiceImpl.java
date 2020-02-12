
package com.a.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a.modules.sys.cache.SysDeptCache;
import com.a.modules.sys.dao.SysDeptDao;
import com.a.modules.sys.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDept>  {

	@Autowired
	private SysDeptCache sysDeptCache;

	/**
	 * 查询所有部门
	 */	 
	public List<SysDept> AqueryList() {
		List<SysDept> list = sysDeptCache.listall();
		if (list == null) {
			list = this.baseMapper.AqueryList();    //调用本身dao
			sysDeptCache.set(list);
		}
		return list;
	}

    /**
     * 保存
     * @param dept
     */
	@Transactional(rollbackFor = Exception.class)
	public void Asave(SysDept dept) {
       this.save(dept);
       sysDeptCache.delete();
	}
	
	/**
	 * 更新
	 * @param dept
	 */
	@Transactional(rollbackFor = Exception.class)
	public void Aupdate(SysDept dept) {
      this.updateById(dept);
      sysDeptCache.delete();
	}

	/**
	 * 删除
	 * @param deptId
	 */
	@Transactional(rollbackFor = Exception.class)
	public void Adelete(long deptId) {
           this.removeById(deptId);
           sysDeptCache.delete();
	}

	/**
	 * 查询第一级子部门ID
	 */
	public List<Long> AqueryDetpIdList(Long parentId) {
		return this.baseMapper.AqueryDetpIdList(parentId);
	}

	/**
	 * 查询所有子部门ID
	 */
	public List<Long> AgetSubDeptIdList(Long deptId) {
		// 部门及子部门ID列表
		List<Long> deptIdList = new ArrayList<>();

		// 获取子部门ID
		List<Long> subIdList = AqueryDetpIdList(deptId);
		AgetDeptTreeList(subIdList, deptIdList);

		return deptIdList;
	}

	/**
	 * 递归
	 */
	private void AgetDeptTreeList(List<Long> subIdList, List<Long> deptIdList) {
		for (Long deptId : subIdList) {
			List<Long> list = AqueryDetpIdList(deptId);
			if (list.size() > 0) {
				AgetDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}
}
