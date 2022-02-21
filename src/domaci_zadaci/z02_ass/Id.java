package domaci_zadaci.z02_ass;

public enum Id {

	PRVA(1, "g3khre7jrsih1idp5b5ahgm1f8"),
	DRUGA(2, "hu93vkklcv692mikqvm17scnv4"),
	TRECA(3, "6ovsf0fqb19q10s271b9e59ttc"),
	CETVRTA(4, "a730slcmbr9c6dii94j9pbdir4");

	private final int GODINA;
	private final String ID;

	private Id(int godina, String id) {
		this.GODINA = godina;
		this.ID = id;
	}

	public static String getId(int godina) {
		for (Id id : values()) {
			if (id.GODINA == godina) {
				return id.ID;
			}
		}
		return null;
	}
}
