package visitors;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitProject {

	// Store Metrics
	StoreMetrics projectMetrics;

	// package path list
	List<String> packagesPath;

	// package name list
	List<String> packagesName;

	// ca
	//int caCounter;

	// map of all import
	Map<String, List<List<ImportDeclaration>>> imports;

	// map of all package metrics
	Map<String, StoreMetrics> allMetrics;

	public VisitProject(String folderName) throws Exception {
		projectMetrics = new StoreMetrics(folderName);
		packagesPath = new ArrayList<>();
		packagesName = new ArrayList<>();
		//caCounter = 0;
		imports = new HashMap<>();
		allMetrics = new HashMap<>();
		getPackages(folderName);
	}

	private void getPackages(String folderName) {
		File dir = new File(folderName);
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				getPackages(file.getAbsolutePath());
			}
		} else if (dir.getName().contains(".java")) {
			if (!packagesPath.contains(dir.getParent())) {
				packagesPath.add(dir.getParent());

				packagesName.add(dir.getParentFile().getName());

				// System.out.println("directory path : " + dir.getParent());
				// System.out.println("directory name : " +
				// dir.getParentFile().getName());
			}

		} else {
			// System.out.println(dir.getAbsolutePath());
		}
	}

	public StoreMetrics returnMetrics() throws Exception {
		for (String packagePath : packagesPath) {
			// ce
			int ceCounter = 0;
			List<List<ImportDeclaration>> idsInPackage = new ArrayList<List<ImportDeclaration>>();
			VisitPackage vp = new VisitPackage(packagePath);
			// System.out.println(packagePath);
			StoreMetrics packageSM = vp.returnMetrics();
			
			// report: package-level
//			packageSM.printMetrics(4);
//			System.out.println("");
			
			List<CompilationUnit> cus = vp.getCUList();
			String packageName = "";
			for (CompilationUnit cu : cus) {
				packageName = cu.getPackage().getName().getName();
				List<ImportDeclaration> ids = cu.getImports();
				idsInPackage.add(ids);
				if (ids != null) {
					for (ImportDeclaration id : ids) {
						String fullImport = id.getName().toString();
						if (compareString(fullImport, packagesName,packageName)) {
							ceCounter++;
							break;
						}
					}
				}
			}
			packageSM.addMetricValue("CE", ceCounter);
			// projectMetrics.aggregate(vp.returnMetrics());
			// System.out.println("package name : " + packageName + " CE : " +
			// ceCounter);
			allMetrics.put(packageName, packageSM);
			imports.put(packageName, idsInPackage);
		}

		for (String packageName : packagesName) {
			// ca
			int caCounter = 0;
			for (String key : imports.keySet())
			{
				List<List<ImportDeclaration>> importlists = imports.get(key);
				//System.out.println("importlist: " + importlists);
				if(!key.equals(packageName))
				{
					
					for(List<ImportDeclaration> importlist : importlists)
					{
						if(importlist != null)
						{
							for (ImportDeclaration id : importlist) {
								String fullImport = id.getName().toString();
								// System.out.println("full import : " + fullImport);
								if (fullImport.contains(packageName + ".") || fullImport.equals(packageName)) {
									caCounter++;
									// System.out.println("package name : " + packageName +
									// " CA : " + caCounter);
									//System.out.println(fullImport);
									break;
								}
							}
						}
					
					}
					
				}
				else 
				{
					//System.out.println("key: " + key + " packageName: " + packageName);
				}
				
			}
		
			
			StoreMetrics singleMetrics = allMetrics.get(packageName);
			singleMetrics.addMetricValue("CA", caCounter);
			double ceCounter = singleMetrics.metricsList.get("CE").get(0);
			double instability;
			if(ceCounter != 0)
			{
				instability = ceCounter / (ceCounter + caCounter);
			}
			else
			{
				instability = 0;
			}
			singleMetrics.addMetricValue("I", instability);
			// System.out.println("package name : " + packageName + " I : " +
			// instability);
			projectMetrics.aggregate(singleMetrics);
		}

		return projectMetrics;
	}

	private boolean compareString(String fullImport, List<String> packagesName, String currentPackageName) {
		boolean flag = false;
		for (String packageName : packagesName) {
			if(!packageName.equals(currentPackageName))
			{
				flag = fullImport.contains(packageName + ".") || fullImport.equals(packageName);
				if (flag) {
					break;
				}
			}
			
		}
		return flag;
	}
}