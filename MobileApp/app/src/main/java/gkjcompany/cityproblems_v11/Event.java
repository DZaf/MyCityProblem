package gkjcompany.cityproblems_v11;

public class Event {
    int id;
    String path;
    String category;
    String day;
    String month;
    String year;
    String hour;
    double longtitude;
    double latitude;
    int upvotes;
    int downVotes;
    String useremail;
    String description;

    public Event(String useremail,int id, String path, String category, String day, String month, String year, String hour, double longtitude, double latitude, int upvotes, int downVotes,String description) {
        this.useremail=useremail;
        this.id = id;
        this.path = path;
        this.category = category;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.upvotes = upvotes;
        this.downVotes = downVotes-1;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getCategory() {
        return category;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getEmail() {
        return useremail;
    }

    public String getYear() {
        return year;
    }

    public String getHour() {
        return hour;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", category='" + category + '\'' +
                ", day='" + day + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", hour='" + hour + '\'' +
                ", longtitude=" + longtitude +
                ", latitude=" + latitude +
                ", upvotes=" + upvotes +
                ", downVotes=" + downVotes +
                '}';
    }
}
