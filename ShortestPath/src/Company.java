import java.util.ArrayList;
import java.util.List;

public class Company {

    // IMPORTANT: DO NOT MODIFY THIS CLASS
    public static class Employee {

        private final int id;
        private final String name;
        private List<Employee> reports;

        public Employee(int id, String name) {
            this.id = id;
            this.name = name;
            this.reports = new ArrayList<Employee>();
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<Employee> getReports() {
            return reports;
        }

        public void addReport(Employee employee) {
            reports.add(employee);
        }
    }
    
    
    
    /*
     * 1. passed in a list of employee that report to a upper level employee.
     * 2. If target found employee, record this node and return.
     * 3. otherwise, we trace through every path until it is found.
     *  
     * An Employee node will be added to the listToAdd first, then its child list of reports again passed recursively to this function, until the path is exhausted. 
     * If still not found, the added node is removed. 
     */
    
    public static boolean traceEmployeePath(List<Employee> ListIn, Employee EmployeeToFind, List<Employee> listToAdd){
    	
    	boolean Empfound = false;
    	
    	Employee currentnode = null;
    	for(int i=0; i<ListIn.size(); i++){		//scan through each employee in ListIn
    		currentnode = ListIn.get(i);		//each employee in ListIn
    		List<Employee> reportsOfCurrentnode = currentnode.getReports();	//get next level list of reports for each employee 
	    	if (! reportsOfCurrentnode.isEmpty()){
	    		if (reportsOfCurrentnode.contains(EmployeeToFind)){
	    			Empfound = true;
	    			break;
	    		}
	    		else{
	    			listToAdd.add(currentnode);			//store current node in list1
	    			int currentnodeindex = listToAdd.indexOf(currentnode); 
	    			boolean found = traceEmployeePath(reportsOfCurrentnode,EmployeeToFind,listToAdd );
	    			if (found){
	    				return true;
	    			}
	    			else{
	    				listToAdd.remove(currentnode);
	    			}
	    		}
	    	}
	    	else{
	    		continue;			//if currentnode has no child, go to next node at same level
	    	}
    		
    	}    	
    	
    	if (Empfound == true){
	    	listToAdd.add(currentnode);			//store current node in list1	    		
    		listToAdd.add(EmployeeToFind);		//the target employee is the child of current node
        	return true;
    	}    	
    	
    	return false;
    }
    
    

