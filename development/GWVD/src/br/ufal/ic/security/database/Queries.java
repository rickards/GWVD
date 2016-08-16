package br.ufal.ic.security.database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;

import br.ufal.ic.security.struct.Instance;
import br.ufal.ic.security.struct.Setup;

public class Queries {
	
	private String[] metrics;
	
	public void buildingMetrics(){
		String[] metrics_functions = {"AltCountLineCode","CountLine","CountLineCode","CountLineCodeExe","CountLineCodeDecl","Cyclomatic","CyclomaticModified","CyclomaticStrict","Knots","Essential","MaxEssentialKnots","MinEssentialKnots","CountPath","AltCountLineComment","CountLineComment","RatioCommentToCode","CountStmt","CountStmtExe","CountStmtDecl","CountStmtEmpty","CountLineBlank","CountLineInactive","CountSemicolon","MaxNesting","CountInput","CountOutput","CountLinePreprocessor","Patched"};
		String[] metrics_files = {"AltAvgLineBlank","AltAvgLineComment","AltCountLineCode","AvgCyclomatic","AvgCyclomaticStrict","AvgLine","AvgLineCode","CountLineBlank","CountLineCodeDecl","CountLineComment","CountLinePreprocessor","CountStmt","CountStmtEmpty","MaxCyclomaticModified","MaxEssential","RatioCommentToCode","SumCyclomaticModified","SumEssential","AltCountLineComment","AvgCyclomaticModified","AvgEssential","AvgLineBlank","AvgLineComment","CountDeclClass","CountDeclFunction","CountLine","CountLineCode","CountLineCodeExe","CountLineInactive","CountSemicolon","CountStmtDecl","CountStmtExe","MaxCyclomatic","MaxCyclomaticStrict","MaxNesting","SumCyclomatic","SumCyclomaticStrict","CountPath","FanIn","FanOut","AvgFanIn","AvgFanOut","MaxFanIn","MaxFanOut","AvgMaxNesting","SumMaxNesting","MaxMaxNesting","HK","DIT","NOC","CBC","RFC","CBO","LCOM","Patched"};
		if(Setup.GRANULARITY.equals("FUNCTIONS")) metrics = metrics_functions;
		if(Setup.GRANULARITY.equals("FILES")) metrics = metrics_files;
	}
	
	public void buildingQuery(){
		//lokijjugygtfrdeswa
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
