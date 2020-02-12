
package com.a.modules.sys.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a.common.core.Constant;
import com.a.common.core.ControllerAide;
import com.a.common.core.R;
import com.a.modules.sys.entity.SysDept;
import com.a.modules.sys.service.SysDeptServiceImpl;

/**
 * 部门管理
 *
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {
	@Autowired
	private SysDeptServiceImpl sysDeptService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:dept:list")
	public R list() {
		List<SysDept> deptList = sysDeptService.AqueryList();

		return R.ok().put("deptList", deptList);
	}

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:dept:select")
	public R select() {
		List<SysDept> deptList = sysDeptService.AqueryList();

		// 添加一级部门
		if (ControllerAide.getUserId() == Constant.SUPER_ADMIN) {
			SysDept root = new SysDept();
			root.setDeptId(0L);
			root.setName("一级部门");
			root.setParentId(-1L);
			root.setOpen(true);
			deptList.add(root);
		}

		return R.ok().put("deptList", deptList);
	}

	/**
	 * 上级部门Id(管理员则为0)
	 */
	@GetMapping("/info")
	@RequiresPermissions("sys:dept:list")
	public R info() {
		long deptId = 0;
		if (ControllerAide.getUserId() != Constant.SUPER_ADMIN) {
			List<SysDept> deptList = sysDeptService.AqueryList();
			Long parentId = null;
			for (SysDept sysDeptEntity : deptList) {
				if (parentId == null) {
					parentId = sysDeptEntity.getParentId();
					continue;
				}

				if (parentId > sysDeptEntity.getParentId().longValue()) {
					parentId = sysDeptEntity.getParentId();
				}
			}
			deptId = parentId;
		}

		return R.ok().put("deptId", deptId);
	}

	/**
	 * 信息
	 */
	@GetMapping("/info/{deptId}")
	@RequiresPermissions("sys:dept:info")
	public R info(@PathVariable("deptId") Long deptId) {
		SysDept dept = sysDeptService.getById(deptId);
		return R.ok().put("dept", dept);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	@RequiresPermissions("sys:dept:save")
	public R save(@RequestBody SysDept dept) {
		sysDeptService.Asave(dept);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@RequiresPermissions("sys:dept:update")
	public R update(@RequestBody SysDept dept) {
		// 业务：更新时不能选择本身或下级
		SysDept ordept = sysDeptService.getById(dept.getDeptId());
		if (ordept.getDeptId() == dept.getParentId()) {
			return R.error("上级部门不能选择自己");
		}

		//所有子部门
		List<Long> subdeptList = sysDeptService.AgetSubDeptIdList(dept.getDeptId());
		if (subdeptList.contains(dept.getParentId())) {
			return R.error("上级部门不能选择自己的下级");
		}

		sysDeptService.Aupdate(dept);

		return R.ok();
	}

	
	/**
	 * 删除
	 */
	@PostMapping("/delete")
	@RequiresPermissions("sys:dept:delete")
	public R delete(long deptId) {
		// 判断是否有子部门
		List<Long> deptList = sysDeptService.AqueryDetpIdList(deptId);
		if (deptList.size() > 0) {
			return R.error("请先删除子部门");
		}

		// 判断部们下是否有用户
		
		
		sysDeptService.Adelete(deptId);

		return R.ok();
	}

}
