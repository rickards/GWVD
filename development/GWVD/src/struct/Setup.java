package struct;

import java.util.ArrayList;
import java.util.HashMap;

public class Setup {
	public static String[] VULNERABILITIES_TYPE;
	public static String CROSS_VALIDATION;
	public static String GRANULARITY;
	public static String[] PROJECTS;
	
	private static HashMap<String, String[]>  buildModules() {
		HashMap<String, String[]> moludes = new HashMap<>();
		String[] mozilla = {"MODULE_1_dom","MODULE_1_javascript","MODULE_1_javascript_extras","MODULE_1_javascript_xpconnect","MODULE_1_layout_rendering","MODULE_1_libraries","MODULE_1_mozilla","MODULE_1_network","MODULE_1_toolkit","MODULE_1_webpage_structure","MODULE_1_widget"};
		String[] kernel = {"MODULE_2_arch","MODULE_2_driver_extra","MODULE_2_fs","MODULE_2_kernel","MODULE_2_linux","MODULE_2_net"};
		String[] xen = {"MODULE_3_arch","MODULE_3_tools","MODULE_3_xen"};
		String[] httpd = {"MODULE_4_apache"};
		String[] glibc = {"MODULE_5_glibc"};
		moludes.put("Mozilla", mozilla);
		moludes.put("Kernel", kernel);
		moludes.put("Xen", xen);
		moludes.put("Httpd", httpd);
		moludes.put("Glibc", glibc);
		return moludes;
	}
	
	public static ArrayList<String> getModules(){
		HashMap<String, String[]> iterator = buildModules();
		ArrayList<String> modules = new ArrayList<>();
		for (String project : PROJECTS) {
			for (String module : iterator.get(project)) {
				module = module.replace("MODULE", GRANULARITY);
				modules.add(module);
			}
		}
		return modules;
	}
}
