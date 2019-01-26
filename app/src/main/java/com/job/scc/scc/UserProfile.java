package com.job.scc.scc;

    public class UserProfile {
        public String Name;
        public String Email;
        public String College;
        public String City;

        public UserProfile() {
        }

        public UserProfile(String userName, String userEmail, String userCollege, String userCity) {
            this.Name = userName;
            this.Email = userEmail;
            this.College = userCollege;
            this.City = userCity;
        }

          /*public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getCollege() {
            return College;
        }

        public void setCollege(String college) {
            College = college;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String city) {
            City = city;
        }*/
    }
