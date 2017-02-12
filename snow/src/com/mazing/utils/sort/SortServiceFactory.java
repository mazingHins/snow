package com.mazing.utils.sort;

/**
 * 排序相关业务的工厂类
 * 
 * @author hins   
 * @date 2017年2月12日 下午10:11:49
 */
public class SortServiceFactory {
	
	public static SortService getInstance(int code){
		if(code == 1){
			return new SortServiceInsertImpl();
		} else if(code == 2){
			return new SortServiceHeapYHImpl();
		} else if(code == 3){
			return new SortServiceHeapImpl();
		}
		return null;
		
	}
	
	public static void main(String[] args) {
		// 时间原因，code写死吧
		SortService sort = getInstance(1);
		long begin = System.currentTimeMillis();
		sort.listMaxNumber();
		System.out.println(System.currentTimeMillis() - begin);
		
		sort = getInstance(3);
		begin = System.currentTimeMillis();
		sort.listMaxNumber();
		System.out.println(System.currentTimeMillis() - begin);
		
		sort = getInstance(2);
		begin = System.currentTimeMillis();
		sort.listMaxNumber();
		System.out.println(System.currentTimeMillis() - begin);
		
	}

}
