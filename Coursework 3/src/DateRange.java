import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateRange {
	private LocalDate startDate;
	private LocalDate endDate;

	public DateRange (LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public LocalDate getStart() {
		return this.startDate;
	}

	public LocalDate getEnd() {
		return this.endDate;
	}

	public int getDuration() {
		return (int) ChronoUnit.DAYS.between(startDate, endDate);
	}

	public boolean overlaps(DateRange date) {
		if (ChronoUnit.DAYS.between(date.getEnd(), this.startDate) <= 0) {
			if (ChronoUnit.DAYS.between(this.endDate, date.getStart()) <= 0) {
				return true;
			}
		}

		return false;
	}
	
	public DateRange ExpandRange() {
		LocalDate newStart = this.getStart().minusDays(3);
		LocalDate newEnd = this.getEnd().plusDays(3);
		DateRange expandedDateRange = new DateRange(newStart, newEnd);
		return expandedDateRange;
	}
}