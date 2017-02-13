/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util.Graph;

import java.io.File;
import java.io.IOException;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Casa
 */
public class graph {
        final NumberAxis xAxis;
        final NumberAxis yAxis;

    public graph(){ 
       xAxis = new NumberAxis();
       yAxis = new NumberAxis();
    }
    public void createLineChart(int [] values,String title,String xlabel,String ylabel){
        //defining the axes
        xAxis.setLabel(xlabel);
       // yAxis.setLabel(ylabel);
        //creating the chart
        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis,yAxis);
                
        lineChart.setTitle(title);
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName(ylabel);
        
        for(int pos = 0;pos<values.length;pos++){
            series.getData().add(new XYChart.Data(pos,values[pos]));
        }
        
       //SAVE AS IMAGE
       
    }
    public void lineChart (double [] values,String title,String xlabel,String ylabel) throws IOException{
     
        
        
      DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
      
        
        for(int pos = 0;pos<values.length;pos++){
            line_chart_dataset.addValue(values[pos],"Count",""+pos);
        }
        
     
      JFreeChart lineChartObject = ChartFactory.createLineChart(
         title,xlabel, ylabel,
         line_chart_dataset,PlotOrientation.VERTICAL,
         true,true,false);

      int width = 640; /* Width of the image */
      int height = 480; /* Height of the image */ 
      File lineChart = new File( "LineChart.jpeg" ); 
      ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);
    }
}
