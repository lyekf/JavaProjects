package com.InterthreadComm;

public class TestThread {
	   public static void main(String[] args) {
	      Chat m = new Chat();
	      new _T1(m);
	      new _T2(m);
	   }
}