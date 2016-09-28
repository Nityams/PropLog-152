import java.util.*;


class Statement {
	private String conclusion;
	private List<String> conditions;
	public Statement(String conclusion, String... conditions) {
		this.conclusion = conclusion;
		this.conditions = Arrays.asList(conditions);
	}

	public List<String> getConditions()
	{
		return this.conditions;
	}
	public String getConclusion()
	{
		return this.conclusion;
	}
	// matches
}

public class Engine {
	private List<Statement> kbase = new LinkedList<Statement>();
	private Scanner scanner = new Scanner(System.in);

	public void add(Statement s) 
	{
		kbase.add(s); 
	}

	public boolean execute(List<String> goals) 
	{
		boolean result = false;
		//System.out.println(goals.get(0));

		if(goals.size() == 0)
		{
			return true;
		}else
		{
			if (goals.size() == 1)
			{
				for(int i=0; i<kbase.size(); i++)
				{
					System.out.println("Kbase: "+ kbase.get(i).getConclusion()+"= "+kbase.get(i).getConditions().size());
					System.out.println("Goals: "+ goals.get(0));
					if(kbase.get(i).getConditions().size() == 0)
					{
						if( kbase.get(i).getConclusion().equals(goals.get(0)))
							return true;
					}
					else if(kbase.get(i).getConditions().size() > 0)
					{
						if(kbase.get(i).getConclusion().equals(goals.get(0))) 
							result = execute(kbase.get(i).getConditions());
							
						if (result == true)
							return true;
							if (result == false && i == kbase.size()-1)
								return false;
						
					}
					else 
						return false;
				}
				return false;
			}else
			{
				for(int j=0; j< goals.size(); j++)
				{
					List<String> dummy = new ArrayList<>();
					dummy.add(goals.get(j));
					result = execute(dummy);
					if (result == false)
						return false;
				}
			}
			return result;
		}
	}
	

	public void repl() {
		List<String> goals = new LinkedList<String>();
		while(true) {
			System.out.print("?- ");
			String query = scanner.next();
			if (query.equals("quit")) {
				System.out.println("bye");
				break;
			}
			goals.clear();
			goals.add(query);
			boolean result = execute(goals);
			System.out.println(result);

		}
	}
	public static void main(String[] args) {
		Engine engine = new Engine();
		engine.add(new Statement("homerIsMale"));
		engine.add(new Statement("bartIsMale"));
		engine.add(new Statement("homerIsParentOfBart"));
		engine.add(new Statement("homerIsFatherOfBart","homerIsMale","homerIsParentOfBart" ));
		//nityam:
		engine.add(new Statement("margeIsFemale"));
		engine.add(new Statement("margeIsParentOfBart"));
		engine.add(new Statement("margeIsMotherOfBart","margeIsFemale","margeIsParentOfBart" ));
		engine.add(new Statement("lisaIsFemale","margeIsFemale","homerIsFemale"));
		engine.repl();
	}

}