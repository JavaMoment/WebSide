package com.controllers.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.hbar.HorizontalBarChartDataSet;
import org.primefaces.model.charts.hbar.HorizontalBarChartModel;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.primefaces.model.charts.pie.PieChartOptions;

import com.entities.DtItr;
import com.entities.Estudiante;
import com.entities.HtEvento;
import com.entities.ReclamosCountByEstudiante;
import com.models.ReclamosCount;
import com.models.SemestreCount;
import com.services.AnalistaBeanRemote;
import com.services.DtItrBeanRemote;
import com.services.DtTiempoBeanRemote;
import com.services.EstudianteBeanRemote;
import com.services.HtEventoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.ReclamoCountBeanRemote;
import com.services.TutorBeanRemote;
import com.services.UsuarioBeanRemote;

@Named("dashboardView")
@ViewScoped
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
    @EJB
    private HtEventoBeanRemote htEventoBean;
    @EJB
    private DtTiempoBeanRemote dtTiempoBean;
    @EJB
    private DtItrBeanRemote dtItrBean;
    @EJB
    private ReclamoCountBeanRemote reclamoCountService;
    
    private HorizontalBarChartModel hbarModel;
    private PieChartModel pieModel;
    private LineChartModel eventosSeriesModel;
    private BarChartModel eventosBarModel;
    private LineChartModel reclamosSeriesModel;
    
    private int totalUsers;
    private int itrsCount;
    private int itrsActiveCount;
    private int itrsInactiveCount;
    private List<HtEvento> htEventos;
    private List<Estudiante> estudiantes;
    private List<DtItr> itrs;
    private List<Integer> availableYears;
    private List<ReclamosCount> reclamosCount;
    private List<String> availableReclamosYears;
    private List<String> availableReclamosMonths;
    
    private Estudiante selectedEstudiante;
    private DtItr selectedItr;
    private Integer selectedYear;
    private boolean groupBySemester = false;
    private String selectedMonth;
    private String selectedReclamosYear;
    
    @PostConstruct
    public void init() {
        totalUsers = userBean.selectAll().size();
        itrsCount = itrBean.selectAll().size();
        itrsActiveCount = itrBean.selectAllByActive(1).size();
        itrsInactiveCount = itrBean.selectAllByActive(0).size();
        estudiantes = studentBean.selectAll();
        
        itrs = dtItrBean.selectAll();
        availableYears = dtTiempoBean.getAvailableYears();
        
        selectedItr = itrs.get(0);
        selectedYear = availableYears.get(0);
        
        availableReclamosYears = reclamoCountService.getAvailableYears();
        availableReclamosMonths = reclamoCountService.getAvailableMonths();

        selectedEstudiante = estudiantes.get(0);
        selectedMonth = availableReclamosMonths.get(0);
        selectedReclamosYear = availableReclamosYears.get(0);
        
        createHorizontalBarModel();
        createPieModel();
        createEventosHistoricalLine();
        createReclamosHistoricalLine();
    }
    
    public void refreshEventosCharts() {
    	createEventosBarModel();
    	createEventosHistoricalLine();
    }
    
    public void createEventosBarModel() {
        eventosBarModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet  dataSet = new BarChartDataSet ();
        List<Number> values = new ArrayList<>();
        List<SemestreCount> semestresCount = htEventoBean.getSemestreCount(selectedItr.getSkItr(), selectedYear);
        semestresCount.forEach(h -> values.add(h.getCantEventos()));;
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(66, 245, 75)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        semestresCount.forEach(h -> labels.add("Semestre " + h.getSemestre()));
        data.setLabels(labels);
        
        BarChartOptions opts = new BarChartOptions();
    	opts.setMaintainAspectRatio(false);
    	CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        linearAxes.setBeginAtZero(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        opts.setScales(cScales);
    	Title title = new Title();
    	title.setDisplay(true);
    	title.setText("Cantidad de reclamos por semestre.");
    	opts.setTitle(title);
    	Legend legend = new Legend();
        legend.setDisplay(false);
        opts.setLegend(legend);

    	eventosBarModel.setData(data);
    	eventosBarModel.setOptions(opts);
    }
    
    public void createReclamosHistoricalLine() {
		reclamosCount = reclamoCountService.getReclamoCountBy(selectedEstudiante.getIdEstudiante(), selectedMonth, selectedReclamosYear);

		reclamosSeriesModel = new LineChartModel();
    	ChartData data = new ChartData();
    	
    	LineChartDataSet dataset = new LineChartDataSet();
    	List<Object> values = new ArrayList<>();
    	reclamosCount.forEach(r -> values.add(r.getCantReclamos()));
    	
    	dataset.setData(values);
    	dataset.setFill(false);
    	dataset.setLabel("Cantidad de reclamos");
    	dataset.setBorderColor("rgb(81, 228, 245)");
    	dataset.setTension(0.1);
    	
    	List<String> labels =  new ArrayList<>();
    	reclamosCount.forEach(r -> labels.add(r.getFechaCreacion()));
    	
    	data.addChartDataSet(dataset);
    	data.setLabels(labels);
    	
    	LineChartOptions opts = new LineChartOptions();
    	opts.setMaintainAspectRatio(false);
    	Title title = new Title();
    	title.setDisplay(true);
    	title.setText("Cantidad de reclamos a lo largo del tiempo seleccionado.");
    	opts.setTitle(title);
    	
    	reclamosSeriesModel.setData(data);
    	reclamosSeriesModel.setOptions(opts);
    }
    
    public void createEventosHistoricalLine() {
		htEventos = htEventoBean.getHtEventosBy(selectedItr.getSkItr(), selectedYear);

		eventosSeriesModel = new LineChartModel();
    	ChartData data = new ChartData();
    	
    	LineChartDataSet dataset = new LineChartDataSet();
    	List<Object> values = new ArrayList<>();
    	htEventos.forEach(h -> values.add(h.getCantEventos()));
    	
    	dataset.setData(values);
    	dataset.setFill(false);
    	dataset.setLabel("Cantidad de eventos");
    	dataset.setBorderColor("rgb(81, 228, 245)");
    	dataset.setTension(0.1);
    	
    	List<String> labels =  new ArrayList<>();
    	htEventos.forEach(h -> labels.add(h.getDtTiempo().getPkTiempo().toString()));
    	
    	data.addChartDataSet(dataset);
    	data.setLabels(labels);
    	
    	LineChartOptions opts = new LineChartOptions();
    	opts.setMaintainAspectRatio(false);
    	Title title = new Title();
    	title.setDisplay(true);
    	title.setText("Cantidad de eventos a lo largo del tiempo.");
    	opts.setTitle(title);
    	
    	eventosSeriesModel.setData(data);
    	eventosSeriesModel.setOptions(opts);
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
        hbarDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        borderColor.add("rgb(75, 192, 192)");
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

	public LineChartModel getEventosSeriesModel() {
		return eventosSeriesModel;
	}

	public void setEventosSeriesModel(LineChartModel eventosSeriesModel) {
		this.eventosSeriesModel = eventosSeriesModel;
	}

	public Estudiante getSelectedEstudiante() {
		return selectedEstudiante;
	}

	public void setSelectedEstudiante(Estudiante selectedEstudiante) {
		this.selectedEstudiante = selectedEstudiante;
	}

	public List<Estudiante> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(List<Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}

	public DtItr getSelectedItr() {
		return selectedItr;
	}

	public void setSelectedItr(DtItr dtItr) {
		this.selectedItr = dtItr;
	}

	public List<DtItr> getItrs() {
		return itrs;
	}

	public void setItrs(List<DtItr> itrs) {
		this.itrs = itrs;
	}

	public List<Integer> getAvailableYears() {
		return availableYears;
	}

	public void setAvailableYears(List<Integer> availableYears) {
		this.availableYears = availableYears;
	}

	public boolean getGroupBySemester() {
		return groupBySemester;
	}

	public void setGroupBySemester(boolean groupBySemester) {
		this.groupBySemester = !this.groupBySemester;
	}

	public Integer getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}

	public LineChartModel getReclamosSeriesModel() {
		return reclamosSeriesModel;
	}

	public void setReclamosSeriesModel(LineChartModel reclamosSeriesModel) {
		this.reclamosSeriesModel = reclamosSeriesModel;
	}

	public List<String> getAvailableReclamosYears() {
		return availableReclamosYears;
	}

	public void setAvailableReclamosYears(List<String> availableReclamosYears) {
		this.availableReclamosYears = availableReclamosYears;
	}

	public List<String> getAvailableReclamosMonths() {
		return availableReclamosMonths;
	}

	public void setAvailableReclamosMonths(List<String> availableReclamosMonths) {
		this.availableReclamosMonths = availableReclamosMonths;
	}

	public String getSelectedMonth() {
		return selectedMonth;
	}

	public void setSelectedMonth(String selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public String getSelectedReclamosYear() {
		return selectedReclamosYear;
	}

	public void setSelectedReclamosYear(String selectedReclamosYear) {
		this.selectedReclamosYear = selectedReclamosYear;
	}

	public BarChartModel getEventosBarModel() {
		return eventosBarModel;
	}

	public void setEventosBarModel(BarChartModel eventosBarModel) {
		this.eventosBarModel = eventosBarModel;
	}

}

