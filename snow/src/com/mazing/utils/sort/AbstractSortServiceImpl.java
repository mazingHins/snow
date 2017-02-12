package com.mazing.utils.sort;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * SortService抽象实现类
 * 
 * @author hins   
 * @date 2017年2月11日 下午9:29:33
 */
abstract public class AbstractSortServiceImpl implements SortService{
	
	private final static int CUT_LINE = (int) Math.pow(10, 7);	// 每个线程分割的行数
	
	private final static int FILE_LINE = CUT_LINE * 1;	// 文件的行数
	
	protected int[] array = new int[FILE_LINE];;

	@Override
	public List<Integer> listMaxNumber() {
		
		/*
		 * 1. 从文本中获取所有的数据
		 * 2. 校验数据的合法性
		 * 3. 由具体的业务实现（比如堆排序）
		 */
		try {
			processFile("E:\\javacode\\numberData.txt");
			
			validateData();
			
		} catch (Exception e) {
			throw new RuntimeException("执行出现异常。" + e);
		}
		return doSort();
	}
	
	/**
	 * 往一个文本文件写入20亿行数据，每行数据都是从[0，1亿)的随机的数字。<br>
	 * 该方法只用于生成测试数据，时间原因，不做清空处理，只调用一次即可<br>
	 * 注意：如果文件不存在，会自动创建
	 * 
	 * @param fileName - 文件绝对路径
	 * @date 2017年2月10日  下午3:21:46
	 * @author qiujun
	 * @return void
	 */
	protected void writeFile(String fileName) throws IOException {
		FileChannel channel = new RandomAccessFile(fileName, "rw").getChannel();
		ByteBuffer buf = ByteBuffer.allocate(1024);
		Random r = new Random();
		final int progressLine = CUT_LINE / 20;	// 每执行完1/20，就打印一次
		for (long i = 0; i < CUT_LINE; i++) {	// 循环20亿次
			if (i % progressLine == 0) {
				System.out.println("已完成" + i + "个");
			}
			
			buf.clear();
			String line = String.valueOf(r.nextInt(CUT_LINE)) + "\r\n";
			buf.put(line.getBytes());
			buf.flip();
			
			while (buf.hasRemaining()) {
				channel.write(buf);
			}
			
		}
		
		channel.force(true);
		channel.close();
	}

	/**
	 * 开启20个线程分别从文本文件读取数据，并将文本中每行的数据汇总成数组，结果无序<br>
	 * 如果文本文件中存在非数字的字符串、文件不存在，抛出异常
	 * 
	 * @param fileName - 文件绝对路径
	 * @date 2017年2月10日  下午3:21:46
	 * @author qiujun
	 * @return 文本文件每行出现的所有数字组成的数组，结果无序
	 * @throws IOException 
	 */
	protected void processFile(final String fileName) throws IOException {
		File file = new File(fileName);
		BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"), 5 * 1024 * 1024);// 用5M的缓冲读取文本文件
		String line = "";
		int index = 0;
		while ((line = reader.readLine()) != null) {
			array[index] = Integer.valueOf(line);
			index++;
		}
		reader.close();
		fis.close();
	}
	
	/**
	 * 校验准备统计的数据是否合法<br>
	 * 只做了简单的统计，没有对array数据的重复问题做校验.比如array一共有101个元素，但是每个元素的值都相同
	 * 
	 * @param array - 从文本上读取的数据
	 * @date 2017年2月10日  下午2:25:14
	 * @author qiujun
	 * @return void
	 */
	protected void validateData(){
		if (array == null || array.length < 1) {
			// --------------- 打log，抛异常。 这里就直接忽略日志 -----------------
			throw new RuntimeException("该文件没有数据，请确认。");
		}
		
		if (array.length < 100) {
			throw new RuntimeException("该文件没有数据，请确认。");
		}
	}
	
	/**
	 * 交换数组array下标位置为from和to的2个元素
	 * 
	 * @param array - 数组array
	 * @param from - 交换元素的下标之一
	 * @param to - 交换元素的下标之一
	 * @date 2017年2月10日 下午2:15:32
	 * @author qiujun
	 * @return void
	 */
	protected void swapElement(int[] array, int from, int to) {
		final int temp = array[from];
		array[from] = array[to];
		array[to] = temp;
	}
	
	/**
	 * 数组转list。
	 * 方法不判断数组是否为空，是否有元素
	 * 
	 * @author hins  
	 * @date 2017年2月12日 下午10:21:28 
	 * @param array
	 * @return 队列list
	 */
	protected List<Integer> arrayToList(int[] array){
		List<Integer> list = new ArrayList<>(array.length);
		for(int i : array){
			list.add(i);
		}
		return list;
	}
	
	abstract protected List<Integer> doSort();
	
}
