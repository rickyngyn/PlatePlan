package dto;

import java.time.LocalTime;
import java.util.Objects;

public class TimeSlot {

	public TimeSlot(LocalTime from, LocalTime to) {
		super();
		this.from = from;
		this.to = to;
	}

	private LocalTime from;

	private LocalTime to;

	/**
	 * @return the from
	 */
	public LocalTime getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(LocalTime from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public LocalTime getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(LocalTime to) {
		this.to = to;
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, to);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeSlot other = (TimeSlot) obj;
		return Objects.equals(from, other.from) && Objects.equals(to, other.to);
	}

	@Override
	public String toString() {
		return "TimeSlot [from=" + from + ", to=" + to + "]";
	}

}
