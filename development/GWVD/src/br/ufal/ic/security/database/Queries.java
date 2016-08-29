package br.ufal.ic.security.database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufal.ic.security.struct.Instance;
import br.ufal.ic.security.struct.Setup;

public class Queries {
	
	private String[] metrics;
	private String id_table;
	public static String PATH = "/home/easy/GWVD/outputs/";
	
	public Queries() {
		buildingMetrics();
		File dir = new File(PATH);
		int i = 2;
		while (dir.exists()) {
			if(!PATH.contains("(")) PATH=PATH.replace("outputs", "outputs("+i+")");
			else PATH=PATH.replace("outputs("+i+++")", "outputs("+i+")");
			dir = new File(PATH);
		}
		dir.mkdirs();
	}
	
	//TODO: Refactoring: this method not supposed to be here and yes in setups
	private void buildingMetrics(){
		String[] metrics_functions = {"AltCountLineCode","CountLine","CountLineCode","CountLineCodeExe","CountLineCodeDecl","Cyclomatic","CyclomaticModified","CyclomaticStrict","Knots","Essential","MaxEssentialKnots","MinEssentialKnots","CountPath","AltCountLineComment","CountLineComment","RatioCommentToCode","CountStmt","CountStmtExe","CountStmtDecl","CountStmtEmpty","CountLineBlank","CountLineInactive","CountSemicolon","MaxNesting","CountInput","CountOutput","CountLinePreprocessor","Patched"};
		String[] metrics_files = {"AltAvgLineBlank","AltAvgLineComment","AltCountLineCode","AvgCyclomatic","AvgCyclomaticStrict","AvgLine","AvgLineCode","CountLineBlank","CountLineCodeDecl","CountLineComment","CountLinePreprocessor","CountStmt","CountStmtEmpty","MaxCyclomaticModified","MaxEssential","RatioCommentToCode","SumCyclomaticModified","SumEssential","AltCountLineComment","AvgCyclomaticModified","AvgEssential","AvgLineBlank","AvgLineComment","CountDeclClass","CountDeclFunction","CountLine","CountLineCode","CountLineCodeExe","CountLineInactive","CountSemicolon","CountStmtDecl","CountStmtExe","MaxCyclomatic","MaxCyclomaticStrict","MaxNesting","SumCyclomatic","SumCyclomaticStrict","CountPath","FanIn","FanOut","AvgFanIn","AvgFanOut","MaxFanIn","MaxFanOut","AvgMaxNesting","SumMaxNesting","MaxMaxNesting","HK","DIT","NOC","CBC","RFC","CBO","LCOM","Patched"};
		if(Setup.GRANULARITY.equals("FUNCTIONS")){
			metrics = metrics_functions;
			id_table = "ID_Function";
		}
		if(Setup.GRANULARITY.equals("FILES")){
			metrics = metrics_files;
			id_table = "ID_File";
		}
	}
	
	public void buildingQuery() throws IOException{
		int numberQuerys = Setup.numberQuerys();
		System.out.println("nUMERO DE QUERIES: "+numberQuerys);
		float count = 0;
		for (String project : Setup.PROJECTS) {
			for (String module : Setup.getModules(project)) {
				String query_no_vul = "SELECT * FROM "+module+" f2s WHERE f2s.Patched=0 and f2s.Occurrence='before' and f2s."+id_table+" NOT IN (SELECT distinct tb."+id_table+" FROM "+module+" tb, (SELECT * FROM "+module+" WHERE patched=1 AND Occurrence='before') vul WHERE tb.FilePath=vul.FilePath";
				String query_vul = "SELECT DISTINCT tb.FilePath";
				if(Setup.GRANULARITY.equals("FUNCTIONS")){
					query_no_vul+=" AND tb.NameMethod=vul.NameMethod";
					query_vul+=",tb.NameMethod";
				}
				for (String metric : metrics) {
					query_no_vul+=" AND tb."+metric+"=vul."+metric;
					query_vul+=",tb."+metric;
				}
				query_no_vul+=")";
				String or_vulnerability=" AND (VULNERABILITIES.V_CLASSIFICATION like '%"+Setup.VULNERABILITIES_TYPE[0]+"%'";
				for (int i = 1; i < Setup.VULNERABILITIES_TYPE.length; i++) {
					or_vulnerability+=" OR "+"VULNERABILITIES.V_CLASSIFICATION like '%"+Setup.VULNERABILITIES_TYPE[i]+"%'";
				}
				or_vulnerability+=")";
				for (String release : Setup.getReleases(project)) {
					String neutralQuery = query_vul+" FROM software.VULNERABILITIES as VULNERABILITIES,software.PATCHES as PATCHES, software.EXTRA_TIME_"+Setup.GRANULARITY+" as EXTRA_TIME,("+query_no_vul+") as tb where EXTRA_TIME."+id_table+"=tb."+id_table+" and EXTRA_TIME.P_ID=PATCHES.P_ID and PATCHES.RELEASES LIKE '%"+release+"%' and VULNERABILITIES.V_ID=PATCHES.V_ID "+or_vulnerability+";";
					String vulnerableQuery = query_vul+" FROM "+module+" as tb, software.PATCHES AS p, software.VULNERABILITIES AS VULNERABILITIES where tb.Patched=1 and tb.Occurrence='before' and p.P_ID=tb.P_ID AND p.V_ID=VULNERABILITIES.V_ID "+or_vulnerability+" and p.RELEASES like '%"+release+"%';";
					System.out.println(neutralQuery);
					System.out.println(vulnerableQuery);
					if(executeQuery(vulnerableQuery,project+"_"+release)){
						executeQuery(neutralQuery,project+"_"+release);
					}
					count++;
					Setup.progressOverall=count/numberQuerys;
				}
			}
		}
		System.out.println("Progressos");
		System.out.println(Setup.progressOverall);
		System.out.println(Setup.progressQuery);
	}
	
	public boolean executeQuery(String query, String nameFileOutput) throws IOException{
		
		nameFileOutput = nameFileOutput.replace("/", "->");
		
		StringBuilder instances = new StringBuilder();
		
		java.sql.Connection conector = null;
        try {
            conector = DriverManager.getConnection("jdbc:mysql://localhost/software", "root", "root");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Statement stm = conector.createStatement();
            ResultSet rs = stm.executeQuery(query);
            rs.last();
            int total = rs.getRow();
            rs.beforeFirst();
            System.out.println(total);
            if(total==0) return false;
            while (rs.next()) {
            	Instance output = new Instance(metrics);
            	for (String metric : metrics) {
            		output.put(metric, rs.getString(metric));
				}
            	instances.append(output.toString());
            	Setup.progressQuery=rs.getRow()/total;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("e = " + e);
            e.printStackTrace();
        } finally {
            try {
                conector.close();
            } catch (SQLException ex) {
                System.out.println("ex = " + ex);
                ex.printStackTrace();
            }
        }
        
        //writing in file
        FileReader fr = null;
		try {
			fr = new FileReader(PATH+nameFileOutput+".csv");
			fr.close();
		} catch (FileNotFoundException e1) {
			File file = new File(PATH+nameFileOutput+".csv");
			FileWriter fw = new FileWriter(file);
			BufferedWriter b = new BufferedWriter(fw);
			Instance output = new Instance(metrics);
        	for (String metric : metrics) {
        		output.put(metric, metric);
			}
        	b.write(output.toString());
        	b.close();
        	fw.close();
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(PATH+nameFileOutput+".csv", true));
		bw.write(instances + "");
		bw.close();
		return true;
	}
	
	
}
