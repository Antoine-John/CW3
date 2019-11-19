import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateRange {
	private Date startDate;
	private Date endDate;

	public Date getStart() {
		return this.startDate;
	}

	public Date getEnd() {
		return this.endDate;
	}

	public int getDuration() {
		long durationInMillis = Math.abs(endDate.getTime() - startDate.getTime());
		int duration = (int) TimeUnit.DAYS.convert(durationInMillis, TimeUnit.MILLISECONDS);
		return duration;
	}

	public boolean overlaps(DateRange date) {
		if (Math.abs(this.startDate.getTime() - date.getEnd().getTime()) <= 0) {
			return true;
		}
		if (Math.abs(date.getStart().getTime() - this.endDate.getTime()) <= 0) {
			return true;
		}
		return false;
	}
}