    /*
     * Read the attached PDF for more explanation about the problem
     * Note: Don't modify the signature of this function
     * @param ceo
     *
     * @param firstEmployee
     *            
     * @param secondEmployee
     *            
     * @return the shortest path from First Employee to the Second Employee.
     */
    @SuppressWarnings("unused")
    public static String shortestPath(Employee ceo, Employee firstEmployee, Employee secondEmployee) {
       // Implement me
    	
    	
    	 List<Employee> reportsceo = ceo.getReports();
    	 List<Employee> reportsfirstEmp = firstEmployee.getReports();
    	 List<Employee> reportssecondEmp = secondEmployee.getReports();
    	 
    	 List<Employee> ListEmp1Path = new ArrayList<Employee>();
    	 List<Employee> ListEmp2Path = new ArrayList<Employee>();
    	
    	 
    	 Employee firstnode = ceo;
    	 Employee currentnode = firstnode;
    	 List<Employee> reportsCurrentnode = currentnode.getReports();
    	 
       	 
    	 boolean Emp1found = false;
    	 boolean Emp2found = false;
    	 int firstEmpLvl = 0;					//this is not required. We use size of ListEmp1Path to determine employee 1 level
    	 int secondEmpLvl = 0;					//this is not required. We use size of ListEmp2Path to determine employee 2 level


    	//create 2 lists of Employee, one for Emp1 path, 
    	//and the other for Emp2 path. The first Employee is always ceo  
		ListEmp1Path.add(firstnode);			//store first node in list1 (ceo)    	 
		ListEmp2Path.add(firstnode);			//store first node in list2 (ceo)		
    	
    	
    	if (! reportsCurrentnode.isEmpty()){	//ceo should always have some employees below him
    		
    		 //trace first employee path
    		if (reportsCurrentnode.contains(firstEmployee)){	//if Emp1 is directly below ceo, we add him into list1 and job done for Emp1.
    			Emp1found = true;
    			ListEmp1Path.add(firstEmployee);
    		}		
			else{
				//pass in list of employee directly under ceo. traceEmployeePath() will trace recursively until employee is found. ListEmp1Path will consist complete path of Emp1.
				traceEmployeePath(reportsCurrentnode, firstEmployee, ListEmp1Path);		
			}	    	

    		 //trace second employee path
    		if (reportsCurrentnode.contains(secondEmployee)){	//if Emp2 is directly below ceo, we add him into list2 and job done for Emp2.
    			Emp2found = true;
    			ListEmp2Path.add(secondEmployee);
    		}		
			else{
				//pass in list of employee directly under ceo. traceEmployeePath() will trace recursively until employee is found. ListEmp2Path will consist complete path of Emp2.
    	    	traceEmployeePath(reportsCurrentnode, secondEmployee, ListEmp2Path);		 
			}
    	} 
    	 
    	int Emp1Lvl = ListEmp1Path.size();
    	int Emp2Lvl = ListEmp2Path.size();
		int currentEmp1Lvl = Emp1Lvl-1;
		int currentEmp2Lvl = Emp2Lvl-1;	

		String separatorStr = " > ";
    	String s = new String("");
    	
    	if (Emp1Lvl == Emp2Lvl){
    		
    		//starting from Emp 1
			s = s.concat(ListEmp1Path.get(currentEmp1Lvl).getName()) + separatorStr;
    		
    		while(currentEmp1Lvl >= 0){
    			currentEmp1Lvl--;	//move one employee level up 
    			
    			s = s.concat(ListEmp1Path.get(currentEmp1Lvl).getName()) + separatorStr;			//store next upper level of Emp 1
    			if (ListEmp1Path.get(currentEmp1Lvl).getName() == ListEmp2Path.get(currentEmp1Lvl).getName()){	//Emp 1 and 2 have the same reporting boss, we terminate the trace here for Emp 1
        			break;
    			}
    		}
    			
    		//We now trace Emp2 starting from current last Emp1 position, which is the reporting boss of Emp1 1 and 2
    		currentEmp2Lvl = currentEmp1Lvl;		//skip this level of Emp 2, since it has been captured above
    		currentEmp2Lvl++;						//move one level down
    		while (currentEmp2Lvl <= Emp2Lvl-1){
    			s = s.concat(ListEmp2Path.get(currentEmp2Lvl).getName()) + separatorStr;
    			currentEmp2Lvl++;
    		}
    	
    	}
    	else if (Emp1Lvl < Emp2Lvl){
    		//we check if Emp1 and Emp2 are along the same path, ie, ListEmp2Path contains both Emp1 and Emp2
    		if (ListEmp2Path.contains(firstEmployee)){	//if so, we extract path from Emp1 to Emp2 in ListEmp2Path 
    			while(currentEmp1Lvl <= currentEmp2Lvl){
    				s = s.concat(ListEmp2Path.get(currentEmp1Lvl).getName()) + separatorStr;
    				currentEmp1Lvl++;
    			}
    		}
    		else{	//Emp1 and Emp2 are not along the same path
    			//navigate Emp2 up until it is same level as Emp1
    			while (currentEmp2Lvl > currentEmp1Lvl){
    				currentEmp2Lvl--;
    			}
    			
    			//move both Emp1 and Emp2 path 1 level up
    			currentEmp1Lvl--;	
    			currentEmp2Lvl--;		
    			
    			int Emp1meetEmp2lvl = 0;
    			//search path of Emp1 and Emp2 upwards until a common point is reached, ie, a same reporting person 
    			while(currentEmp1Lvl >= 0){		//using currentEmp1Lvl to check is sufficient
    				
    				if (ListEmp1Path.get(currentEmp1Lvl).getName() == ListEmp2Path.get(currentEmp1Lvl).getName()){	//Path of Emp 1 and Emp 2 is meeting here. we terminate the trace for Emp 1
    					Emp1meetEmp2lvl = currentEmp1Lvl;
    					break;
    					
    				}
        			currentEmp1Lvl--;	// 1 level up
    			}
    			
    			//now we extract path from Emp1Lvl till Emp1meetEmp2lvl
    			currentEmp1Lvl = Emp1Lvl-1;		//reset currentEmp1Lvl 
    			while(currentEmp1Lvl >= Emp1meetEmp2lvl){
    				s = s.concat(ListEmp1Path.get(currentEmp1Lvl).getName()) + separatorStr;
    				currentEmp1Lvl--;
    			}
    			
    			//then we extract path from Emp1meetEmp2lvl till Emp2Lvl
        		currentEmp2Lvl = Emp1meetEmp2lvl;		//skip this level of Emp 2, since it has been captured above
        		currentEmp2Lvl++;						//move one level down
    			
    			while(currentEmp2Lvl <= Emp2Lvl-1){
    				s = s.concat(ListEmp2Path.get(currentEmp2Lvl).getName()) + separatorStr;
    				currentEmp2Lvl++;
    			}
    		}

    	}
    	else{	//Emp1Lvl > Emp2Lvl
    		//we check if Emp1 and Emp2 are along the same path, ie, ListEmp1Path contains both Emp1 and Emp2
    		if (ListEmp1Path.contains(secondEmployee)){	//if so, we extract path from Emp1 to Emp2 in ListEmp2Path 
    			while(currentEmp1Lvl >= currentEmp2Lvl){
    				s = s.concat(ListEmp1Path.get(currentEmp1Lvl).getName()) + separatorStr;
    				currentEmp1Lvl--;
    			}
    		}
    		else{	//Emp1 and Emp2 are not along the same path
    			//navigate Emp1 up until it is same level as Emp2
    			while (currentEmp1Lvl > currentEmp2Lvl){
    				currentEmp1Lvl--;
    			}
    			
    			//move both Emp1 and Emp2 path 1 level up
    			currentEmp1Lvl--;	
    			currentEmp2Lvl--;		
    			
    			int Emp1meetEmp2lvl = 0;
    			//search path of Emp1 and Emp2 upwards until a common point is reached, ie, a same reporting person 
    			while(currentEmp1Lvl >= 0){		//using currentEmp1Lvl to check is sufficient
    				
    				if (ListEmp1Path.get(currentEmp1Lvl).getName() == ListEmp2Path.get(currentEmp1Lvl).getName()){	//Path of Emp 1 and Emp 2 is meeting here. we terminate the trace for Emp 1
    					Emp1meetEmp2lvl = currentEmp1Lvl;
    					break;
    					
    				}
        			currentEmp1Lvl--;	// 1 level up
    			}

    			//now we extract path from Emp1Lvl till Emp1meetEmp2lvl
    			currentEmp1Lvl = Emp1Lvl-1;		//reset currentEmp1Lvl 
    			while(currentEmp1Lvl >= Emp1meetEmp2lvl){
    				s = s.concat(ListEmp1Path.get(currentEmp1Lvl).getName()) + separatorStr;
    				currentEmp1Lvl--;
    			}

    			//then we extract path from Emp1meetEmp2lvl till Emp2Lvl
        		currentEmp2Lvl = Emp1meetEmp2lvl;		//skip this level of Emp 2, since it has been captured above
        		currentEmp2Lvl++;						//move one level down

    			while(currentEmp2Lvl <= Emp2Lvl-1){
    				s = s.concat(ListEmp2Path.get(currentEmp2Lvl).getName()) + separatorStr;
    				currentEmp2Lvl++;
    			}
        		
    		}
    		
    	}
    		
    		
		//final string
		s = s.substring(0, s.length() - separatorStr.length());
    	
    	
	    return s;
     	
    }
    

