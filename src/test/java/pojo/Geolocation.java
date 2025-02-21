package pojo;

public  class Geolocation {
    private String lat;
    private String lon;

    public Geolocation(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }



    // Getters and Setters
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
