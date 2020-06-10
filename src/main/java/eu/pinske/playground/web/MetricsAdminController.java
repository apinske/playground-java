package eu.pinske.playground.web;

import static java.lang.Math.max;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/chart")
public class MetricsAdminController {

	@GetMapping("hist.png")
	public void hist(String name, @RequestParam(defaultValue = "10") int binSize,
			@RequestParam(defaultValue = "2000") int maxDuration, OutputStream out) throws IOException {
		long[] rawValues = random();
		double[] values = new double[rawValues.length];
		for (int i = 0; i < rawValues.length; i++) {
			values[i] = rawValues[i];
		}
		double max = NumberUtils.max(values);
		double min = NumberUtils.min(values);
		HistogramDataset data = new HistogramDataset();
		data.addSeries(name, values, (int) max(1, (max - min) / binSize));
		NumberAxis duration = new NumberAxis("Dauer");
		duration.setUpperBound(maxDuration > 0 ? maxDuration : max);
		XYPlot plot = new XYPlot(data, duration, new NumberAxis("Anzahl"), new XYBarRenderer());
		plot.getRenderer().setSeriesPaint(0, java.awt.Color.BLACK);
		ChartUtils.writeChartAsPNG(out, new JFreeChart(plot), 2048, 1536);
	}

	private long[] random() {
		long[] data = new long[1000];
		for (int i = 0; i < data.length; i++) {
			data[i] = RandomUtils.nextLong(0, RandomUtils.nextLong(0, 10000));
		}
		return data;
	}

}
