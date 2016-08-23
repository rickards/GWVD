package br.ufal.ic.security.struct;

import java.util.ArrayList;
import java.util.HashMap;

public class Setup {
	public static String[] VULNERABILITIES_TYPE;
	public static String CROSS_VALIDATION;
	public static String GRANULARITY;
	public static String[] PROJECTS;
	public static float progressQuery = 0;
	public static float progressOverall = 0;
	
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
	
	private static HashMap<String, String[]>  buildReleases() {
		HashMap<String, String[]> releases = new HashMap<>();
		String[] mozilla = {"Firefox1","Firefox10","Firefox10.0.1","Firefox10.0.2","Firefox1.0.1","Firefox1.0.2","Firefox1.0.3","Firefox1.0.4","Firefox1.0.5","Firefox1.0.7","Firefox1.0.8","Firefox11","Firefox12","Firefox13","Firefox14","Firefox15","Firefox1.5","Firefox1.5.0.1","Firefox1.5.0.10","Firefox1.5.0.11","Firefox1.5.0.12","Firefox1.5.0.2","Firefox1.5.0.3","Firefox1.5.0.4","Firefox1.5.0.5","Firefox1.5.0.7","Firefox1.5.0.8","Firefox1.5.0.9","Firefox16","Firefox16.0.1","Firefox16.0.2","Firefox17","Firefox17.0.9","Firefox18","Firefox19","Firefox19.0.2","Firefox20","Firefox2.0.0.1","Firefox2.0.0.10","Firefox2.0.0.12","Firefox2.0.0.13","Firefox2.0.0.14","Firefox2.0.0.15","Firefox2.0.0.16","Firefox2.0.0.17","Firefox2.0.0.18","Firefox2.0.0.19","Firefox2.0.0.2","Firefox2.0.0.20","Firefox2.0.0.3","Firefox2.0.0.4","Firefox2.0.0.5","Firefox2.0.0.6","Firefox2.0.0.7","Firefox2.0.0.8","Firefox21","Firefox22","Firefox23","Firefox24","Firefox25","Firefox25.0.1","Firefox26","Firefox27","Firefox28","Firefox28.0.1","Firefox29","Firefox3","Firefox30","Firefox3.0.1","Firefox3.0.10","Firefox3.0.11","Firefox3.0.12","Firefox3.0.13","Firefox3.0.14","Firefox3.0.15","Firefox3.0.16","Firefox3.0.18","Firefox3.0.19","Firefox3.0.2","Firefox3.0.4","Firefox3.0.5","Firefox3.0.6","Firefox3.0.7","Firefox3.0.8","Firefox3.0.9","Firefox31","Firefox32","Firefox32.0.3","Firefox33","Firefox34","Firefox35","Firefox3.5","Firefox3.5.1","Firefox3.5.10","Firefox3.5.11","Firefox3.5.12","Firefox3.5.14","Firefox3.5.15","Firefox3.5.16","Firefox3.5.17","Firefox3.5.18","Firefox3.5.19","Firefox3.5.2","Firefox3.5.3","Firefox3.5.4","Firefox3.5.6","Firefox3.5.8","Firefox3.5.9","Firefox36","Firefox3.6","Firefox3.6.11","Firefox3.6.12","Firefox3.6.13","Firefox3.6.14","Firefox3.6.16","Firefox3.6.17","Firefox3.6.18","Firefox3.6.2","Firefox3.6.20","Firefox3.6.21","Firefox3.6.22","Firefox3.6.23","Firefox3.6.24","Firefox3.6.25","Firefox3.6.26","Firefox3.6.27","Firefox3.6.28","Firefox3.6.3","Firefox3.6.4","Firefox3.6.7","Firefox3.6.8","Firefox3.6.9","Firefox37","Firefox37.0.1","Firefox37.0.2","Firefox38","Firefox39","Firefox39.0.3","Firefox40","Firefox4.0.1","Firefox41","Firefox42","Firefox43","Firefox5","Firefox6","Firefox6.0.1","Firefox6.0.2","Firefox7","Firefox8","Firefox9","FirefoxESR10.0.1","FirefoxESR10.0.10","FirefoxESR10.0.11","FirefoxESR10.0.12","FirefoxESR10.0.2","FirefoxESR10.0.3","FirefoxESR10.0.4","FirefoxESR10.0.5","FirefoxESR10.0.6","FirefoxESR10.0.7","FirefoxESR10.0.8","FirefoxESR10.0.9","FirefoxESR17.0.1","FirefoxESR17.0.10","FirefoxESR17.0.11","FirefoxESR17.0.2","FirefoxESR17.0.3","FirefoxESR17.0.4","FirefoxESR17.0.5","FirefoxESR17.0.6","FirefoxESR17.0.7","FirefoxESR17.0.8","FirefoxESR17.0.9","FirefoxESR24.1","FirefoxESR24.1.1","FirefoxESR24.2","FirefoxESR24.3","FirefoxESR24.4","FirefoxESR24.5","FirefoxESR24.6","FirefoxESR24.7","FirefoxESR24.8","FirefoxESR24.8.1","FirefoxESR31.1","FirefoxESR31.1.1","FirefoxESR31.2","FirefoxESR31.3","FirefoxESR31.4","FirefoxESR31.5","FirefoxESR31.6","FirefoxESR31.7","FirefoxESR31.8","FirefoxESR38.1","FirefoxESR38.1.1","FirefoxESR38.2","FirefoxESR38.4","FirefoxESR38.7","FirefoxMobile10.0.4","FirefoxMobile6.0.1","FirefoxMobile6.0.2","FirefoxOS1.2.2","FirefoxOS1.3","FirefoxOS2.2","FirefoxOS2.5","MozillaSuite1.7.10","MozillaSuite1.7.12","MozillaSuite1.7.13","MozillaSuite1.7.5","MozillaSuite1.7.6","MozillaSuite1.7.7","MozillaSuite1.7.8","NetscapePortableRuntime4.10.6","NSS3.11.3","NSS3.11.5","NSS3.12.3","NSS3.16.2.1","RELEASES","SeaMonkey","SeaMonkey1","SeaMonkey1.0.1","SeaMonkey1.0.2","SeaMonkey1.0.3","SeaMonkey1.0.5","SeaMonkey1.0.6","SeaMonkey1.0.7","SeaMonkey1.0.8","SeaMonkey1.0.9","SeaMonkey1.1.1","SeaMonkey1.1.10","SeaMonkey1.1.11","SeaMonkey1.1.12","SeaMonkey1.1.13","SeaMonkey1.1.14","SeaMonkey1.1.15","SeaMonkey1.1.16","SeaMonkey1.1.17","SeaMonkey1.1.18","SeaMonkey1.1.19","SeaMonkey1.1.2","SeaMonkey1.1.3","SeaMonkey1.1.4","SeaMonkey1.1.5","SeaMonkey1.1.7","SeaMonkey1.1.8","SeaMonkey1.1.9","SeaMonkey2","SeaMonkey2.0.1","SeaMonkey2.0.10","SeaMonkey2.0.11","SeaMonkey2.0.12","SeaMonkey2.0.13","SeaMonkey2.0.14","SeaMonkey2.0.3","SeaMonkey2.0.4","SeaMonkey2.0.5","SeaMonkey2.0.6","SeaMonkey2.0.7","SeaMonkey2.0.9","SeaMonkey2.10","SeaMonkey2.11","SeaMonkey2.12","SeaMonkey2.13","SeaMonkey2.13.1","SeaMonkey2.13.2","SeaMonkey2.14","SeaMonkey2.15","SeaMonkey2.16","SeaMonkey2.16.1","SeaMonkey2.17","SeaMonkey2.19","SeaMonkey2.2","SeaMonkey2.20","SeaMonkey2.21","SeaMonkey2.22","SeaMonkey2.22.1","SeaMonkey2.23","SeaMonkey2.24","SeaMonkey2.25","SeaMonkey2.26","SeaMonkey2.26.1","SeaMonkey2.29","SeaMonkey2.3","SeaMonkey2.30","SeaMonkey2.31","SeaMonkey2.32","SeaMonkey2.3.2","SeaMonkey2.33","SeaMonkey2.3.3","SeaMonkey2.35","SeaMonkey2.38","SeaMonkey2.4","SeaMonkey2.5","SeaMonkey2.6","SeaMonkey2.7","SeaMonkey2.7.1","SeaMonkey2.7.2","SeaMonkey2.8","SeaMonkey2.9","Th","Thu","Thund","Thunde","Thunder","Thunderb","Thunderbir","Thunderbird","Thunderbird0.9","Thunderbird1","Thunderbird10","Thunderbird1.0","Thunderbird10.0.1","Thunderbird1.0.2","Thunderbird1.0.5","Thunderbird1.0.7","Thunderbird1.0.8","Thunderbird1.0.8 &","Thunderbird11","Thunderbird12","Thunderbird13","Thunderbird14","Thunderbird15","Thunderbird1.5.0","Thunderbird1.5.0.10","Thunderbird1.5.0.12","Thunderbird1.5.0.13","Thunderbird1.5.0.14","Thunderbird1.5.0.2","Thunderbird1.5.0.4","Thunderbird1.5.0.5","Thunderbird1.5.0.7","Thunderbird1.5.0.8","Thunderbird1.5.0.9","Thunderbird16","Thunderbird16.0.1","Thunderbird16.0.2","Thunderbird17","Thunderbird17.0.2","Thunderbird17.0.3","Thunderbird17.0.4","Thunderbird17.0.5","Thunderbird17.0.6","Thunderbird17.0.7","Thunderbird17.0.8","Thunderbird2.0.0.12","Thunderbird2.0.0.14","Thunderbird2.0.0.16","Thunderbird2.0.0.17","Thunderbird2.0.0.18","Thunderbird2.0.0.19","Thunderbird2.0.0.21","Thunderbird2.0.0.22","Thunderbird2.0.0.23","Thunderbird2.0.0.24","Thunderbird2.0.0.4","Thunderbird2.0.0.5","Thunderbird2.0.0.6","Thunderbird2.0.0.9","Thunderbird24","Thunderbird24.1","Thunderbird24.2","Thunderbird24.3","Thunderbird24.4","Thunderbird24.5","Thunderbird24.6","Thunderbird24.7","Thunderbird24.8","Thunderbird3","Thunderbird3.","Thunderbird3.0","Thunderbird3.0.1","Thunderbird3.0.10","Thunderbird3.0.11","Thunderbird3.0.2","Thunderbird3.0.4","Thunderbird3.0.5","Thunderbird3.0.6","Thunderbird3.0.7","Thunderbird3.0.9","Thunderbird31","Thunderbird3.1.","Thunderbird3.1.1","Thunderbird3.1.10","Thunderbird3.1.11","Thunderbird3.1.12","Thunderbird3.1.15","Thunderbird3.1.16","Thunderbird3.1.17","Thunderbird3.1.18","Thunderbird31.2","Thunderbird3.1.3","Thunderbird31.3","Thunderbird31.4","Thunderbird31.5","Thunderbird31.6","Thunderbird31.7","Thunderbird3.1.8","Thunderbird38.0.1","Thunderbird38.1","Thunderbird38.2","Thunderbird6","Thunderbird7","Thunderbird8","Thunderbird9","ThunderbirdES","ThunderbirdESR10","ThunderbirdESR10.","ThunderbirdESR10.0","ThunderbirdESR10.0.7","ThunderbirdESR17.","ThunderbirdESR17.0.5","ThunderbirdESR17.0.6"};
		String[] kernel = {"v2.6.12","v2.6.13","v2.6.16","v2.6.17","v2.6.19","v2.6.20","v2.6.21","v2.6.22","v2.6.23","v2.6.24","v2.6.25","v2.6.26","v2.6.27","v2.6.28","v2.6.29","v2.6.30","v2.6.31","v2.6.32","v2.6.33","v2.6.34","v2.6.35","v2.6.36","v2.6.37","v2.6.38","v2.6.39","v3.0","v3.1","v3.10","v3.11","v3.12","v3.13","v3.14","v3.15","v3.16","v3.17","v3.18","v3.19","v3.2","v3.3","v3.4","v3.5","v3.6","v3.7","v3.8","v3.9","v4.0","v4.1","v4.2","v4.3","v4.4","v4.5"};
		String[] xen = {"4.1.4","4.1.5","4.1.6","4.2.1","4.2.2","4.2.3","4.2.4","4.2.5","4.3.0","4.3.1","4.3.4","4.4.0","4.4.1","4.4.2","4.4.4","4.5.0","4.5.1","4.5.2","4.5.3","4.6.0","4.6.1"};
		String[] httpd = {""};
		String[] glibc = {"glibc-2.20","glibc-2.22","glibc-2.21","glibc-2.15","glibc-2.19","glibc-2.18","glibc-2.16","glibc-2.17","glibc-2.14","glibc-2.13","fedora/glibc-2.11.90-10","fedora/glibc-2.12-1","cvs/fedora-glibc-20090424T0747","fedora/glibc-2.10.90-25"};
		releases.put("Mozilla", mozilla);
		releases.put("Kernel", kernel);
		releases.put("Xen", xen);
		releases.put("Httpd", httpd);
		releases.put("Glibc", glibc);
		return releases;
	}
	
	public static ArrayList<String> getModules(String project){
		HashMap<String, String[]> iterator = buildModules();
		ArrayList<String> modules = new ArrayList<>();
		for (String module : iterator.get(project)) {
			module = module.replace("MODULE", GRANULARITY);
			modules.add(module);
		}
		return modules;
	}
	
	public static String[] getReleases(String project){
		HashMap<String, String[]> iterator = buildReleases();
		if(CROSS_VALIDATION.equals("n fold-validation")) return new String[]{""};
		return iterator.get(project);
	}
	
	public static int numberQuerys(){
		int out=0;
		for (String project : PROJECTS) {
			out+=getReleases(project).length*getModules(project).size();
		}
		return out;
	}
}
