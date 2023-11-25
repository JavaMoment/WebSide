package com.controllers.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.hbar.HorizontalBarChartDataSet;
import org.primefaces.model.charts.hbar.HorizontalBarChartModel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import com.services.AnalistaBeanRemote;
import com.services.EstudianteBeanRemote;
import com.services.ItrBeanRemote;
import com.services.TutorBeanRemote;
import com.services.UsuarioBeanRemote;

@Named("chartsView")
@RequestScoped
public class ChartsView implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UsuarioBeanRemote userBean;
    @EJB
    private EstudianteBeanRemote studentBean;
    @EJB
    private AnalistaBeanRemote analistaBean;
    @EJB
    private TutorBeanRemote tutorBean;
    @EJB
    private ItrBeanRemote itrBean;
    
    private HorizontalBarChartModel hbarModel;
    private PieChartModel pieModel;
    
    private int totalUsers;
    private int itrsCount;
    private int itrsActiveCount;
    private int itrsInactiveCount;


    @PostConstruct
    public void init() {
        totalUsers = userBean.selectAll().size();
        itrsCount = itrBean.selectAll().size();
        itrsActiveCount = itrBean.selectAllByActive(1).size();
        itrsInactiveCount = itrBean.selectAllByActive(0).size();
        createHorizontalBarModel();
        createPieModel();
    }
    
    public void createHorizontalBarModel() {
        hbarModel = new HorizontalBarChartModel();
        ChartData data = new ChartData();

        HorizontalBarChartDataSet hbarDataSet = new HorizontalBarChartDataSet();
        hbarDataSet.setLabel("(%) Tipos de usuarios");
        
        List<Number> values = new ArrayList<>();
        values.add((studentBean.selectAll().size() * 100) / totalUsers);
        values.add((analistaBean.selectAll().size() * 100) / totalUsers);
        values.add((tutorBean.selectAll().size() * 100) / totalUsers);
        hbarDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
//        bgColor.add("rgba(54, 162, 235, 0.2)");
//        bgColor.add("rgba(153, 102, 255, 0.2)");
//        bgColor.add("rgba(201, 203, 207, 0.2)");
        hbarDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        borderColor.add("rgb(75, 192, 192)");
//        borderColor.add("rgb(54, 162, 235)");
//        borderColor.add("rgb(153, 102, 255)");
//        borderColor.add("rgb(201, 203, 207)");
        hbarDataSet.setBorderColor(borderColor);
        hbarDataSet.setBorderWidth(1);

        data.addChartDataSet(hbarDataSet);

        List<String> labels = new ArrayList<>();
        labels.add("Estudiantes");
        labels.add("Analistas");
        labels.add("Tutores");
        data.setLabels(labels);
        hbarModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        linearAxes.setBeginAtZero(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addXAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("(%) Tipos de usuarios");
        options.setTitle(title);

        hbarModel.setOptions(options);
    }
    
    private void createPieModel() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(userBean.selectAllByActive(1).size());
        values.add(userBean.selectAllByActive(0).size());
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Usuarios activos");
        labels.add("Usuarios inactivos");
        data.setLabels(labels);

        pieModel.setData(data);
    }

    public HorizontalBarChartModel getHbarModel() {
        return hbarModel;
    }

    public void setHbarModel(HorizontalBarChartModel hbarModel) {
        this.hbarModel = hbarModel;
    }

	public int getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}

	public int getItrsCount() {
		return itrsCount;
	}

	public void setItrsCount(int itrsCount) {
		this.itrsCount = itrsCount;
	}

	public int getItrsActiveCount() {
		return itrsActiveCount;
	}

	public void setItrsActiveCount(int itrsActiveCount) {
		this.itrsActiveCount = itrsActiveCount;
	}

	public int getItrsInactiveCount() {
		return itrsInactiveCount;
	}

	public void setItrsInactiveCount(int itrsInactiveCount) {
		this.itrsInactiveCount = itrsInactiveCount;
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}
}

