import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateRange {
	private Date startDate;
	private Date endDate;

	public DateRange(Date start, Date end) {
		startDate = start;
		endDate = end;
	}

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

	public DateRange ExpandRange(DateRange oldRange) {
		Date newStart = (oldRange.getStart().getTime() - TimeUnit.DAYS.convert(3, TimeUnit.DAYS));
		Date newEnd = oldRange.getEnd();
		DateRange newRange = new DateRange(newStart, newEnd);
		return newRange;
	}
}