package com.mazing.utils.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 插入排序的实现<br>
 * 逻辑： 1. 创建一个大小为100的有序队列Q<br>
 * 		2. 每次for循环20E数据时候（假设遍历值=A），都跟队列Q比较。使用插入排序算法，比较A和Q<br>
 *      3. 经过步骤2，遍历完了A后，就已经得到最新的有序队列Q。重复步骤2，直到for循环完<br>
 * 时间复杂度：n*100至n*100*100之间，其中n是文本的行数
 * 
 * @author hins   
 * @date 2017年2月12日 下午8:31:43
 */
public class SortServiceInsertImpl extends AbstractSortServiceImpl {
	
	private int[] queue;	//  大下=100的有序队列

	@Override
	public void testListMaxNumber() {

		for (int i = 0; i < 50; i++) {
			doSort();
		}

		long begin = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			doSort();
		}
		
		System.out.println("SortServiceInsertImpl测试" + array.length + "次完毕，耗时：" + (System.currentTimeMillis() - begin));
	}
	
	private void initArray() {
		queue = new int[101];
		for (int i = 0; i < 100 && i < array.length; i++) {
			queue[i] = array[i];
		}
		
		processInsertSort(queue);
	}

	@Override
	protected List<Integer> doSort() {
		// 构建一个大小为100的有序队列，用于存储前100的数字
		initArray();
		
		if (array.length < 100) { // 子类也做多一层校验
			return arrayToList(queue);
		}
		
		for (int i = 100; i < array.length; i++) {
			queue[100] = array[i];
			processInsertSort(queue);
		}
		
		return arrayToList(queue);
	}
	
	/**
	 * 插入排序 方法：将一个记录插入到已排好序的有序表（有可能是空表）中,从而得到一个新的记录数增1的有序表
	 * 
	 * @author hins  
	 * @date 2017年2月12日 下午9:16:49 
	 * @param arr - 等待排序的无序数组
	 */
	private void processInsertSort(int[] arr){
		for (int j = 1; j < arr.length; j++) {
			for (int k = j; (k > 0) && (queue[k] > queue[k - 1]); k--) {
				swapElement(queue, k, k - 1);
			}
		}
	}
	

}