    public static void main(String[] args) {
        Employee Eugene = new Employee(1, "Eugene");
        Employee Jose = new Employee(2, "Jose");
        Employee Kelvin = new Employee(3, "Kelvin");
        Employee Terence = new Employee(4, "Terence");
        Employee Dennis = new Employee(5, "Dennis");
        Employee Eunice = new Employee(6, "Eunice");
        Employee Bryan = new Employee(7, "Bryan");
        Employee Gabriel = new Employee(8, "Gabriel");
        Employee Jimmy = new Employee(9,"Jimmy");


        Employee Mark = new Employee(10,"Mark");
        Employee David = new Employee(11,"David");
        
        Employee Michael = new Employee(12,"Michael");
        Employee Alex = new Employee(13,"Alex");        

       
        
        Eugene.addReport(Jose);
        Eugene.addReport(Kelvin);
        Eugene.addReport(Terence);

        Jose.addReport(Dennis);
        Jose.addReport(Eunice);
        Jose.addReport(Bryan);

        Eunice.addReport(Gabriel);
        Eunice.addReport(Jimmy);

        Gabriel.addReport(Mark);
        Gabriel.addReport(David);
        
        Jimmy.addReport(Michael);
        Jimmy.addReport(Alex);      
        
        
        
		//pls add other test cases to fully cover different scenarios.
        System.out.println(shortestPath(Eugene, Alex, Bryan));        
        

        //Emp1lvl == Emp2lvl
        System.out.println(shortestPath(Eugene, Bryan, Eunice));   
        System.out.println(shortestPath(Eugene, Jose, Kelvin));
        System.out.println(shortestPath(Eugene, Gabriel, Jimmy));
        
        
        //Emp1lvl < Emp2lvl
    	System.out.println(shortestPath(Eugene, Eunice, Jimmy));
        System.out.println(shortestPath(Eugene, Eunice, Gabriel));
        System.out.println(shortestPath(Eugene, Dennis, Jimmy));        
        System.out.println(shortestPath(Eugene, Kelvin, Bryan));        
        
        
        //Emp1lvl > Emp2lvl
        System.out.println(shortestPath(Eugene, Gabriel, Jose));        
        System.out.println(shortestPath(Eugene, Gabriel, Terence));
        System.out.println(shortestPath(Eugene, Eunice, Terence));

        
    }
}
