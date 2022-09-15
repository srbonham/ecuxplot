package org.nyet.util;

import java.util.ArrayList;

public class Version extends ArrayList<Object> {
    private static final long serialVersionUID = 20150620L;
    public static final String ECUxPlot="v1.0.2-dirty";
    public static final String JFreeChart="1.0.19";
    public static final String JCommon="1.0.23";
    public static final String OpenCSV="5.2";
    public static final String JavacMajor = "11";
    public static final String CommonsCLI = "1.4";
    public static final String CommonsLang3 = "3.10";
    public static final String CommonsText = "1.8";

    public Version () {
	this.add("ECUxPlot " + Version.ECUxPlot);
	this.add("JFreeChart " + Version.JFreeChart);
	this.add("JCommon " + Version.JCommon);
	this.add("OpenCSV " + Version.OpenCSV);
	this.add("commons-cli " + Version.CommonsCLI);
	this.add("commons-lang3 " + Version.CommonsLang3);
	this.add("commons-text " + Version.CommonsText);
	this.add("Java compiler " + Version.JavacMajor);
	this.add("Java runtime "+ System.getProperty("java.version"));
    }

    public String toString() {
	String s = Strings.join("<br>", this.toArray());
	return "<html>"+s+"</html>";
    }
}
