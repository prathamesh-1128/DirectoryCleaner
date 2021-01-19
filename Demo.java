import java.lang.*;
import java.util.*;
import java.io.*;
import java.io.FileInputStream;
import java.security.MessageDigest;

class Demo{
	public static void main(String[] args) throws Exception{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		//Taking Directory Name 
		
			System.out.println("Enter Directory Name");
		String dr=br.readLine();
		
		Cleaner obj=new Cleaner(dr);
		
		obj.CleanEmptyFile();
		obj.CleanDuplicateFile();
		
		
	}
}

class Cleaner{
	public File fdir=null;
	public Cleaner(String name){
		fdir=new File(name);
		
		if(!fdir.exists()){
			System.out.println("Invalid Name");
			System.exit(0);
		}
	}
	
	 public void CleanEmptyFile(){
		 File filelist[]=fdir.listFiles();
		 
		 int EmptyFile=0;
		 
		 for(File file:filelist){
			 if(file.length()==0){
				 if(!file.delete()){
					 System.out.println("Unable to Delete");
				 }
				 
				 else EmptyFile++;
			 }
		 }
		 
		 System.out.println("Total Empty File Deleted:"+EmptyFile);
		
	}
	
	public void CleanDuplicateFile() throws Exception{
		File filelist[]=fdir.listFiles();
		int duFile=0,Rcount=0;
		
		byte arr[]=new byte[1024];
		
		LinkedList<String> res= new LinkedList<String>();
	
		
		try{
			MessageDigest digest=MessageDigest.getInstance("MD5");
			
			if(digest==null){
				System.out.println("Unable to Create Object of MD5");
				System.exit(0);
			}
				
				
			for(File file:filelist){
				FileInputStream fin=new FileInputStream(file);
	
				//System.out.println("File:"+file);
				if(file.length()!=0){
					while((Rcount=fin.read(arr))!=-1){
						digest.update(arr,0,Rcount);
					}
				}
				
				byte bytes[]=digest.digest();
				StringBuilder sb=new StringBuilder();
				
				for(int i=0;i<bytes.length;i++){
					sb.append(Integer.toString((bytes[i] & 0xff)+0x100,16).substring(1));
				}
				System.out.println("File Name:"+file.getName()+" "+"CheckSum:"+sb);
				
					
				
				if(res.contains(sb.toString())){
					
				//file.setExecutable(true); 
				//file.setReadable(true); 
				//file.setWritable(true);
				
					if(!file.delete()){
						System.out.println("Unable to Delete File");
					}
					
					else{
						duFile++;
					} 
				}
				else{
					res.add(sb.toString());
	
              
				} 
				fin.close();
			}
		}
		
		catch(Exception e){
			System.out.println(e);
		}
		
		finally{
			
		}
		System.out.println("Total duplicate files deleted : "+ duFile);
	}
}