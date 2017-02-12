package com.mazing.utils.sort;

import java.util.List;

/**
 * 优化的堆排序实现<br>
 * 逻辑： 1. 创建一个大小为100的有序队列Q（经过堆排序的完整过程，结果：从大到小）<br>
 * 		2. 每次for循环20E数据时候（假设遍历值=A）,从队列Q中二分法找到要修改的节点（满足的条件：A最先小于节点的地方，比如节点值=3000，A=2000）
 * 		3. 从低于当前节点的，重新进行堆排序。
 * 		4. 重复2，3
 * 具体的实现代码还没写，时间原因，先这样先(忽略该类的实现代码).时间复杂度：n*100*log100(底数是2)，其中n是文本的行数
 * 
 * @author hins   
 * @date 2017年2月12日 下午9:57:05
 */
public class SortServiceHeapYHImpl  extends AbstractSortServiceImpl {
	
	private int[] queue;

	@Override
	public void testListMaxNumber() {
		
		for (int i = 0; i < 50; i++) {
			doSort();
		}

		long begin = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			doSort();
		}
		
		System.out.println("SortServiceHeapYHImpl测试" + array.length + "次完毕，耗时：" + (System.currentTimeMillis() - begin));

		
	}
	
	private void initArray() {
		queue = new int[101];
		for (int i = 0; i < 100 && i < array.length; i++) {
			queue[i] = array[i];
		}
		

//		// 排序
//		processSort(queue);
		
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
			processSort(queue);
		}

		return arrayToList(queue);
	}
	
	
	private void processSort(int[] arr){
		// 重新构建最小堆
		buildMaxHeap(queue);
		
		for (int i = arr.length - 1; i >= 1; i--) {
			swapElement(arr, 0, i);
			processHeap(arr, 0, i);
		}
	}
	
	/**
	 * 对原始数组进行-构建最大堆
	 * 
	 * @param array  - 要排序的数组(原始数据)
	 * @date 2017年2月10日 上午11:32:12
	 * @author qiujun
	 * @return void
	 */
	private void buildMaxHeap(int[] array) {

		/*
		 * 规则：从最后一个节点的父节点开始，按照最大堆原则，for循环调整堆。for(最后节点的父节点->最高级root节点)
		 * 调整过程(实现代码在processHeap方法)：
		 *    a. 判断当前节点D1的子节点(可能有2个C1,C2)，如果C1/C2>D1，交换。否则continue
		 *    b. 如果步骤a有进行交换操作（假设D1与C2交换），则交换后的C2还要进行步骤a处理（直到满足不交换条件/在我看来是没有子节点）
		 * 以上调整过程是for完一轮
		 */

		if (array == null || array.length < 2) {
			return;
		}

		for (int i = array.length / 2; i >= 0; i--) {
			// exchangElement(array, i, array.length);
			// 执行调整过程
			processHeap(array, i, array.length);
		}

	}
	
	/**
	 * 将数组array从root节点开始，从下往上调整成最大堆的完全二叉树<br>
	 * 注意：root不一定是二叉树的最终根节点。只是相对的root节点
	 * 
	 * @param array - 待调整的数组
	 * @param root - 开始调整的根节点
	 * @param heapSize
	 *            -
	 * @date 2017年2月10日 下午2:15:32
	 * @author qiujun
	 * @return void
	 */
	private void processHeap(int[] array, int root, int heapSize) {

		// left-是root根节点的左子节点C1，root-是root跟节点的右子节点C2，max-是root、C1、C2三个子节点中最大的
		int left = root * 2 + 1, right = left + 1, max = root;

		// 如果C1>D1，要走交换的流程(ps:要防止没有子节点的，即没有c1/c2的)
		if (heapSize > left && array[left] < array[max]) {
			max = left;
		}

		// 防止C2>C1， 此时要用C2与D1交换
		if (heapSize > right && array[right] < array[max]) {
			max = right;
		}

		// 需要交换
		if (max != root) {
			swapElement(array, max, root);
			// 交换后再递归，直到无须交换为止
			processHeap(array, max, heapSize);
		}

	}
	
}
