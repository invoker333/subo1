package com.mingli.toms;


public class UserName {
	private static String[] nameArray;
	static{
		String nameSet="李白、陶渊明、苏轼、苏东坡、杜甫、王维、李清照、辛弃疾、李煜、纳兰容若、李商隐、杜牧、范仲淹、欧阳修、柳宗元、刘长卿、刘禹锡、屈原、王昌龄、白居易、陆游、韦应物、王勃、张若虚、柳永、周邦彦、韦庄、杜荀鹤、岑参、吴文英、杨万里、庾信、贺知章、孟浩然、王安石、李贺、王之涣";
		nameArray=nameSet.split("、");
//		for(int i=0;i<nameArray.length;i++)
//		System.out.println(nameArray[i]);
	}
	public static String randomName() {
		// TODO Auto-generated method stub
		return nameArray[(int) (nameArray.length*Math.random())];
	}
	public static void main(String args[]){
		new UserName();
	}

}
