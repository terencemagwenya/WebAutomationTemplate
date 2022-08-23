package mfstestcases;

public class BookingDatesData {
    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

//    public BookingDatesData(String checkin, String checkout) {
//        this.checkin = checkin;
//        this.checkout = checkout;
//    }

    String checkin;
    String checkout;

}
