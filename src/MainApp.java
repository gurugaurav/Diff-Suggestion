import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainApp {
	
	
	public static void main(String[] args) throws IOException {
		String s = new String(Files.readAllBytes(Paths.get("C:\\Users\\ggaurav\\Downloads\\diff.txt")));
		LineNumberReader rdr = new LineNumberReader(new FileReader(new File("C:\\Users\\ggaurav\\Downloads\\diff.txt")));
		
		String splitByComp[] = s.split("Component: .*");
		


		for (String fileDetail : splitByComp) {
			Matcher m = Pattern.compile("([\\+]{1}[^+].*\\n)+").matcher(fileDetail);
			Pattern functionPatter = Pattern.compile("\\+\\s*(public|private|protected)?(\\s+static)?(\\s+final)?\\s+\\w+\\s+\\w+\\s*\\(.*?\\)\\s*(throws .*)+\\{.*\\}",Pattern.DOTALL);
													  
			
			while(m.find()){
				String line = m.group();
				Matcher jFunctionmatcher = functionPatter.matcher(line);
				
				while (jFunctionmatcher.find()) {
					String functionLine = jFunctionmatcher.group();
					System.err.print("Found...");				    
					   
				
					String[] functionLines = functionLine.split("\n");
					String line1;
					boolean start = false;
					int fLineNo = 0;
					int lineStart = 0;
					while((line1 = rdr.readLine())!= null){
						if (line1.indexOf(functionLines[fLineNo]) >= 0) {
							if(!start){
								lineStart = rdr.getLineNumber();
								start = true;
							}
							
							if(start){
								if(fLineNo == functionLines.length-1){
									System.err.println("Success..Line no:"+lineStart+"-"+(lineStart+fLineNo));
									break;
								}
								else
									fLineNo++;
								
							}
						}else{
							if(start){
								start =false;
								lineStart = 0;
								fLineNo = 0;
							}
						}
					}
					
				
					System.out.println(functionLine);
				}
				
			}
			

			
		}
		
		rdr.close();
	}

}
