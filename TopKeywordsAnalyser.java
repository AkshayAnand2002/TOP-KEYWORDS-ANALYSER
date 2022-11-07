import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class TopKeywordsAnalyser implements Runnable{
	public final String filePath;
	public TopKeywordsAnalyser(String filePath) {
		this.filePath=filePath;
	}
	@Override
	public void run() {
		ArrayList<String> keywordfiledata = null;
		try {
			keywordfiledata = FileUtility.readFileAsList(filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String,Integer> keywordcounter=new HashMap<String,Integer>();
		//for every row 
		for(String row:keywordfiledata) {
			String[] keywords=row.split(" ");
			//within each row process each keyword.
			for(String keyword:keywords) {
				String str=keyword.toLowerCase();
				if(!keywordcounter.containsKey(str)) {//not in hashmap
					keywordcounter.put(str, 1);//1st time any keyword found.
				}else {
					Integer value=keywordcounter.get(str);
					keywordcounter.put(str,value+1);
				}
			}
		}
		ArrayList<Keywordcount>keywordcountarraylist=new ArrayList<Keywordcount>();
		for(String keyword:keywordcounter.keySet()) {
			Keywordcount keywordcount=new Keywordcount(keyword,keywordcounter.get(keyword));
			keywordcountarraylist.add(keywordcount);
		}
		Collections.sort(keywordcountarraylist,new Comparator<Keywordcount>() {
			public int compare(Keywordcount o1,Keywordcount o2) {
				if(o2.count==o1.count) {
					return o1.keyword.compareTo(o2.keyword);
				}
				return o2.count-o1.count;
			}
		});
		for(Keywordcount keywordcount:keywordcountarraylist ) {
			System.out.println(keywordcount.keyword+" "+keywordcount.count);
		}
	}
	public static void main(String[] args) {
		TaskManager taskManager=new TaskManager(1);
		taskManager.waitTillQueueIsFreeAndAddTask(new TopKeywordsAnalyser("C:\\IndianNationalAnthem.txt"));
	}


}
