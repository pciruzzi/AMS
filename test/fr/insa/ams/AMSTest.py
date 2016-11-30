import unittest
import requests
import MySQLdb
import json

def defineGlobalConstants():

    global BASE
    BASE = "http://localhost:8080/AMS/resources/"

    # Resources
    global APPLICATIONS
    APPLICATIONS = BASE + "applications/"

    global APPLICATIONS_STATE
    APPLICATIONS_STATE = APPLICATIONS + "state/"

    global CLASS_COORDINATORS
    CLASS_COORDINATORS = BASE + "classCoordinators/"

    global PARTNERS
    PARTNERS = BASE + "partners/"

    global STUDENTS
    STUDENTS = BASE + "students/"

    # HTTP Status
    global BAD_REQUEST
    BAD_REQUEST = 400

    global NOT_FOUND
    NOT_FOUND = 404

    global UNAUTHORIZED
    UNAUTHORIZED = 401

    global RESOURCE_CREATED
    RESOURCE_CREATED = 201

    global SUCCESS
    SUCCESS = 200

    global UNSUPPORTED_METHOD
    UNSUPPORTED_METHOD = 405

def logTest(testname):
    print("Runing test " + testname)

class AMSTest(unittest.TestCase):
    def setUp(self):
        print("Setting up for test...")
        db = MySQLdb.connect(host="localhost",
                             user="amsUser",
                             passwd="amsuser",
                             db="ams")
        cur = db.cursor()
        cur.execute("DELETE FROM applications;")
        cur.execute("DELETE FROM classCoordinators;")
        cur.execute("DELETE FROM partners;")
        cur.execute("DELETE FROM students;")

        # cur.execute("DROP TABLE IF EXISTS applications;")
        # cur.execute("DROP TABLE IF EXISTS classCoordinators;")
        # cur.execute("DROP TABLE IF EXISTS partners;")
        # cur.execute("DROP TABLE IF EXISTS students;")
        # TODO: create tables
        db.commit()
        db.close()

    def test_basicTest(self):
        logTest("basicTest")
        self.assertEquals(True, True)

    def test_getStudent(self):
        logTest("getStudent")
        parameters = {'name' : 'juancitou', 'year' : 5}
        r = requests.put(STUDENTS, params=parameters)
        r = requests.get(STUDENTS + "1")
        print("URL: " + r.url)
        self.assertEquals(r.status_code, SUCCESS)

    def test_createStudent(self):
        logTest("createStudent")
        parameters = {'name' : 'juancitou', 'year' : 5}
        r = requests.put(STUDENTS, params=parameters)
        print("URL: " + r.url)
        self.assertEquals(r.status_code, RESOURCE_CREATED)



if __name__ == '__main__':
    defineGlobalConstants()
    unittest.main()