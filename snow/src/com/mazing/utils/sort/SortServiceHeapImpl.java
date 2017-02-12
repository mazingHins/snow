package com.mazing.utils.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆排序算法的实现
 * 时间复杂度：时间复杂度：n*logn(底数是2)，其中n是文本的行数
 * 
 * @author hins
 * @date 2017年2月11日 下午9:42:20
 */
public class SortServiceHeapImpl extends AbstractSortServiceImpl {

	@Override
	protected List<Integer> doSort() {

		/*
		 * 1. 进行堆排序，得到从大到小的“二叉树”。 
		 * 	1.1 构建最大堆，具体过程看“构建最大堆”buildMaxHeap方法的注释 
		 * 	1.2 将堆顶和最后一个元素（相对的）交换，得到新的无序区，再执行重建堆（既再将最大值置顶）
		 * 
		 * 2. 从二叉树中获取最后100个最大的值（注意重复）
		 */

		// 构建最大堆
		buildMaxHeap(array);

		// 将堆顶和最后一个元素（相对的）交换，得到新的无序区，再执行重建堆（既再将最大值置顶）
		for (int i = array.length - 1; i >= 1; i--) {
			swapElement(array, 0, i);

			processHeap(array, 0, i);
		}

		// 到了这一步，array排序顺序是从小到大了，直接获取最后100个（注意重复）
		int previous = 0; // 上一个遍历的元素。用于防止重复
		List<Integer> result = new ArrayList<>(100);
		for (int i = array.length - 1; i < 1 && result.size() < 100; i--) {
			if (array[i] == previous)
				continue;

			result.add(array[i]);
		}

		return result;
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
		if (heapSize > left && array[left] > array[max]) {
			max = left;
		}

		// 防止C2>C1， 此时要用C2与D1交换
		if (heapSize > right && array[right] > array[max]) {
			max = right;
		}

		// 需要交换
		if (max != root) {
			swapElement(array, max, root);
			// 交换后再递归，直到无须交换为止
			processHeap(array, max, heapSize);
		}

	}

	@Override
	public void testListMaxNumber() {

		for (int i = 0; i < 50; i++) {
			doSort();
		}

		long begin = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			doSort();
		}
		
		System.out.println("SortServiceHeapImpl测试" + array.length + "次完毕，耗时：" + (System.currentTimeMillis() - begin));

	}
	
	

}
