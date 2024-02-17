package database;

public class DataBaseFactory {
	private static String ENVIRONMENT = "development";

	public static DataBase getDatabase() {

		DataBase dataBase = null;

		if (ENVIRONMENT.equals("development")) {
			dataBase = DataBaseStubImpl.getInstance();
		}

		return dataBase;

	}

}
