package org.andot.jdatax;

import org.junit.Test;

import org.andot.jdatax.utils.FileToZip;

public class ZipTest extends BaseTest {
	@Test
	public void zip(){
		String filePath = "D:\\Upload\\datax";
		FileToZip.fileToZip(filePath , filePath.substring(0, filePath.lastIndexOf("\\")), "datax-json");
	}
}
