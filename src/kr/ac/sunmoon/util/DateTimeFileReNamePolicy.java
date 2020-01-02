package kr.ac.sunmoon.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.oreilly.servlet.multipart.FileRenamePolicy;

public class DateTimeFileReNamePolicy implements FileRenamePolicy {

	public File rename(File srcFile) {
		System.out.println("rename---------------");
		
		if(srcFile.exists()){
			SimpleDateFormat dateForm = new SimpleDateFormat("yyyyMMddhhmmssSSS");
			
			String newName = srcFile.getParent()+File.separator+dateForm.format(new Date())+"_"+srcFile.getName();
			
			File newFile = new File(newName);
			System.out.println(srcFile.getName()+"/"+newFile.getName());
			return newFile;
		}else{
			return srcFile;
		}
		
	}

}
