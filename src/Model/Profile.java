package Model;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

//XML imports below
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Profile implements Serializable {
    private static final long serialVersionUID = -5L;
    /* the profiles .ser files aren't recognised by the
     * program if this line is in the program.
     * So maybe the "serialisation" is broken... but the program is functional.
     * and profiles are in .ser files... so i can live with that. */
    private String name;
    private String password;
    private ArrayList<Module> modules;

    public Profile() {
        this.name = "";
        this.password = "";
        this.modules = new ArrayList<>();
        //this.milestone = new Milestone();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }

    public void addModule(Module module){
        modules.add(module);
    }

    public static Profile InitialiseStudyProfile(Profile profile, File file){
        try{
            profile.getModules().clear();
            File xmlFile = file;
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("module");//returns list of modules

            // for each module, read the details
            for (int i = 0; i < nList.getLength(); i++) {
                Module module = new Module();
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String moduleName = eElement.getAttribute("moduleName");
                    String moduleCode = eElement.getAttribute("moduleCode");
                    String moduleDescription = eElement.getAttribute("moduleDescription");

                    module.setName(moduleName);
                    module.setCode(moduleCode);
                    module.setModuleDescription(moduleDescription);

                    profile.getModules().add(module);//adding the new module to the semester profile modules list
                }

                NodeList assignmentList = nNode.getChildNodes();

                for (int j = 0; j < assignmentList.getLength(); j++) {
                    Node n1Node = assignmentList.item(j);
                    if (n1Node.getNodeType() == Node.ELEMENT_NODE) {

                        Element e1Element = (Element) n1Node;

                        String assignmentName = e1Element.getElementsByTagName("name").item(0).getTextContent();
                        String assignmentTypeString = e1Element.getElementsByTagName("type").item(0).
                                getTextContent();
                        Assignment.AssignmentType assignmentType = Assignment.AssignmentType.
                                valueOf(assignmentTypeString.toUpperCase());
                        double assignmentWeighting = Double.parseDouble(e1Element.
                                getElementsByTagName("weighting").item(0).getTextContent());
                        Date start = new Date();
                        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            start = df.parse(e1Element.getElementsByTagName("start").item(0).getTextContent());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date end  = new Date();
                        try {
                            end = df.parse(e1Element.getElementsByTagName("end").item(0).getTextContent());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String assignmentDescription = e1Element.getElementsByTagName("description").
                                item(0).getTextContent();

                        Assignment assignment = new Assignment(assignmentName, assignmentDescription,
                                start, end, assignmentWeighting, assignmentType, module);
                        //assignment.setModule(module);
                        module.getAssignments().add(assignment);
                    }
                }
            }

            System.out.println("Number of modules is:");
            System.out.println(profile.getModules().size());

        }catch (Exception e){
            e.printStackTrace();
        }
        return profile;
    }

    public static boolean isValid(File file){
        Profile testProfile = new Profile();
        InitialiseStudyProfile(testProfile, file);
        boolean result = false;
        if(!testProfile.getModules().isEmpty()){
            if(!testProfile.getModules().get(0).getAssignments().isEmpty())
                result = true;
        }
        else{
            result = false;
        }
        return result;
    }

    public int getTimeSpent(Module module)
    {
        int time = 0;
        for (Assignment assignment : module.getAssignments())
            for (Task task : assignment.getTasks())
                for (Activity activity : task.getActivities())
                        time += activity.getCompletedValue();
        return time;
    }

}
