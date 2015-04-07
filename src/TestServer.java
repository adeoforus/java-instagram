
public class TestServer {

	public static void main(String args[]) {
		
		// Variables for read and write files
		String file_name = "All_Data_DB0";
		String file_path_in = "C:\\Users\\Administrator\\Dropbox\\adeo_prj\\instagram-project\\DB_0\\";
		// Out path for map_count
		String file_path_out_count = "C:\\Users\\Administrator\\Dropbox\\adeo_prj\\instagram-project\\Map_Count_out\\";
		// Out path for map_select
		String file_path_out_select = "C:\\Users\\Administrator\\Dropbox\\adeo_prj\\instagram-project\\Map_Select_out\\";
		String tag = "love";
		
		// Test for MAP_COUNT
		Mapper test_map_count = new Mapper(file_name, file_path_in, file_path_out_count);
		test_map_count.mapCount();
		test_map_count = null;
		System.out.println("Test MAP_COUNT done");
		
		// Test for MAP_SELECT
		Mapper test_map_select = new Mapper(file_name, file_path_in, file_path_out_select, tag);
		test_map_select.mapSelect();
		test_map_select = null;
		System.out.println("Test MAP_SELECT done");
		
		// Variables for REDUCE_MERGE
		String[] files_for_merge = new String[2];
		files_for_merge[0] = file_path_out_count + file_name;
		files_for_merge[1] = file_path_out_select + file_name;
		String merged_files = "C:\\Users\\Administrator\\Dropbox\\adeo_prj\\instagram-project\\Reduce_Merge_out\\" + file_name;
	
		// Test for REDUCE_MERGE
		Reducer test_reduce_merge = new Reducer(files_for_merge, merged_files);
		test_reduce_merge.reduceMerge();
		test_reduce_merge = null;
		System.out.println("Test REDUCE_MERGE done");
	}

}
