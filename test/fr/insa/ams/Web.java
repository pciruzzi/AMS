package fr.insa.ams;

public class Web {

    public static final String BASE = "http://localhost:8080/AMS/resources";
    public static final String APPLICATIONS = BASE + "/applications";
    public static final String COORDINATORS = BASE + "/classCoordinators";
    public static final String PARTNERS = BASE + "/partners";
    public static final String STUDENTS = BASE + "/students";

    public static final int SUCCESS = 200;
    public static final int RESOURCE_CREATED = 201;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int NOT_FOUND = 404;

}
