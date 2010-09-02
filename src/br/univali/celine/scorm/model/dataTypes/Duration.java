package br.univali.celine.scorm.model.dataTypes;

public class Duration {

	private String duration;

	public Duration(String duration) {
		this.duration = duration;
	}
	
	private long extractPeriod(String key, long days, StringBuffer sb) {
		
		return extractTime(key, days*86400, sb); // 86400 = 24*60*60 
	}
	
	private long extractTime(String key, long seconds, StringBuffer sb) {
		long resSeconds = 0;
		
		if (sb.indexOf(key) > -1) {
			resSeconds += (seconds)*(Float.parseFloat(sb.substring(0, sb.indexOf(key))));
			sb.delete(0, sb.indexOf(key)+1);
		}
		
		return resSeconds;
	}
	
	public long toSeconds() {
		
		long seconds = 0;
		
		int posT = duration.indexOf('T');
		String period = duration.substring(1);
		String time = "";
		if (posT > -1) {
			period = period.substring(0, posT-1);
			time = duration.substring(posT + 1);
			if (time.length() == 0) {
				throw new NumberFormatException("Time components expected");
			}
		}
		
		StringBuffer sb = new StringBuffer(period);
		
		// estou considerando 1 Y = 360 D (12 * 30)
		seconds += extractPeriod("Y", 360, sb) + extractPeriod("M", 30, sb) + extractPeriod("D", 1, sb);
		if (sb.length() > 0) {
			throw new NumberFormatException("Time components not expected");
		}
		sb = new StringBuffer(time);
		
		seconds += extractTime("H", 3600, sb) + extractTime("M", 60, sb) + extractTime("S", 1, sb);
		
		return seconds;
	}
	
	@Override
	public String toString() {
		return duration;
	}
	
	public static Duration parseDuration(long seconds) {
		
		long minutes = seconds / 60;
		seconds = seconds - (minutes*60);
		
		long hours = minutes / 60;
		minutes = minutes - (hours*60);
		
		long days = hours / 24;
		hours = hours - (days*24);
		
		long months = days / 30;
		days = days - (months*30);
		
		long years = months / 12;
		months = months - (years*12);
		
		return new Duration("P"+years+"Y"+months+"M"+days+"DT"+hours+"H"+minutes+"M"+seconds+"S");
		
	}
	
	
}
 