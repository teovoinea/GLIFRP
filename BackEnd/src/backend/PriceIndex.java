package backend;

public class PriceIndex 
{
	public String place_name, place_id;
	public int id, period, index_nsa, index_sa, year;

	public String getPlace_name() {
		return place_name;
	}

	public String getPlace_id() {
		return place_id;
	}

	public int getId() {
		return id;
	}

	public int getPeriod() {
		return period;
	}

	public int getIndex_nsa() {
		return index_nsa;
	}

	public int getIndex_sa() {
		return index_sa;
	}

	public int getYear() {
		return year;
	}

	public PriceIndex(int id, String place_name, String place_id, int period, int index_nsa, int index_sa, int year)
	{
		this.id = id;
		this.place_name = place_name;
		this.place_id = place_id;
		this.period = period;
		this.index_nsa = index_nsa;
		this.index_sa = index_sa;
		this.year = year;
	}
}
