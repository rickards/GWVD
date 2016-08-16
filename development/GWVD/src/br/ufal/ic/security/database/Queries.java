package br.ufal.ic.security.database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;

import br.ufal.ic.security.struct.Instance;
import br.ufal.ic.security.struct.Setup;

public class Queries {
	
	private String[] metrics;
	
	//TODO: Refactoring: this method not supposed to be here and yes in setups
	public void buildingMetrics(){
		String[] metrics_functions = {"AltCountLineCode","CountLine","CountLineCode","CountLineCodeExe","CountLineCodeDecl","Cyclomatic","CyclomaticModified","CyclomaticStrict","Knots","Essential","MaxEssentialKnots","MinEssentialKnots","CountPath","AltCountLineComment","CountLineComment","RatioCommentToCode","CountStmt","CountStmtExe","CountStmtDecl","CountStmtEmpty","CountLineBlank","CountLineInactive","CountSemicolon","MaxNesting","CountInput","CountOutput","CountLinePreprocessor","Patched"};
		String[] metrics_files = {"AltAvgLineBlank","AltAvgLineComment","AltCountLineCode","AvgCyclomatic","AvgCyclomaticStrict","AvgLine","AvgLineCode","CountLineBlank","CountLineCodeDecl","CountLineComment","CountLinePreprocessor","CountStmt","CountStmtEmpty","MaxCyclomaticModified","MaxEssential","RatioCommentToCode","SumCyclomaticModified","SumEssential","AltCountLineComment","AvgCyclomaticModified","AvgEssential","AvgLineBlank","AvgLineComment","CountDeclClass","CountDeclFunction","CountLine","CountLineCode","CountLineCodeExe","CountLineInactive","CountSemicolon","CountStmtDecl","CountStmtExe","MaxCyclomatic","MaxCyclomaticStrict","MaxNesting","SumCyclomatic","SumCyclomaticStrict","CountPath","FanIn","FanOut","AvgFanIn","AvgFanOut","MaxFanIn","MaxFanOut","AvgMaxNesting","SumMaxNesting","MaxMaxNesting","HK","DIT","NOC","CBC","RFC","CBO","LCOM","Patched"};
		if(Setup.GRANULARITY.equals("FUNCTIONS")) metrics = metrics_functions;
		if(Setup.GRANULARITY.equals("FILES")) metrics = metrics_files;
	}
	
	public void buildingQuery(){
		for (String project : Setup.PROJECTS) {
			for (String module : Setup.getModules(project)) {
				String query_no_vul = "SELECT * FROM "+module+" f2s WHERE f2s.Patched=0 and f2s.Occurrence='before' and f2s.ID_Function NOT IN (SELECT distinct tb.ID_Function FROM "+module+" tb, (SELECT * FROM "+module+" WHERE patched=1 AND Occurrence='before') vul WHERE tb.FilePath=vul.FilePath AND tb.NameMethod=vul.NameMethod";
				String query_vul = "SELECT DISTINCT tb.FilePath,tb.NameMethod";
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
					String neutralQuery = "SELECT VULNERABILITIES.V_CLASSIFICATION,PATCHES.RELEASES,MAIN.* FROM software.VULNERABILITIES as VULNERABILITIES,software.PATCHES as PATCHES, software.EXTRA_TIME_FUNCTIONS as EXTRA_TIME,("+query_no_vul+") as MAIN where EXTRA_TIME.ID_Functions=MAIN.ID_Function and EXTRA_TIME.P_ID=PATCHES.P_ID and PATCHES.RELEASES LIKE '%"+release+"%' and VULNERABILITIES.V_ID=PATCHES.V_ID AND "+or_vulnerability+";";
					String vulnerableQuery = query_vul+",tb.Patched FROM "+module+" as tb, software.PATCHES AS p, software.VULNERABILITIES AS v where tb.Patched=1 and tb.Occurrence='before' and p.P_ID=tb.P_ID AND p.V_ID=v.V_ID "+or_vulnerability+" and p.RELEASES like '%"+release+"%';";
					System.out.println(neutralQuery);
					System.out.println(vulnerableQuery);
					//TODO: [...]
				}
			}
		}
	}
	
	public LinkedHashSet<Instance> executeQuery(String query){
		LinkedHashSet<Instance> instances = new LinkedHashSet<>(); 
		java.sql.Connection conector = null;
        try {
            conector = DriverManager.getConnection("jdbc:mysql://localhost/security", "root", "admin");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Statement stm = conector.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
            	Instance output = new Instance(metrics);
            	for (String metric : metrics) {
            		output.put(metric, rs.getString(metric));
				}
            	instances.add(output);
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
        return instances;
	}
	
}
