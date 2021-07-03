package sorter;

import java.util.Comparator;

import beans.Karta;

public class KartaSorter  implements Comparator<Karta> {

	@Override
	public int compare(Karta o1, Karta o2) {
		if(o1.getDatumOtkazivanja() !=null && o2.getDatumOtkazivanja()!=null) {
			return o1.getDatumOtkazivanja().compareTo(o2.getDatumOtkazivanja());
		}
		return 0;
	}

}
