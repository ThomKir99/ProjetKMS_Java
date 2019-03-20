import java.util.ArrayList;
import java.util.List;

public class Project extends Entity{
	private List<Group> groups;

	public Project(){
		super();
		this.groups = new ArrayList<Group>();
	}

	public List<Group> getGroups() {return groups;}

	public void setGroups(List<Group> groups) {this.groups = groups;}


}
