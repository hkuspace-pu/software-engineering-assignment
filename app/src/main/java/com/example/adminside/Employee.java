package com.example.adminside;

    public class Employee {
        private int id;
        private String forename;
        private String surname;

        public Employee(int id, String forename, String surname) {
            this.id = id;
            this.forename = forename;
            this.surname = surname;
        }

        public int getId() {
            return id;
        }

        public String getForename() {
            return forename;
        }

        public String getSurname() {
            return surname;
        }
    }