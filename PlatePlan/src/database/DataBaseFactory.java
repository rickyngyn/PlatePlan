package database;

public class DataBaseFactory {
	private static String ENVIRONMENT = "development";

	public static DataBase getDatabase() {

		DataBase dataBase = null;

		if (ENVIRONMENT.equals("development")) {
			dataBase = DataBaseStubImpl.getInstance();
		}else if (ENVIRONMENT.equals("production")) {
			dataBase = DataBaseImpl.getInstance();
		}
		return dataBase;

	}

}
