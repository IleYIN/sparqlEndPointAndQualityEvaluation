package fr.ensma.lias.sparqlendpointjavaclient.completudeprecision;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import fr.ensma.lias.sparqlendpointjavaclient.utils.ReaderRank1f;
import fr.ensma.lias.sparqlendpointjavaclient.utils.ReaderRank2;

public class BarChartCompletude80 {
	ChartPanel frame1;
	public  BarChartCompletude80(int NUMBER, String[] strWant){
		CategoryDataset dataset = getDataSet(NUMBER, strWant);
      JFreeChart chart = ChartFactory.createBarChart3D(
     		                 "Complétude", // 图表标题
                          "Propriété", // 目录轴的显示标签
                          "ratio", // 数值轴的显示标签
                          dataset, // 数据集
                          PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                          true,           // 是否显示图例(对于简单的柱状图必须是false)
                          false,          // 是否生成工具
                          false           // 是否生成URL链接
                          );
      
      //从这里开始
      CategoryPlot plot=chart.getCategoryPlot();//获取图表区域对象
      CategoryAxis domainAxis=plot.getDomainAxis();         //水平底部列表
       domainAxis.setLabelFont(new Font("Calibri",Font.BOLD,14));         //水平底部标题
       domainAxis.setTickLabelFont(new Font("Calibri",Font.BOLD,15));  //垂直标题
       ValueAxis rangeAxis=plot.getRangeAxis();//获取柱状
       rangeAxis.setLabelFont(new Font("Calibri",Font.BOLD,15));
        chart.getLegend().setItemFont(new Font("Calibri", Font.BOLD, 15));
        chart.getTitle().setFont(new Font("Calibri",Font.BOLD,20));//设置标题字体
        
        //到这里结束，虽然代码有点多，但只为一个目的，解决汉字乱码问题
        
       frame1=new ChartPanel(chart,true);        //这里也可以用chartFrame,可以直接生成一个独立的Frame
       
	}
	   private static CategoryDataset getDataSet(int NUMBER, String[] strWant) {
         DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//         dataset.addValue(ratioprecision/completude, "nameSubclass", "namePred");
        
         
 		String[] nameSuj = new String[NUMBER];
 		
    
     	for (int m = 0; m<3; m++){
			int n = m*(2+NUMBER);
			ReaderRank1f rPred1 = new ReaderRank1f();
			ReaderRank2 rPred2 = new ReaderRank2();
			ReaderRank2 rPred3 = new ReaderRank2();
			String[] namePred = new String[NUMBER];
			double[] ratioSujPred = new double[NUMBER];
			
		
			nameSuj[m] = rPred3.readLineFile("resultFilePredCompletude80.txt", 0, n);
			String[] values1=nameSuj[m].split("/");
			String nameSujSimp = values1[4];
			System.out.println(nameSujSimp);

			
			for (int k = 0; k<NUMBER; k++ ){
				
				ratioSujPred[k] = rPred1.readLineFile("resultFilePredCompletude80.txt", 1 + n, 2 + NUMBER + n, k, 1);//init 1 end 22 
				namePred[k] = rPred2.readLineFile("resultFilePredCompletude80.txt", 3, k + 2 + n);
			
				String[] values=namePred[k].split("/");
				String namePredSimp = values[4];
				System.out.println(namePredSimp);
					
				
				for (int l=0; l<strWant.length; l++)	
					if (namePredSimp.equals(strWant[l])) {
						
						dataset.addValue(ratioSujPred[k], nameSujSimp, namePredSimp);
					
					}
			}
		}
        
         return dataset;
}
public ChartPanel getChartPanel(){
	return frame1;
	
}

public static void main(String args[]){
	JFrame frame=new JFrame("JavaBarChart");
	frame.setLayout(new GridLayout(2,2,10,10));
	int NUMBER = 80;
	String[] str = {"cuisine", "opening_hours", "takeaway","internet_access","smoking"};
	frame.add(new BarChartCompletude80(NUMBER, str).getChartPanel());           //添加柱形图
	frame.setBounds(50, 50, 800, 600);
	frame.setVisible(true);
}
}
