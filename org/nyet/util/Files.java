package org.nyet.util;

public class Files {
	public static String join(String sep, String [] a) {
	    return join(sep, a, a.length);
	}

	public static String join(String sep, String [] a, int count) {
	    String out = "";
	    for (int i=0; i<count; i++) {
		out += ((i==0)?"":sep) + a[i];
	    }
	    return out;
	}

	public static String dirname(String s) {
	    String [] a = s.split("\\"+java.io.File.separator);
	    return join(java.io.File.separator, a, a.length-1);
	}

	public static String filename(String s) {
	    String [] a = s.split("\\"+java.io.File.separator);
	    return a[a.length-1];
	}

	public static String stem(String s) {
	    String [] a = s.split("\\.");
	    return join(".", a, a.length-1);
	}

	public static String filenameStem(String s) {
	    return stem(filename(s));
	}

	public static String extension(String s) {
	    String [] a = s.split("\\.");
	    return a[a.length-1];
	}

	public static void main(final String[] args) {
	    String [] a = args;
	    if(args.length == 0) {
		a = new String [] {
			join(java.io.File.separator,
			    new String[] {"a","b","c","d.foo"})
		    };
	    }
	    for(int i=0;i<a.length;i++) {
		System.out.println("in " + a[i]);
		System.out.println(" dirname " +dirname(a[i]));
		System.out.println(" filename " +filename(a[i]));
		System.out.println(" stem " +stem(a[i]));
		System.out.println(" extension " +extension(a[i]));
		System.out.println(" stem/file " +filenameStem(a[i]));
	    }
	}
};
