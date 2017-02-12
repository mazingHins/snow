package com.mazing.utils.sort;

import java.util.List;

/**
 * 数字排序服务定义。
 * 只用于从文件中获取要求的数据
 * 
 * @author hins   
 * @date 2017年2月11日 下午9:27:44
 */
public interface SortService {
	
	/**
	 * 从文本文件中，获取大小排在前100名的数字列表，结果按从大到小排序<br>
	 * 结果会进行去重处理<br>
	 * 结果可能会不足100（比如文本文件非重复的不足100个数字）<br>
	 * 如果文件数据不足100个或存在非数字的数据，抛出异常
	 * 
	 * @author hins  
	 * @date 2017年2月11日 下午9:24:25 
	 * @return 排名前100名的数字列表
	 */
	List<Integer> listMaxNumber();
	
	/**
	 * 测试listMaxNumber每个算法实现的性能。
	 * 该方法只用于测试
	 * 
	 * @author hins  
	 * @date 2017年2月11日 下午9:53:26
	 */
	@Deprecated
	void testListMaxNumber();
	
}
