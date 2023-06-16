package Models;

import java.util.ArrayList;

public class User {

        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String email;
        private String licensePlateNumber;


        private ArrayList<Violations> violations=new ArrayList<>();

        public User() {
        }

    public ArrayList<Violations> getViolations() {
        return violations;
    }

    public void setViolations(ArrayList<Violations> violations) {
        this.violations = violations;
    }

    public User(String firstName, String lastName, String phoneNumber, String email, String licensePlateNumber, ArrayList<Violations> violations) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.licensePlateNumber = licensePlateNumber;
        this.violations = violations;
    }

    public User(String firstName, String lastName, String phoneNumber, String email, String licensePlateNumber) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.licensePlateNumber = licensePlateNumber;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLicensePlateNumber() {
            return licensePlateNumber;
        }

        public void setLicensePlateNumber(String licensePlateNumber) {
            this.licensePlateNumber = licensePlateNumber;
        }
    }


