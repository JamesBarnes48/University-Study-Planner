package Controller;

import java.time.*;
import java.time.DayOfWeek;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import Model.*;
import Model.Module;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

/**
 *
 * @author mattcorcoran
 */

public class StatisticsController implements Initializable {

    private static Profile profile;

    //link with FXML file - Statistics.FMXL
    @FXML private BarChart<?, ?> DailyHoursChart;
    @FXML private CategoryAxis x;
    @FXML private NumberAxis y;
    @FXML private Label TipText;
    @FXML private PieChart pieChart;
    @FXML private Label workTodayNum;


    DayOfWeek[] daysOfWeek = DayOfWeek.values();
    Calendar calendar = Calendar.getInstance();
    int currentDayofWeekInt = calendar.get(Calendar.DAY_OF_WEEK);
    DayOfWeek currentdayOfWeek = (daysOfWeek[currentDayofWeekInt-1]);

    //getting the user profile from other part of system
    public void setProfile(Profile profile) { this.profile = profile; }

    //link to UI manager
    UIManager ui = new UIManager();

    /*
    public void statsBack(ActionEvent event){
        try {
            ui.statsBack(profile, event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
    public void initData(){
        getTaskHours();
    }

    //bar Chart
    //adding to chart
    public int getTaskHours() {
        int hourCount = 0;
        int activityHoursDone = 0;
        for (Module m : profile.getModules()) {
            for (Assignment assign : m.getAssignments()) {
                for (Task task : assign.getTasks()) {
                    for (Activity activity : task.getActivities()) {
                        activityHoursDone = (int) activity.getCompletedValue();
                        hourCount = (int) (hourCount + activityHoursDone);
                    }
                }
            }
        }
        return hourCount;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initData();
        //setWorkTodayNum();

        int dayVal = currentdayOfWeek.getValue();
        XYChart.Series set1 = new XYChart.Series<>();
        set1.setName(profile.getName() + " - Module Hours Done");
        //set1.getData().add(new XYChart.Data(profile.getModules().get(0).getName(), getMonHours()));
        for(Module module : profile.getModules()){
            set1.getData().add(new XYChart.Data(module.getName(), profile.getTimeSpent(module)));
        }

        //add all data to bar Chart
        DailyHoursChart.getData().addAll(set1);

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
        for(Module module : profile.getModules()){
            pieChartData.add(new PieChart.Data(module.getName(), module.getAssignments().size()));
        }

        //add all data to PieChart
        pieChart.setData(pieChartData);

    }
}