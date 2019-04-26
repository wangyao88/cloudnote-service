package com.sxkl.project.cloudnote.utils;

import com.github.pagehelper.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author wangyao
 */
public class PaginationHelper {

	public static final int PAGE_SIZE = 10;

	public static Pageable buildPageInfo(HttpServletRequest request) {

		int start = 0;
		int pageSize = PAGE_SIZE;

		String startString = request.getParameter("start");
		String lengthString = request.getParameter("length");

		if (null != startString && !"".equals(startString)) {
			start = Integer.parseInt(startString);
			start = (start < 0) ? 0 : start;
		}

		if (null != lengthString && !"".equals(lengthString)) {
			pageSize = Integer.parseInt(lengthString);
		}

		int page = start / pageSize + 1; // 从1开始计数

		PageRequest pRequest = new PageRequest(page, pageSize);

		return pRequest;
	}

	/**
	 * 构造当前页对象
	 * 
	 * @param list
	 * @param pageable
	 * @return
	 */
	public static <T> Page<T> buildPage(Collection<T> list, Pageable pageable) {

		List<T> eles = new ArrayList<>();
		Iterator<T> it = list.iterator();

		int startNum = (pageable.getPageNumber() - 1) * pageable.getPageSize();
		int endNum = startNum + pageable.getPageSize();

		int count = 0;
		while (it.hasNext()) {
			T ele = it.next();

			if (count >= startNum) {
				if (count < endNum) {
					eles.add(ele);
				} else {
					break;
				}
			}
			count++;
		}

		Page<T> page = new Page<>(pageable.getPageNumber(), pageable.getPageSize());
		page.setTotal(list.size());
		page.addAll(eles);

		return page;
	}

	@SuppressWarnings("rawtypes")
	public static <T> Page<T> buildPage(List oldList, List<T> newList, Pageable pageinfo) {
		Long total = 0L;
		if (oldList instanceof Page) {
			total = ((Page) oldList).getTotal();
		} else {
			return null;
		}
		Page<T> page = new Page<>(pageinfo.getPageNumber(), pageinfo.getPageSize());
		page.setTotal(total.intValue());
		page.addAll(newList);
		return page;
	}
}
